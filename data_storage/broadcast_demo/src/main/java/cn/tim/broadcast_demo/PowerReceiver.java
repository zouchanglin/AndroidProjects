package cn.tim.broadcast_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerReceiver extends BroadcastReceiver {
    private static final String TAG = "PowerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收广播
        if(intent != null){
            // 判断收到的是什么广播
            String action = intent.getAction();
            assert action != null;
            switch (action){
                case Intent.ACTION_PACKAGE_REMOVED:
                    Log.i(TAG, "onReceive: ACTION_POWER_CONNECTED " + "电源已连接");
                    break;
                case Intent.ACTION_PACKAGE_ADDED:
                    Log.i(TAG, "onReceive: ACTION_BATTERY_LOW " + "电量低于20%");
                    break;
            }
        }
    }
}
