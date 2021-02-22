package cn.tim.common;

import android.app.Activity;

import java.util.Map;

public interface IRouterLoad {
    void loadInfo(Map<String, Class<? extends Activity>> routers);
}
