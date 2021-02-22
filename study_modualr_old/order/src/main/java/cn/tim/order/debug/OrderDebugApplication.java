package cn.tim.order.debug;

import android.app.Application;
import android.util.Log;

import cn.tim.common.config.BaseConfig;

// 集成化打包的时候需要排除掉debug包下的代码
public class OrderDebugApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(BaseConfig.TAG, "order/debug/OrderDebugApplication onCreate: ");
    }
}
