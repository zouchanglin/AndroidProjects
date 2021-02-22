package cn.tim.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import cn.tim.order.debug.OrderDebugActivity;


public class OrderMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oder_main_activity);
    }

//    public void toDebugActivity(View view) {
//        startActivity(new Intent(this, OrderDebugActivity.class));
//    }
}