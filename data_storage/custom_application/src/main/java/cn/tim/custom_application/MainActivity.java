package cn.tim.custom_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Bus;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i(TAG, "onCreate: " + getApplication());

        MyApplication application = (MyApplication) getApplication();
        application.registerActivity(this);

        startActivity(new Intent(this, OtherActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication application = (MyApplication) getApplication();
        Bus bus = application.getBus();
        Log.i(TAG, "onResume: bus = " + bus);
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication application = (MyApplication) getApplication();
        Bus bus = application.getBus();
        Log.i(TAG, "onPause: bus = " + bus);
        bus.unregister(this);
    }
}
