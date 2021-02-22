package cn.tim.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_back extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView ivEmoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivEmoji = findViewById(R.id.iv_emoji);
    }

    public void showDialogFragment(View view) {
        // Display DialogFragment
//        PublisherDialogFragment fragment = new PublisherDialogFragment();
//        fragment.show(getSupportFragmentManager(), "publisher");
//        fragment.setEventListener(new PublisherDialogFragment.OnEventListener() {
//            @Override
//            public void onSuccess() {
//                ivEmoji.setImageResource(R.drawable.ic_happy);
//            }
//
//            @Override
//            public void onFailed() {
//                ivEmoji.setImageResource(R.drawable.ic_bad);
//            }
//        });
    }
}