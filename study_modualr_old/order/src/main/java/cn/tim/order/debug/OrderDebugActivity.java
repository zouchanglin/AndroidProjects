package cn.tim.order.debug;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.tim.order.R;

public class OrderDebugActivity extends OrderDebugBaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_debug);
    }
}
