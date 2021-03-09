package cn.tim.hilt_demo;

import android.util.Log;

import javax.inject.Inject;

// 卡车
public class Truck {
    private static final String TAG = "Truck";

    @Inject
    Tyre tyre;
    
    // 无参构造注入
    @Inject
    public Truck() {
        Log.i(TAG, "Truck: ");
        // 先执行Truck初始化，再初始化Tyre，再注入
        Log.i(TAG, "Truck: tyre == null ? " + (tyre == null));
    }

    // 带参构造注入 （构造注入只能有一个）
//    @Inject
//    public Truck(Tyre tyre){
//        this.tyre = tyre;
//    }


    public void run(){
        System.out.println("truck run ...");
    }
}
