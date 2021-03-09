package cn.tim.apt_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import cn.tim.annotation.DIActivity;
import cn.tim.annotation.DIView;

@DIActivity
public class MainActivity extends AppCompatActivity {

    @DIView(value = R.id.text)
    TextView textView;

    @DIView(value = R.id.text1)
    TextView textView1;

    @DIView(value = R.id.text2)
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DIMainActivity.bindView(this);
        textView.setText("Hello, JavaPoet!");

        textView2.setText("Tim");
    }
}