package com.tal.learn_gradle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 打出的Debug包与打出的Release包内容会自动变化
        String serverUrl = BuildConfig.SERVER_URL;
        Log.i(TAG, "onCreate: serverUrl = " + serverUrl);
        TextView tv = findViewById(R.id.tv_main);
        tv.setText("BuildConfig.BUILD_TYPE = " + BuildConfig.BUILD_TYPE
                + "\n SERVER_URL = " + serverUrl);
    }
}