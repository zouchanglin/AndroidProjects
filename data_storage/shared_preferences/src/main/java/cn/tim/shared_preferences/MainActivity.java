package cn.tim.shared_preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        SharedPreferences login_info = getSharedPreferences("login_info", MODE_PRIVATE);
        String userName = login_info.getString("user_name", "");
        String password = login_info.getString("password", "");

        etUserName.setText(userName);
        etPassword.setText(password);
    }

    public void userLogin(View view) {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "输入不完整", Toast.LENGTH_SHORT).show();
        }

        // 存储输入的信息
        // 1、拿到SharedPreference对象
        SharedPreferences loginInfoSP = getSharedPreferences("login_info", MODE_PRIVATE);
        // 2、获取Editor对象
        SharedPreferences.Editor editor = loginInfoSP.edit();
        // 3、通过Editor存储数据
        editor.putString("user_name", userName);
        editor.putString("password", password);
        // 3、调用提交方法
        boolean commit = editor.commit();
        Log.i(TAG, "userLogin: commitRet = " + commit);

        // 校验登录结果
        if(!("admin".equals(userName) && "123456".equals(password))){
            Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            // TODO ...
        }
    }
}
