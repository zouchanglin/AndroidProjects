package cn.tim.custom_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Bus;

public class OtherActivity extends AppCompatActivity {
    private static final String TAG = "OtherActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        MyApplication application = (MyApplication) getApplication();
        application.registerActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
        Log.i(TAG, "onResume: bus = " + BusProvider.getBus());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bus bus = BusProvider.getBus();
        Log.i(TAG, "onPause: bus = " + bus);
        bus.unregister(this);
    }
}