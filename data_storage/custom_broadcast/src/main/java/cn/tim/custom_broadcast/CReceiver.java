package cn.tim.custom_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class CReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            if(MainActivity.MY_ACTION.equals(action)){
                String inputContent = intent.getStringExtra(MainActivity.MY_ACTION_EXTRA_KEY);
                Toast.makeText(context, "C:" + inputContent, Toast.LENGTH_SHORT).show();
                //终止广播
                //abortBroadcast();

                // 修改数据
                Bundle newBundle = new Bundle();
                newBundle.putString(MainActivity.MY_ACTION_EXTRA_KEY, "World");
                setResultExtras(newBundle);
            }
        }
    }
}
