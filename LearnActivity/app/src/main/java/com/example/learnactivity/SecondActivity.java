package com.example.learnactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.Arrays;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i(TAG, "onCreate: ");
        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("data");
//        if (bundle != null) {
//            String[] arrays = bundle.getStringArray("strArray");
//            String appName = bundle.getString("appName");
//            Log.i(TAG, "onCreate: arrays = " + Arrays.toString(arrays));
//            Log.i(TAG, "onCreate: appName = " + appName);
//        }
        Serializable user = intent.getSerializableExtra("user");
        Log.i(TAG, "onCreate: user = " + user);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    public void backMainActivity(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("appName", getString(R.string.app_name));
        intent.putExtra("resultData", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}