package cn.tim.external_storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText etContent;
    private TextView tvShow;

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
        // 获得外部存储的目录
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        Log.i(TAG, "onCreate: externalStorageDirectory = " + externalStorageDirectory.getAbsolutePath());
        etContent = findViewById(R.id.et_content);
        tvShow = findViewById(R.id.tv_show);
        verifyStoragePermissions(this);
    }

    public void save(View view) {
        String content = etContent.getText().toString();
        if(TextUtils.isEmpty(content)) {
            Toast.makeText(MainActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 判断外部存储的状态
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 外部存储已挂载
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String descPath = absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS + "/input_content.txt";
            File descFile = new File(descPath);
            Log.i(TAG, "save: descFile = " + descFile.getAbsolutePath());
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(descFile, true));
                writer.write(content);
                writer.close();
                Toast.makeText(MainActivity.this, "写入成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(MainActivity.this, "存储设备未挂载", Toast.LENGTH_SHORT).show();
        }
    }

    public void read(View view) {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String descPath = absolutePath + "/" + Environment.DIRECTORY_DOWNLOADS + "/input_content.txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(descPath)));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){
                builder.append(line).append("\n");
            }
            tvShow.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToPrivate(View view) {
        File externalPrivateDir = MainActivity.this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File myDownloadTxt = new File(externalPrivateDir, "myDownload.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(myDownloadTxt));
            writer.write("这是下载目录的文件内容！");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveToCache(View view) {
        File externalPrivateDir = MainActivity.this.getExternalCacheDir();
        File myCacheTxt = new File(externalPrivateDir, "myCache.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(myCacheTxt));
            writer.write("这是缓存文件内容！");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
