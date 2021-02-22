package cn.tim.aidl_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import cn.tim.basic_service.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AIDLMainActivity";
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface asInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                int progress = asInterface.getProgress();
                Log.i(TAG, "onServiceConnected: progress = " + progress);
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
        Intent intent = new Intent();
        intent.setAction("cn.tim.basic_service.myservice");
        intent.setPackage("cn.tim.basic_service");
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
        }
    }
}