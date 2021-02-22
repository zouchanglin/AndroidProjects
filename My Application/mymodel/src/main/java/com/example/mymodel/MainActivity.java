package com.example.mymodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 中文的注释写在这里了
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findViewById(R.id.one_btn).setOnClickListener(new MyListener());
        //findViewById(R.id.one_btn).setOnClickListener(new MyListener());
        //findViewById(R.id.one_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
    }

    public void myClick(View view) {
        Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
    }


    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT).show();
        }
    }
}
