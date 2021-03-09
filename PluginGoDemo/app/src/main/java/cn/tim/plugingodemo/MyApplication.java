package cn.tim.plugingodemo;

import android.app.Application;

import cn.tim.plugin_lib.PluginManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.getInstance().init(this);
    }
}
