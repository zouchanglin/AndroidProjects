package cn.tim.custom_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class CustomReceiver extends BroadcastReceiver {
    TextView tvShow;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String action = intent.getAction();
            if(MainActivity.MY_ACTION.equals(action)){
                String inputContent = intent.getStringExtra(MainActivity.MY_ACTION_EXTRA_KEY);
                tvShow.setText(inputContent);
            }
        }
    }
}
