package cn.tim.broadcast_type;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class BReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            if(MainActivity.MY_ACTION.equals(action)){
                Bundle bundle = getResultExtras(true);
                Toast.makeText(context, "B:" + bundle.getInt(MainActivity.KEY), Toast.LENGTH_SHORT).show();
            }
        }
    }
}