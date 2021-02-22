package com.example.learndialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

public class DiyDialog extends Dialog {
    public DiyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        // 为对话框设置布局
        setContentView(R.layout.diy_dialog);

        // 按钮添加点击事件
        findViewById(R.id.no_btn).setOnClickListener((e)->{
            Log.d(TAG, "DiyDialog: ");
            this.dismiss();
        });

        findViewById(R.id.yes_btn).setOnClickListener((e)->{
            System.exit(0);
        });
    }
}
