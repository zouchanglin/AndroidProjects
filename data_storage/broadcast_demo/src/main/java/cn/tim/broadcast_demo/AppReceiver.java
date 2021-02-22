package cn.tim.broadcast_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class AppReceiver extends BroadcastReceiver {
    private static final String TAG = "AppReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收广播
        if(intent != null){
            // 判断收到的是什么广播
            String action = intent.getAction();
            assert action != null;
            switch (action){
                case Intent.ACTION_PACKAGE_REMOVED:
                    Log.i(TAG, "onReceive: ACTION_PACKAGE_REMOVED " + "应用被卸载");
                    DownloadUtil util = DownloadUtil.get();
                    util.download("http://www.sqliteexpert.com/v4/SQLiteExpertPersSetup64.exe",
                            Environment.getExternalStorageDirectory().getAbsolutePath(),
                            "SQLiteExpertPersSetup64.exe", new DownloadUtil.OnDownloadListener() {
                                @Override
                                public void onDownloadSuccess(File file) {

                                }

                                @Override
                                public void onDownloading(int progress) {

                                }

                                @Override
                                public void onDownloadFailed(Exception e) {

                                }
                            });
                    break;
                case Intent.ACTION_PACKAGE_ADDED:
                    Log.i(TAG, "onReceive: ACTION_PACKAGE_ADDED " + "应用被安装");
                    break;
            }
        }
    }
}