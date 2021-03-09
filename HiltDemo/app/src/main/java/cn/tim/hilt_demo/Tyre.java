package cn.tim.hilt_demo;

import android.util.Log;

import javax.inject.Inject;

// 轮胎
public class Tyre {
    private static final String TAG = "Tyre";
    @Inject
    public Tyre() {
        Log.i(TAG, "Tyre: ");
    }
}
