package cn.tim.custom_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    SurfaceHolder saveHolder;
    // 记录播放位置
    private int position;

    // 申请权限请求码
    private static final int REQUEST_EXTERNAL_STORAGE = 1001;

    // 检查权限，这种写法主要是针对比较新的Android6.0及以后的版本
    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED
                || readPermission != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限需要动态地去申请权限
            ActivityCompat.requestPermissions(
                    activity,
                    // 权限数组
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    // 权限请求码
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    // 如果在申请权限的过程中需要做一些对应的处理，则在此方法中处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQUEST_EXTERNAL_STORAGE == requestCode){
            // TODO ...
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");

        mediaPlayer = new MediaPlayer();
        surfaceView = findViewById(R.id.main_sv);
        // 设置屏幕常亮
        surfaceView.getHolder().setKeepScreenOn(true);
        // 添加回调接口
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                Log.i(TAG, "SurfaceHolder 被创建");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG, "SurfaceHolder 被改变");
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                Log.i(TAG, "SurfaceHolder 被销毁");
                if(mediaPlayer != null && mediaPlayer.isPlaying()){
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                }
            }
        });
    }

    public void start(View view) {
        verifyStoragePermissions(this);
        mediaPlayer.reset();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.mp4";
        try {
            mediaPlayer.setDataSource(path);
            //mediaPlayer.setDataSource("https://www.w3school.com.cn/example/html5/mov_bbb.mp4");
            mediaPlayer.setDisplay(surfaceView.getHolder());
            // 使用同步方式
            mediaPlayer.prepare();
            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(View view) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();
        }
    }

    public void goOn(View view) {
        mediaPlayer.setDisplay(surfaceView.getHolder());
        mediaPlayer.seekTo(position);
        mediaPlayer.start();
    }

    public void stop(View view) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: ");
        super.onPause();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            position = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }
}