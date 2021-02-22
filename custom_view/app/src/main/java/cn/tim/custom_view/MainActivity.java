package cn.tim.custom_view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View view = findViewById(R.id.id_pb);
        view.setOnClickListener((v)-> ObjectAnimator.ofInt(view, "progress", 0, 100).setDuration(3000).start());
    }
}