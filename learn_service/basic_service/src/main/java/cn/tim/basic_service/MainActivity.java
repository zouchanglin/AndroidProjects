package cn.tim.basic_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            MyService.DownloadBinder binder = (MyService.DownloadBinder) service;
//            binder.startDownload();
//            binder.getProgress();
            IMyAidlInterface asInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                asInterface.getProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutService(View view) {
        int id = view.getId();
        Intent intent = new Intent(this, MyService.class);
        Intent frontIntent = new Intent(this, FrontService.class);
        switch (id){
            case R.id.start_btn:
                startService(intent);
                break;
            case R.id.stop_btn:
                stopService(intent);
                break;
            case R.id.bind_btn:
                bindService(intent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_btn:
                unbindService(connection);
                break;
            case R.id.start_front_btn:
                startService(frontIntent);
                break;
            case R.id.start_intent_btn:
                loopStartIntentService();
                break;
        }
    }

    private void loopStartIntentService() {
        for (int i = 0; i < 10; i++) {
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            startService(intent);
        }
    }
}
