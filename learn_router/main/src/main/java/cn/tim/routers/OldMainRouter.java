package cn.tim.routers;

import android.app.Activity;

import java.util.Map;

import cn.tim.common.IRouterLoad;
import cn.tim.main.MainActivity;

public class OldMainRouter implements IRouterLoad{
    @Override
    public void loadInfo(Map<String, Class<? extends Activity>> routers) {
        routers.put("/main/main", MainActivity.class);
    }
}