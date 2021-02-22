package com.tal.live;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

public class MainActivity extends AppCompatActivity {
    private NiceVideoPlayer mNiceVideoPlayer;
    /**
     * 申请权限请求码
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        init();
    }

    /**
     * 如果在申请权限的过程中需要做一些对应的处理，则在此方法中处理
     * @param requestCode 请求码
     * @param permissions 权限列表
     * @param grantResults 申请结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQUEST_EXTERNAL_STORAGE == requestCode){
            for(int ret: grantResults){
                if(ret == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "拒绝了权限，功能无法正常使用！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    /**
     * 校验权限
     * @param activity context
     */
    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED
                || readPermission != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限需要动态地去申请权限
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    // 权限请求码
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void init() {
        mNiceVideoPlayer = findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // or NiceVideoPlayer.TYPE_NATIVE
        mNiceVideoPlayer.setUp("http://ivi.bupt.edu.cn/hls/hunanhd.m3u8", null);
        MyVideoPlayerController controller = new MyVideoPlayerController(this);
        controller.setTitle("湖南卫视直播");
        mNiceVideoPlayer.setController(controller);
        mNiceVideoPlayer.enterFullScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
}