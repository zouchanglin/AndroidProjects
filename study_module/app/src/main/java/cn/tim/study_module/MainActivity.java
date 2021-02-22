package cn.tim.study_module;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.tim.order.OrderMainActivity;
import cn.tim.personal.PersonalMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 组件化中不能直接使用其他组件的类
//    public void toOrderModule(View view) {
//        startActivity(new Intent(this, OrderMainActivity.class));
//    }
//
//    public void toPersonalModule(View view) {
//        startActivity(new Intent(this, PersonalMainActivity.class));
//    }
}