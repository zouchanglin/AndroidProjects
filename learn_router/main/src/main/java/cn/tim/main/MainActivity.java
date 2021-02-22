package cn.tim.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.tim.router_annotation.Router;

//@Router("/main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}