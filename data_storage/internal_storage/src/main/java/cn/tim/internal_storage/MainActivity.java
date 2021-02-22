package cn.tim.internal_storage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveToFileDir();
        saveToCacheDir();
    }

    public void saveToFileDir() {
        File filesDir = MainActivity.this.getFilesDir();
        File myFileTxt = new File(filesDir, "myFile.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myFileTxt))){
            writer.write("这是内部存储文件目录的文件内容！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToCacheDir() {
        File cacheDir = MainActivity.this.getCacheDir();
        File myCacheTxt = new File(cacheDir, "myCache.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(myCacheTxt))){
            writer.write("这是内部存储缓存文件内容！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
