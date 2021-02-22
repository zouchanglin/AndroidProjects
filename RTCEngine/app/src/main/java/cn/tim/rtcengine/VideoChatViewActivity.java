package cn.tim.rtcengine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

public class VideoChatViewActivity extends AppCompatActivity {
    private static final String TAG = "VideoChatViewActivity";
    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;

    private static final String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private RelativeLayout localWindow;
    private RelativeLayout remoteWindow;
    private RtcEngine mRtcEngine;
    private EditText editTextToken;
    private EditText editTextRoom;
    private String YOUR_TOKEN;
    private String ROOM_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat_view);
        verifyStoragePermissions(this);

        localWindow = findViewById(R.id.local_video_window);
        remoteWindow = findViewById(R.id.remote_video_window);
        editTextToken = findViewById(R.id.et_token);
        editTextRoom = findViewById(R.id.et_room);
    }

    // 开始通话
    public void startTelephone(View view) {
        YOUR_TOKEN = editTextToken.getText().toString();
        ROOM_ID = editTextRoom.getText().toString();
        // 获取权限后，初始化 RtcEngine，并加入频道
        initEngineAndJoinChannel();
    }

    // 初始化并且加入Channel
    private void initEngineAndJoinChannel() {
        Log.i(TAG, "initEngineAndJoinChannel: ");
        initializeEngine();
        setupLocalVideo();
        joinChannel();
    }

    // 初始化RtcEngine对象
    private void initializeEngine() {
        Log.i(TAG, "initializeEngine: ");
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
            Log.i(TAG, "initializeEngine: ");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        // 注册 onJoinChannelSuccess 回调
        // 本地用户成功加入频道时，会触发该回调
        public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
            Log.i(TAG, "onJoinChannelSuccess: uid = " + uid);
        }

        // SDK 接收到加入到Channel时，会触发该回调
        // 可以在该回调中调用 setupRemoteVideo 方法设置远端视图
        @Override
        public void onUserJoined(int uid, int elapsed) {
            super.onUserJoined(uid, elapsed);
            Log.i(TAG, "onUserJoined: uid = " + uid);
            runOnUiThread(()-> setupRemoteVideo(uid));
        }

        @Override
        // 注册 onUserOffline 回调
        // 远端用户离开频道或掉线时，会触发该回调。
        public void onUserOffline(final int uid, int reason) {
            Log.i(TAG,"User offline, uid: " + (uid & 0xFFFFFFFFL));
        }
    };

    // 设置本地视图
    private void setupLocalVideo() {
        // 启用视频模块
        mRtcEngine.enableVideo();

        SurfaceView localView = RtcEngine.CreateRendererView(getBaseContext());
        localView.setZOrderMediaOverlay(true);
        localWindow.addView(localView);
        // 设置本地视图
        VideoCanvas localVideoCanvas = new VideoCanvas(localView, VideoCanvas.RENDER_MODE_HIDDEN, 0);
        mRtcEngine.setupLocalVideo(localVideoCanvas);
    }

    // 加入频道
    private void joinChannel() {
        Log.i(TAG, "joinChannel: ");
        // 调用 joinChannel 方法 加入频道
        // TODO 临时Token
//        String YOUR_TOKEN = "00613915a51a0194d2a83d403b82110feb0IAACFYsR1lXWrkh9ZxyzxDym5XmdGJxz4cdujT6uQhJ/Dk2ohH0AAAAAEABwjq6PYFYaYAEAAQBgVhpg";
        mRtcEngine.joinChannel(YOUR_TOKEN, ROOM_ID, "Extra Optional Data", 0);
    }

    // 设置远端视图
    private void setupRemoteVideo(int uid) {
        mRtcEngine.enableVideo();

        Log.i(TAG, "setupRemoteVideo: uid = " + uid);
        SurfaceView remoteView = RtcEngine.CreateRendererView(getBaseContext());
        remoteView.setZOrderMediaOverlay(true);
        remoteWindow.addView(remoteView);
        // 设置远端视图
        mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leaveChannel();
        RtcEngine.destroy();
    }

    // 离开当前频道
    private void leaveChannel() {
        Log.i(TAG, "leaveChannel: ");
        if(mRtcEngine != null) mRtcEngine.leaveChannel();
    }

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