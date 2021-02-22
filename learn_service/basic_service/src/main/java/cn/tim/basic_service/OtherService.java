package cn.tim.basic_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OtherService extends Service {
    public OtherService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(()->{
            // TODO 执行耗时操作
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
