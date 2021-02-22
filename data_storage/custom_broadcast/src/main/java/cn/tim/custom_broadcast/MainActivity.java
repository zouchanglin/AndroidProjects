package cn.tim.custom_broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MY_ACTION = "cn.tim.action.MY_ACTION";
    public static final String MY_ACTION_EXTRA_KEY = "input_content";
    private EditText etContent;
    private CustomReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent = findViewById(R.id.et_content);
        TextView tvShow = findViewById(R.id.tv_show);

        receiver = new CustomReceiver();
        receiver.tvShow = tvShow;

        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_ACTION);
        registerReceiver(receiver, filter);
    }

    public void send(View view) {
        // 新建广播
        Intent intent = new Intent(MY_ACTION);
        // 放入广播要携带的数据
        intent.putExtra(MY_ACTION_EXTRA_KEY, etContent.getText().toString());
        //sendBroadcast(intent);
        // 发送顺序广播，参数二：权限
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除注册
        if(receiver != null){
            unregisterReceiver(receiver);
        }
    }
}
