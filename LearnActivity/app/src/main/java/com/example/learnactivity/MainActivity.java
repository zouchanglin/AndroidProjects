package com.example.learnactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");

        if(savedInstanceState != null){
            String name = savedInstanceState.getString("name");
            // TODO ...
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.i(TAG, "onBackPressed: 用户按下返回键");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出程序吗？");
        builder.setPositiveButton("确定", (dialog, which) -> {
            super.onBackPressed();
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", "Tim");
        Log.i(TAG, "onSaveInstanceState: Success!");
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

    public void newActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("appName", getString(R.string.app_name));
//        bundle.putStringArray("strArray", new String[]{"A", "B", "C"});
        //intent.putExtra("user", new User("Tim", 18));
        //startActivity(intent);
        startActivityForResult(intent,666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 666 && resultCode == RESULT_OK){
            setTitle("SecondActivity成功返回");
            if (data != null) {
                Bundle resultData = data.getBundleExtra("resultData");
                String appName = resultData != null ? resultData.getString("appName") : null;
                Log.i(TAG, "onActivityResult: appName = " + appName);
            }
        }
    }

    public void newDialogActivity(View view) {
        startActivity(new Intent(this, DialogActivity.class));
    }

    public void newDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Message");
        builder.show();
    }
}