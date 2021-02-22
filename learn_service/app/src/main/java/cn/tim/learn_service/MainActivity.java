package cn.tim.learn_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final int UPDATE_UI = 1001;

    private TextView textView;

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == UPDATE_UI) textView.setText("Hello Thread!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_main);
    }

    public void updateUI(View view) {
        // new Thread(()-> textView.setText("Hello Thread!")).start(); Error!
        new Thread(()->{
            Message message = new Message();
            message.what = UPDATE_UI;
            handler.sendMessage(message);
        }).start();
    }
}