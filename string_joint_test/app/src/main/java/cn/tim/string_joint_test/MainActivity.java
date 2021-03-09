package cn.tim.string_joint_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    // 10000次拼接
    private static final long NUM = 10000;
    private static String initStr = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRun(View view) {
        long s1 = System.currentTimeMillis();
        String str1 = new String("");
        for (int i = 0; i < NUM; i++) {
            str1 += initStr;
        }
        long e1 = System.currentTimeMillis();
        Log.i(TAG, "startRun: [string += str]的方式，耗时 = " + (e1 - s1));

        long s2 = System.currentTimeMillis();
        String str2 = new String("");
        for (int i = 0; i < NUM; i++) {
            str2 = str2 + initStr;
        }
        long e2 = System.currentTimeMillis();
        Log.i(TAG, "startRun: [string = string + str]的方式，耗时 = " + (e2 - s2));

        long s3 = System.currentTimeMillis();
        String str3 = new String("");
        for (int i = 0; i < NUM; i++) {
            str3.concat(initStr);
        }
        long e3 = System.currentTimeMillis();
        Log.i(TAG, "startRun: [string.concat(string)]的方式，耗时 = " + (e3 - s3));

        long s4 = System.currentTimeMillis();
        String str4 = new String("");
        StringBuffer buffer = new StringBuffer(initStr);
        for (int i = 0; i < NUM; i++) {
            buffer.append(initStr);
        }
        long e4 = System.currentTimeMillis();
        Log.i(TAG, "startRun: [stringbuffer.append()]的方式，耗时 = " + (e4 - s4));

        long s5 = System.currentTimeMillis();
        String str5 = new String("");
        StringBuilder builder = new StringBuilder(initStr);
        for (int i = 0; i < NUM; i++) {
            builder.append(initStr);
        }
        long e5 = System.currentTimeMillis();
        Log.i(TAG, "startRun: [stringbuilder.append()]的方式，耗时 = " + (e5 - s5));
    }
}