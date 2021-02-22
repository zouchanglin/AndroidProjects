package cn.tim.boradcast_cycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    public AlarmReceiver() {
        //查看类创建的进程id和线程id
        Log.i(TAG, "AlarmReceiver() time = " + System.currentTimeMillis());
        Log.i(TAG, "AlarmReceiver()-> pid = " + android.os.Process.myPid());
        Log.i(TAG, "AlarmReceiver()-> tid = " + android.os.Process.myTid());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: time = " + System.currentTimeMillis());
    }
}
