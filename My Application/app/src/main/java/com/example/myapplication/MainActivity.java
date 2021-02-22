package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar one = findViewById(R.id.pb_one);
        ProgressBar two = findViewById(R.id.pb_two);
        ProgressBar three = findViewById(R.id.pb_three);
        two.setProgress(20);
        two.setMax(100);
        one.setMax(100);
        three.setMax(100);
        three.setProgress(0);
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                two.setProgress(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}