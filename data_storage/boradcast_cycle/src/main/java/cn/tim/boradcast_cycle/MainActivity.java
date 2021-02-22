package cn.tim.boradcast_cycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PendingIntent broadcast;
    private AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSend(View view) {
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        broadcast = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmReceiver.class), 0);
        manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                5000,
                broadcast
        );
    }

    public void stopSend(View view) {
        manager.cancel(broadcast);
    }
}
