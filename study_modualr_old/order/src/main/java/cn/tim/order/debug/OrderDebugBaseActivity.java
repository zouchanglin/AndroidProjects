package cn.tim.order.debug;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.tim.common.config.BaseConfig;

public class OrderDebugBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(BaseConfig.TAG, "order/debug/OrderDebugBaseActivity onCreate: ");
    }
}
