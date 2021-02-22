package cn.tim.routers;

import android.app.Activity;

import java.util.Map;

import cn.tim.common.IRouterLoad;
import cn.tim.shop.ShopActivity;

public class ShopRouter implements IRouterLoad {
    @Override
    public void loadInfo(Map<String, Class<? extends Activity>> routers) {
        routers.put("/shop/main", ShopActivity.class);
    }
}
