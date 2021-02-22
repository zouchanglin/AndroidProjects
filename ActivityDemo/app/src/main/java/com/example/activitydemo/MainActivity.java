package com.example.activitydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1.1注册上下文菜单
        registerForContextMenu(findViewById(R.id.btn_context_menu));
    }

    // 1.2重写onCreateContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // 加载上下文菜单资源
        getMenuInflater().inflate(R.menu.context, menu);
    }
    // 1.3操作事件处理
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_delete:
                Toast.makeText(getApplicationContext(), "删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_rename:
                Toast.makeText(getApplicationContext(), "重命名", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 1.4为按钮设置上下文操作模式

    public void secondActivity(View view) {
        // 方式一
        startActivity(new Intent(this, SecondActivity.class));
    }

    public void contentMenu(View view) {
    }
    public void popMenu(View view) {
        //1.1实例化PopupMenu对象,绑定在弹出式菜单这个按钮上
        PopupMenu menu = new PopupMenu(MainActivity.this, findViewById(R.id.btn_pop_menu));
        //1.2加载菜单资源:利用MenuInflater
        menu.getMenuInflater().inflate(R.menu.popup, menu.getMenu());
        //1.3为PopupMenu设置点击监听器
        menu.setOnMenuItemClickListener((menuItem)-> {
            int itemId = menuItem.getItemId();
            switch (itemId) {
                case R.id.pop_item_edit:
                    Toast.makeText(getApplicationContext(), "编辑", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pop_item_save:
                    Toast.makeText(getApplicationContext(), "保存", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pop_item_cancel:
                    Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onOptionsItemSelected(menuItem);
        });
        //1.4展示PopupMenu
        menu.show();
    }

    // 创建OptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单资源
        getMenuInflater().inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_develop:
                Toast.makeText(getApplicationContext(), "开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
                Toast.makeText(getApplicationContext(), "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_save:
                Toast.makeText(getApplicationContext(), "保存", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}