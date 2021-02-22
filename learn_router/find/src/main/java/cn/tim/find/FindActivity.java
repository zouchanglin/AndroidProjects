package cn.tim.find;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.tim.router_annotation.Router;

@Router("/find/main")
public class FindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
    }
}