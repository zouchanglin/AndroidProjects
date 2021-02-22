package cn.tim.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private ImageView ivEmoji;


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(MyEvent myEvent){
        if(myEvent.isResult()){
            ivEmoji.setImageResource(R.drawable.ic_happy);
        }else {
            ivEmoji.setImageResource(R.drawable.ic_bad);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivEmoji = findViewById(R.id.iv_emoji);
    }

    public void showDialogFragment(View view) {
        // Display DialogFragment
        PublisherDialogFragment fragment = new PublisherDialogFragment();
        fragment.show(getSupportFragmentManager(), "publisher");
    }
}