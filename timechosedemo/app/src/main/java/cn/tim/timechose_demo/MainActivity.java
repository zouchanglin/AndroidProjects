package cn.tim.timechose_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Calendar calendar = Calendar.getInstance();
//        final int currentHour24 = calendar.get(Calendar.HOUR_OF_DAY);
//        final int currentMinute = calendar.get(Calendar.MINUTE);
//        final int currentYear = calendar.get(Calendar.YEAR);
//        final int currentMonth = calendar.get(Calendar.MONTH);
//        final int currentDay = calendar.get(Calendar.DATE);
//        new TimePickerDialog(MainActivity.this, (view, hourOfDay, minute) -> {
//            String timeStr = currentYear + "-" + currentMonth + "-" + currentDay + " " + hourOfDay + ":" + minute;
//            try {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                Date targetTime = dateFormat.parse(timeStr);
//                Log.i(TAG, "onTimeSet: targetTime = " + targetTime);
//                if(targetTime != null) {
//                    int diffBegin = (int) (System.currentTimeMillis() - targetTime.getTime()) / 1000;
//                    Log.i(TAG, "onTimeSet: diffBegin = " + diffBegin);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }, currentHour24, currentMinute, true).show();
    }

    public void show(View view) {
        Calendar calendar = Calendar.getInstance();
        final int currentHour24 = calendar.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = calendar.get(Calendar.MINUTE);
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH) + 1;
        final int currentDay = calendar.get(Calendar.DATE);
        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeStr = currentYear + "-" + currentMonth + "-" + currentDay + " " + hourOfDay + ":" + minute;
                Log.i(TAG, "onTimeSet: timeStr = " + timeStr);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date targetTime = dateFormat.parse(timeStr);
                    Log.i(TAG, "onTimeSet: targetTime = " + targetTime);
                    long timeMillis = System.currentTimeMillis();
                    Log.i(TAG, "onTimeSet: System.currentTimeMillis() = " + timeMillis);
                    Log.i(TAG, "onTimeSet: targetTime.getTime() = " + targetTime.getTime());
                    if(targetTime != null) {
                        int diffBegin = (int) (System.currentTimeMillis() - targetTime.getTime()) / 1000;
                        Log.i(TAG, "onTimeSet: diffBegin = " + diffBegin);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, currentHour24, currentMinute,true).show();
    }
}