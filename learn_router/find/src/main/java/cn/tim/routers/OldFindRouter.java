package cn.tim.routers;

import android.app.Activity;

import java.util.Map;

import cn.tim.common.IRouterLoad;
import cn.tim.find.FindActivity;

public class OldFindRouter implements IRouterLoad {
    @Override
    public void loadInfo(Map<String, Class<? extends Activity>> routers) {
        routers.put("/find/main", FindActivity.class);
    }
}
