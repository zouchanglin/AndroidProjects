package cn.tim.custom_application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.squareup.otto.Bus;

import java.util.HashMap;
import java.util.Set;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private Bus bus;

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    private HashMap<String, Activity> activityMap;

    public void registerActivity(Activity activity){
        activityMap.put(activity.getLocalClassName(), activity);
        Log.i(TAG, "registerActivity: " + activity + "已注册");
        Set<String> keySet = activityMap.keySet();
        Log.i(TAG, "registerActivity: 开始遍历已注册的Activity");
        for(String key: keySet){
            Log.i(TAG, "" + activityMap.get(key));
        }
    }

    /**
     * Application对象被创建的时候会调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: Application被创建");
        Log.i(TAG, "onCreate: 开始初始化需要需要的服务...");
        //Log.i(TAG, "onCreate: " + Thread.currentThread());
        activityMap = new HashMap<>();

        bus = new Bus();
    }

    /**
     * 系统配置变更，如横屏变成竖屏、系统语言更改
     * @param newConfig 新的系统配置
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged: 系统配置改变");
        Log.i(TAG, "onConfigurationChanged: " + newConfig );
    }

    /**
     * 系统内存吃紧的时候被调用
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory: ");
    }

    // 程序终止时被调用
    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate: ");
    }
}
