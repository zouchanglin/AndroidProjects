package cn.tim.eventbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity_back2 extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView ivEmoji;

    public static final String HAND_EVENT_ACTION = "hand_event_action";;
    public static final String result = "result";
    private LocalBroadcastManager broadcastManager;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(HAND_EVENT_ACTION)){
                if(intent.getBooleanExtra(result, false)){
                    ivEmoji.setImageResource(R.drawable.ic_happy);
                }else {
                    ivEmoji.setImageResource(R.drawable.ic_bad);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivEmoji = findViewById(R.id.iv_emoji);
        broadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(HAND_EVENT_ACTION);
        broadcastManager.registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        broadcastManager.unregisterReceiver(receiver);
    }

    public void showDialogFragment(View view) {
        // Display DialogFragment
        PublisherDialogFragment fragment = new PublisherDialogFragment();
        fragment.show(getSupportFragmentManager(), "publisher");
    }
}