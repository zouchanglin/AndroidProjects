package com.example.mydialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        SwitchRateDialog switchRateDialog = new SwitchRateDialog(this, R.style.live_business_video_rate_dialog);
        switchRateDialog.show();
    }


    static class SwitchRateDialog extends Dialog {
        public SwitchRateDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            // 为对话框设置布局
            setContentView(R.layout.live_business_video_rate_dialog);

            // 按钮添加点击事件
            findViewById(R.id.btn_video_rate_standard).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i(TAG, "onClick: btn_video_rate_standard");
                        }
                    });
            findViewById(R.id.btn_video_rate_smooth).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: btn_video_rate_smooth");
                }
            });
        }
    }
}