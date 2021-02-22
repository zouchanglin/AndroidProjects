package cn.tim.inputboxtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rootView = findViewById(R.id.main_root_layout);
        RelativeLayout inputView = (RelativeLayout) View.inflate(this, R.layout.custom_input_box, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(800, 1400);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootView.addView(inputView, params);
        Handler handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };

        handler.postDelayed(()->{
            inputView.setGravity(View.GONE);
            EditText et = inputView.findViewById(R.id.et_main);
            et.setText("InputBoxTest");
            handler.postDelayed(()-> et.setVisibility(View.GONE), 3000);

        }, 5000);
    }
}