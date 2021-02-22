package cn.tim.eventbus_mode;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class PublisherDialogFragment extends DialogFragment {
    private static final String TAG = "PublisherDialogFragment";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = new String[]{"Posting", "Main", "MainOrder", "Background", "Async"};
        builder.setItems(items, (dialog, which) -> {
            switch (which){
                case 0: // Posting Mode
                    if(Math.random() > 0.5f){
                        new Thread("002"){
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }else {
                        EventBus.getDefault().post(new PostingEvent(Thread.currentThread().toString()));
                    }
                    break;
                case 1: // Main Mode
                    if(Math.random() > 0.5f){
                        new Thread("002"){
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }else {
                        EventBus.getDefault().post(new MainEvent(Thread.currentThread().toString()));
                    }
                    break;

                case 2: // Main_Order Mode
                    Log.i(TAG, "onCreateDialog: before @" + SystemClock.uptimeMillis());
                    EventBus.getDefault().post(new MainOrderEvent(Thread.currentThread().toString()));
                    Log.i(TAG, "onCreateDialog: after @" + SystemClock.uptimeMillis());
                    break;

                case 3: // BACKGROUND Mode
                    if(Math.random() > 0.5f){
                        new Thread("002"){
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new BackgroundEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }else {
                        EventBus.getDefault().post(new BackgroundEvent(Thread.currentThread().toString()));
                    }
                    break;

                case 4: // ASYNC Mode
                    if(Math.random() > 0.5f){
                        new Thread("002"){
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new AsyncEvent(Thread.currentThread().toString()));
                            }
                        }.start();
                    }else {
                        EventBus.getDefault().post(new AsyncEvent(Thread.currentThread().toString()));
                    }
                    break;
                default:
                    break;
            }
        });
        return builder.create();
    }
}
