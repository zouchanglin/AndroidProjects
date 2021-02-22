package cn.tim.broadcast_type;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String MY_ACTION = "cn.tim.action.MY_ACTION";
    public static final String MY_NEW_ACTION = "cn.tim.action.MY_NEW_ACTION";
    public static final String KEY = "cn.tim.action.MY_ACTION";
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 新建广播
        Intent intent = new Intent(MY_ACTION);
        // 放入广播要携带的数据
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, 100);
        intent.putExtras(bundle);
        //sendBroadcast(intent);

        // 发送顺序广播，参数二：权限
        //sendOrderedBroadcast(intent, null);

        // 获得LocalBroadcastManager对象
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // 动态注册广播
        localReceiver = new LocalReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_NEW_ACTION);
        localBroadcastManager.registerReceiver(localReceiver, filter);
    }

    public void sendLocalBroadcast(View view) {
        // 发送本地广播
        Intent intent = new Intent(MY_NEW_ACTION);
        localBroadcastManager.sendBroadcast(intent);
    }


    static class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context,"Received LocalBroadcast!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
