package cn.tim.learn_router;

import android.app.Application;

import cn.tim.common.Router;

public class RouterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Router.getInstance().register("/xxx", x);
        Router.init(this);
    }
}
