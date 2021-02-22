package cn.tim.study_modualr;

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

    public void routerToOrder(View view) {
        startActivity(new Intent(this, OrderMainActivity.class));
    }

    public void routerToPersonal(View view) {
        startActivity(new Intent(this, PersonalMainActivity.class));
    }
}