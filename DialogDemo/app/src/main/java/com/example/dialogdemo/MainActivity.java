package com.example.dialogdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    class SwitchRateDialog extends Dialog {
        public SwitchRateDialog(@NonNull Context context, int themeResId) {
            super(context, themeResId);
            // 为对话框设置布局
            setContentView(R.layout.live_business_video_rate_dialog);

            // 按钮添加点击事件
            findViewById(R.id.btn_video_rate_standard).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
            findViewById(R.id.btn_video_rate_smooth).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}