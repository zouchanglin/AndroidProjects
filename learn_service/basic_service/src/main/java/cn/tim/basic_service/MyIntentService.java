package cn.tim.basic_service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    private int count = 0;
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        count++;
        Log.i(TAG, "onHandleIntent: count = " + count);
    }
}
