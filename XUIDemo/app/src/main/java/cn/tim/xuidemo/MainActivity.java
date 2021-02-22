package cn.tim.xuidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showXUIDialog(View view) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.icon_tip)
                .title("小提示")
                .content("这是XUI风格的弹窗")
                .positiveText("确定")
                .show();
    }
}