package cn.tim.basic_service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private DownloadBinder mBinder = new DownloadBinder();
    static class DownloadBinder extends Binder {
        public void startDownload() {
            // 模拟下载
            Log.i(TAG, "startDownload executed");
        }

        public int getProgress() {
            // 模拟返回下载进度
            Log.i(TAG, "getProgress executed");
            return 0;
        }
    }

    public MyService() {

    }

    // 创建
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    // 启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    // 绑定
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        //return mBinder;
        return new IMyAidlInterface.Stub() {
            @Override
            public int getProgress() {
                Log.i(TAG, "getProgress: IMyAidlInterface");
                return 0;
            }
        };
    }

    // 解绑
    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Log.i(TAG, "unbindService: ");
    }

    // 销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}