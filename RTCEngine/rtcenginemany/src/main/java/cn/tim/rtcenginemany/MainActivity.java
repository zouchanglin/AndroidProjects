package cn.tim.rtcenginemany;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import java.util.ArrayList;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView videoRecyclerView;
    private VideoAdapter videoAdapter;
    private RtcEngine mRtcEngine;
    private int index = 0;

    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;

    private static final String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoRecyclerView = findViewById(R.id.recyclerView);
        videoAdapter = new VideoAdapter(this, videoRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        videoRecyclerView.setLayoutManager(gridLayoutManager);
        verifyStoragePermissions(this);

        ArrayList<VideoEntity> videoEntityList = new ArrayList<>();
        videoAdapter.setDataSource(videoEntityList);
        videoRecyclerView.setAdapter(videoAdapter);

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

    // 设置远端视图
    private void setupRemoteVideo(int uid) {
        mRtcEngine.enableVideo();

        Log.i(TAG, "setupRemoteVideo: uid = " + uid);
        SurfaceView remoteView = RtcEngine.CreateRendererView(getBaseContext());
        remoteView.setZOrderMediaOverlay(true);
        videoAdapter.addData(index, new VideoEntity(remoteView, uid));
        // 设置远端视图
        mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
    }

    // 设置本地视图
    private void setupLocalVideo() {
        // 启用视频模块
        mRtcEngine.enableVideo();

        SurfaceView localView = RtcEngine.CreateRendererView(getBaseContext());
        localView.setZOrderMediaOverlay(true);
        videoAdapter.addData(index, new VideoEntity(localView));
        // 设置本地视图
        VideoCanvas localVideoCanvas = new VideoCanvas(localView, VideoCanvas.RENDER_MODE_HIDDEN, 0);
        mRtcEngine.setupLocalVideo(localVideoCanvas);
    }

    // 加入频道
    private void joinChannel() {
        Log.i(TAG, "joinChannel: ");
        // 调用 joinChannel 方法 加入频道
        String YOUR_TOKEN = "00613915a51a0194d2a83d403b82110feb0IADUbWe5vpC2BktHQvcBYxxjqJmh2n83yh94QLIgkdpKYjdJ/TcAAAAAEABwjq6PKLUbYAEAAQAotRtg";
        mRtcEngine.joinChannel(YOUR_TOKEN, "9081278370914", "Extra Optional Data", 0);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RtcEngine.destroy();
    }
}