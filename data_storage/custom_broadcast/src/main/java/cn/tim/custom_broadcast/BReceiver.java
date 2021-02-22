package cn.tim.custom_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            if(MainActivity.MY_ACTION.equals(action)){
                String inputContent = intent.getStringExtra(MainActivity.MY_ACTION_EXTRA_KEY);
                Toast.makeText(context, "B:" + inputContent, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
