package cn.tim.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import cn.tim.order.debug.DebugOrderActivity;

public class OrderMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_main_activity);
    }

    public void toDebug(View view) {
        //startActivity(new Intent(this, DebugOrderActivity.class));
        //startActivity(new Intent(this, PersonalMainA));
    }
}