package cn.tim.hilt_demo;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

//1、必须自定义一个Application，使用@HiltAndroidApp注解
@HiltAndroidApp
public class MyApplication extends Application {

}
