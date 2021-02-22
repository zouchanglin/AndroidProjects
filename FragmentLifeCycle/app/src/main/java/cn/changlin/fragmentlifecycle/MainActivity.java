package cn.changlin.fragmentlifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadFragment(View view) {
        MyFragment fragment = MyFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment, "MyFragment")
                .commit();
    }

    public void toAnotherActivity(View view) {
        startActivity(new Intent(this, AnotherActivity.class));
    }
}