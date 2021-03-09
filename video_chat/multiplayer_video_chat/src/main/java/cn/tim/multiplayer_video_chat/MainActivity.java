package cn.tim.multiplayer_video_chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.BeautyOptions;
import io.agora.rtc.video.VideoCanvas;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private RtcEngine mRtcEngine;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private FrameLayout localVideoLayout;
    private SwitchCompat localPreviewSwitch;
    private SwitchCompat joinChannelSwitch;
    private SwitchCompat videoCollectSwitch;
    private SwitchCompat voiceCollectSwitch;
    private SwitchCompat videoPushStreamSwitch;
    private SwitchCompat voicePushStreamSwitch;
    private TextView rtcStatusInfoTextView;

    private TextView localUidTextView;

    /**
     * 本地SurfaceView
     */
    private SurfaceView localSurfaceView;

    /**
     * 当前的本地用户的状态
     */
    private volatile int currentStatus = LEAVE_CHANNEL;

    private static final int IN_CHANNEL = 0; // 正处在某频道视频通话中
    private static final int LEAVE_CHANNEL = 1; // 已经离开频道
    private RecyclerView chatRecyclerView;
    private MyRecycleViewAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findBindView();
        addListener();
        verifyStoragePermissions(this);
        // 初始化RTCEngine
        initializeEngine();
        // 设置本地视图
        setupLocalVideo();
    }

    // FindViewById
    private void findBindView() {
        localVideoLayout = findViewById(R.id.local_video_window);
        localPreviewSwitch = findViewById(R.id.sw_local_preview);
        joinChannelSwitch = findViewById(R.id.sw_join_channel);
        videoCollectSwitch = findViewById(R.id.sw_video_collect);
        voiceCollectSwitch = findViewById(R.id.sw_voice_collect);
        videoPushStreamSwitch = findViewById(R.id.sw_video_stream);
        voicePushStreamSwitch = findViewById(R.id.sw_voice_stream);
        chatRecyclerView = findViewById(R.id.rv_chat);
        localUidTextView = findViewById(R.id.local_uid_tv);
        rtcStatusInfoTextView = findViewById(R.id.rtc_status_info_tv);
        initRecyclerView();
    }

    private void addListener(){
        // 本地预览的开关
        localPreviewSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            if (isChecked) {
                mRtcEngine.startPreview();
                localSurfaceView.setVisibility(View.VISIBLE);
            }else {
                mRtcEngine.stopPreview();
                localSurfaceView.setVisibility(View.GONE);
            }
        });

        // 加入房间的开关
        joinChannelSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            if (isChecked) {
                // 加入Channel（optionalInfo/optionalUid非必须）
                mRtcEngine.joinChannel(Constant.TOKEN, Constant.CHANNEL_NAME, "", 0);
            }else {
                // 离开Channel
                currentStatus = LEAVE_CHANNEL;
                mRtcEngine.leaveChannel();
                localUidTextView.setVisibility(View.GONE);
                mRtcEngine.muteAllRemoteVideoStreams(false);
                mRtcEngine.muteAllRemoteAudioStreams(false);
                chatAdapter.clearAllItems();
                Toast.makeText(MainActivity.this, "离开频道成功", Toast.LENGTH_SHORT).show();
            }
        });

        voiceCollectSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            // enableLocalAudio会导致本地听远端播放会有短暂中断
            mRtcEngine.enableLocalAudio(isChecked);
            Log.i(TAG, "音频采集ON/OFF: " + isChecked);
        });

        videoCollectSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            mRtcEngine.enableLocalVideo(isChecked);
            Log.i(TAG, "视频采集ON/OFF: " + isChecked);
        });

        videoPushStreamSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            mRtcEngine.muteLocalVideoStream(!isChecked);
            Log.i(TAG, "视频推流ON/OFF: " + !isChecked);
        });

        voicePushStreamSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(null == mRtcEngine) return;
            mRtcEngine.muteLocalAudioStream(!isChecked);
            Log.i(TAG, "音频推流ON/OFF: " + !isChecked);
        });

    }

    // 初始化RtcEngine对象
    private void initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(this, Constant.APP_ID, rtcEventHandler);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // RtcEngineEventHandler
    private final IRtcEngineEventHandler rtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // 本地用户成功加入频道时，返回uid
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            Log.i(TAG, "(本地用户成功加入频道) uid = " + handleUid(uid));
            currentStatus = IN_CHANNEL;
            // 改用自己的Handler
            handler.post(()->{
                Toast.makeText(MainActivity.this, "加入频道成功", Toast.LENGTH_SHORT).show();
                localUidTextView.setText(new StringBuilder("uid = ").append(handleUid(uid)));
                localUidTextView.setVisibility(View.VISIBLE);
            });
        }

        // 远端用户加入
        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            Log.i(TAG, "onUserJoined(远端用户加入频道): realUid = " + handleUid(uid));
            SurfaceView remoteView = RtcEngine.CreateRendererView(getBaseContext());
            remoteView.setZOrderMediaOverlay(true);
            // 添加对应item，这里的position参数是指定加入的位置，相当于自定义优先级
            mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
            handler.post(()-> chatAdapter.addData(0, new VideoItemData(uid, remoteView)));
        }

        @Override
        // 注册 onUserOffline 回调
        // 远端用户离开频道或掉线时，会触发该回调。
        public void onUserOffline(final int uid, int reason) {
            Log.i(TAG,"User offline(远端用户离开频道), uid: " + handleUid(uid));
            handler.post(() -> chatAdapter.removeData(new VideoItemData(uid)));
        }


        @Override
        public void onRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {
            super.onRemoteVideoStateChanged(uid, state, reason, elapsed);
            Log.i(TAG, "onRemoteVideoStateChanged: 远端用户Video状态变化," + "uid = " + handleUid(uid) + ", reason =" + reason);
            VideoItemData videoItemData = chatAdapter.getByUid(uid);
            if(null == videoItemData) return;
            switch (reason){
                case Constants.REMOTE_VIDEO_STATE_REASON_REMOTE_MUTED:
                    // 远端用户关闭视频推流
                    Log.i(TAG, "onRemoteVideoStateChanged: 远端用户关闭视频推流 uid = " + uid);
                    handler.post(() -> {
                        videoItemData.setVideoStatus(false);
                        chatAdapter.itemStatusChanged(videoItemData);
                    });
                    break;
                case Constants.REMOTE_VIDEO_STATE_REASON_REMOTE_UNMUTED:
                    Log.i(TAG, "onRemoteVideoStateChanged: 远端用户打开视频推流 uid = " +uid);
                    // 远端用户打开视频推流
                    handler.post(() -> {
                        videoItemData.setVideoStatus(true);
                        chatAdapter.itemStatusChanged(videoItemData);
                    });
                    break;
            }
        }

        // 收到远端用户视频第一帧回调【用来确认远端用户视频是否开启】
        @Override
        public void onFirstRemoteVideoFrame(int uid, int width, int height, int elapsed) {
            super.onFirstRemoteVideoFrame(uid, width, height, elapsed);
            VideoItemData videoItemData = chatAdapter.getByUid(uid);
            if(null == videoItemData) return;
            handler.post(() -> {
                videoItemData.setVideoStatus(true);
                chatAdapter.itemStatusChanged(videoItemData);
            });
        }

        // SDK向APP定时发送的统计信息回调
        @Override
        public void onRtcStats(RtcStats stats) {
            super.onRtcStats(stats);
            String buffer = "通话统计信息 >> \n" + "cpuTotalUsage " + stats.cpuTotalUsage + ", " +
                    "cpuAppUsage " + stats.cpuAppUsage + ", " +
                    "RTT " + stats.gatewayRtt + "\n" +
                    "用户数 " + stats.users + ", " +
                    "上行丢包率 " + stats.txPacketLossRate + "%, " +
                    "下行丢包率 " + stats.rxPacketLossRate + "%";
            handler.post(()-> rtcStatusInfoTextView.setText(buffer));
        }
    };

    // 设置本地视图
    private void setupLocalVideo() {
        // 启用视频 & 音频模块
        mRtcEngine.enableVideo();
        mRtcEngine.enableAudio();
        // 默认关闭音频采集
        mRtcEngine.enableLocalAudio(false);
        localSurfaceView = RtcEngine.CreateRendererView(getBaseContext());
        // 多层次SurfaceView支持
        localSurfaceView.setZOrderMediaOverlay(true);
        // 将SurfaceView添加到容器
        localVideoLayout.addView(localSurfaceView);
        // 设置本地视图，直接默认填充
        VideoCanvas localVideoCanvas = new VideoCanvas(localSurfaceView);
        mRtcEngine.setupLocalVideo(localVideoCanvas);
        // 开启美颜
        mRtcEngine.setBeautyEffectOptions(true, new BeautyOptions());
        // 开始预览
        mRtcEngine.startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁RtcEngine
        RtcEngine.destroy();
    }

    private void initRecyclerView() {
        // 设置线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatAdapter = new MyRecycleViewAdapter(this);
        chatAdapter.setOnItemClickListener(new MyRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void clickRecvVideo(int uid, boolean checked) {
                if(null == mRtcEngine) return;
                Log.i(TAG, "clickRecvVideo: ON/OFF接收远端用户(uid = " + handleUid(uid) + ")的视频流");
                // 停止接收视频流
                mRtcEngine.muteRemoteVideoStream(uid, !checked);
                VideoItemData videoItemData = chatAdapter.getByUid(uid);
                if(null == videoItemData) return;
                videoItemData.setVideoRecvStatus(checked);
                handler.post(()-> chatAdapter.itemStatusChanged(videoItemData));
            }

            @Override
            public void clickRecvVoice(int uid, boolean checked) {
                if(null == mRtcEngine) return;
                Log.i(TAG, "clickRecvVideo: ON/OFF接收远端用户(uid = " + handleUid(uid) + ")的音频流");
                // 停止接收音频流
                mRtcEngine.muteRemoteAudioStream(uid, !checked);
                VideoItemData videoItemData = chatAdapter.getByUid(uid);
                if(null == videoItemData) return;
                videoItemData.setVoiceRecvStatus(checked);
                handler.post(()-> chatAdapter.itemStatusChanged(videoItemData));
            }
        });

        chatRecyclerView.setAdapter(chatAdapter);
    }

    // 处理无符号UID
    public static long handleUid(int uid){
        return uid & 0xffffffffL;
    }

    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;

    // 检查权限
    public static void verifyStoragePermissions(Activity activity) {
        for(String permit : permission){
            if(ActivityCompat.checkSelfPermission(activity, permit) != PackageManager.PERMISSION_GRANTED){
                // 如果没有权限需要动态地去申请权限
                ActivityCompat.requestPermissions(
                        activity,
                        // 权限数组
                        permission,
                        // 权限请求码
                        REQUEST_EXTERNAL_STORAGE
                );
            }
            ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}