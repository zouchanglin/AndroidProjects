package cn.tim.roundprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundProgressBar progressBar = findViewById(R.id.main_rpb);
        progressBar.setOnClickListener((e)->{
            ObjectAnimator.ofInt(progressBar, "progress", 0, 100).setDuration(3000).start();
        });
    }
}