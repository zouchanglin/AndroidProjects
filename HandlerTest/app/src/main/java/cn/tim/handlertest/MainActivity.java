package cn.tim.handlertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tv;
    private Handler handler;
    private int count = 0;
    private Handler downloadHandler;

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMessageMethod();
        ProgressBar progressBar = findViewById(R.id.down_progress_bar);
        progressBar.setMax(100);
        downloadHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 100){
                    Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                }
                progressBar.setProgress(msg.what);
            }
        };
    }

    // 点击开始下载
    public void startDown(View view) {
        verifyStoragePermissions(this);
        try {
            download("http://img.zouchanglin.cn/adbdriver-39e8d4e0.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //申请权限成功
                Toast.makeText(this, "申请权限成功", Toast.LENGTH_SHORT).show();
            }else{
                //申请权限被拒绝
                Toast.makeText(this, "申请权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void download(String urlStr) {
        new Thread(()->{
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                // 获取文件总长度
                int length = conn.getContentLength();
                //File downloadsDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                File downloadsDir = new File("/storage/emulated/0/Download");
                File descFile = new File(downloadsDir, "adbdriver-39e8d4e0.zip");
                int downloadSize = 0;
                if(descFile.exists()) descFile.delete();
                int offset;
                byte[] buffer = new byte[1024];
                FileOutputStream fileOutputStream = new FileOutputStream(descFile);
                while ((offset = inputStream.read(buffer)) != -1){
                    downloadSize += offset;
                    fileOutputStream.write(buffer, 0, offset);
                    downloadHandler.sendEmptyMessage((downloadSize * 100 / length));
                }
                fileOutputStream.close();
                inputStream.close();
                Log.i(TAG, "download: descFile = " + descFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void sendMessageMethod() {
        // 应该与当前线程的Looper关联
        handler = new Handler(this.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: " + msg.what);
                Log.i(TAG, "handleMessage: " + msg.arg1);
                Log.i(TAG, "handleMessage: " + msg.arg2);
                Log.i(TAG, "handleMessage: " + msg.obj);
                tv.setText(String.valueOf(msg.what));
            }
        };
        //handler.sendEmptyMessage(1001);
        tv = findViewById(R.id.main_tv);
    }

    public void flushTextView(View view) {
        new Thread(()->{
            //handler.sendEmptyMessage(count++);
            Message message = Message.obtain();
            message.what = 1002;
            message.arg1 = 1003;
            message.arg2 = 1004;
            message.obj = MainActivity.this;

            //handler.sendMessage(message);
            // 3S后把消息发出, SystemClock.uptimeMillis()获取当前时间
            //handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + 3000);
            // 5S后把消息发出
            handler.sendMessageDelayed(message, 5000);
            Log.i(TAG, "flushTextView: 这是点击按钮的线程，Thread Name = " + Thread.currentThread().getName());
            handler.post(()->{
                Log.i(TAG, "flushTextView: 这是发出的任务，Thread Name = " + Thread.currentThread().getName());
            });
        }).start();
    }


}