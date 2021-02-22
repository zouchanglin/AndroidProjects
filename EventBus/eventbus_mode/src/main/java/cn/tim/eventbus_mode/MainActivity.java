package cn.tim.eventbus_mode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Subscriber");
    }

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

    // POSTING 模式
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostingEvent(final PostingEvent event){
        String threadInfo = Thread.currentThread().toString();
        runOnUiThread(()->{
            setPublisherThreadInfo(event.threadInfo);
            setSubscriberThreadInfo(threadInfo);
        });
    }

    // MAIN 模式
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(final MainEvent event){
        String threadInfo = Thread.currentThread().toString();
        runOnUiThread(()->{
            setPublisherThreadInfo(event.threadInfo);
            setSubscriberThreadInfo(threadInfo);
        });
    }

    // MAIN_ORDER 模式
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMainOrderEvent(final MainOrderEvent event){
        Log.i(TAG, "onMainOrderEvent: enter @" + SystemClock.uptimeMillis());
        setPublisherThreadInfo(event.threadInfo);
        setSubscriberThreadInfo(Thread.currentThread().toString());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onMainOrderEvent: exit @" + SystemClock.uptimeMillis());
    }

    // BACKGROUND 模式
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEvent(final BackgroundEvent event){
        final String threadInfo = Thread.currentThread().toString();
        runOnUiThread(()->{
            setPublisherThreadInfo(event.threadInfo);
            setSubscriberThreadInfo(threadInfo);
        });
    }

    // ASYNC 模式
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncEvent(final AsyncEvent event){
        final String threadInfo = Thread.currentThread().toString();
        runOnUiThread(()->{
            setPublisherThreadInfo(event.threadInfo);
            setSubscriberThreadInfo(threadInfo);
        });
    }

    private void setPublisherThreadInfo(String threadInfo){
        setTextView(R.id.tv_publisher_thread, threadInfo);
    }

    private void setSubscriberThreadInfo(String threadInfo){
        setTextView(R.id.tv_subscriber_thread, threadInfo);
    }

    // Run on UI Thread
    private void setTextView(int resId, String text){
        TextView textView = findViewById(resId);
        textView.setText(text);
        textView.setAlpha(0.5f);
        textView.animate().alpha(1).start();
    }

    public void showDialogFragment(View view) {
        // Display DialogFragment
        PublisherDialogFragment fragment = new PublisherDialogFragment();
        fragment.show(getSupportFragmentManager(), "publisher");
    }
}