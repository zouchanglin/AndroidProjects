package com.example.learndialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
        
    }

    public void myClick(View view) {
        switch (view.getId()){
            case R.id.normal_dialog_btn:
                // AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定退出程序吗？");
                builder.setPositiveButton("确定", (dialog, which) -> finish());
                builder.setNegativeButton("取消", null);
                builder.show();
                //showNormalDialog();
                break;
            case R.id.diy_dialog_btn:
                DiyDialog diyDialog = new DiyDialog(this, R.style.DiyDialog);
                diyDialog.show();
                break;
            case R.id.popup_window_btn:
                showPopupWindow(view);
                break;
            case R.id.array_show_btn:
                final String[] items = {"Java", "MySQL", "C/C++", "Python", "GoLang", "JavaScript"};
                //构造参数意义：上下文、布局资源索引（每一项数据呈现的样式）、数据源
                //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.array_show, R.id.item_text, items);
                AlertDialog.Builder arrayShowBuilder = new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setAdapter(adapter, (dialog, which) -> {
                            Toast.makeText(this, adapter.getItem(which), Toast.LENGTH_SHORT).show();
                        });
                arrayShowBuilder.show();
                break;
        }
    }

    private void showPopupWindow(View v) {
        //1.1实例化
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
        // view 宽高 是否获得焦点
        PopupWindow window = new PopupWindow(view, 360, 65, true);
        // 设置背景为透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置可以响应外部的点击事件
        window.setOutsideTouchable(true);
        // 设置自身可以响应点击事件
        window.setTouchable(true);
        // 设置动画(1.创建动画资源 2.创建一个style应用动画资源 3.当前弹窗的动画风格设置)
        window.setAnimationStyle(R.style.translate_anim);
        // 以view为参照物
        window.showAsDropDown(v);

        view.findViewById(R.id.chose_btn).setOnClickListener((e)->{
            Toast.makeText(MainActivity.this, "选择", Toast.LENGTH_SHORT).show();
            // 不要忘记关闭PopupWindow
            window.dismiss();

        });

        view.findViewById(R.id.chose_all_btn).setOnClickListener((e)->{
            Toast.makeText(MainActivity.this, "全选", Toast.LENGTH_SHORT).show();
            window.dismiss();
        });

        view.findViewById(R.id.copy_btn).setOnClickListener((e)->{
            Toast.makeText(MainActivity.this, "复制", Toast.LENGTH_SHORT).show();
            window.dismiss();
        });
    }

    // 自定义方法
    private void showNormalDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("确定退出程序吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}