package cn.tim.eventbus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PublisherDialogFragment_back2 extends DialogFragment {
    private static final String TAG = "PublisherDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = new String[]{"Success", "Failed"};
        builder.setItems(items, (dialog, which) -> {
//            Intent intent = new Intent(MainActivity.HAND_EVENT_ACTION);
//            switch (which){
//                case 0: // Success
//                    intent.putExtra(MainActivity.result, true);
//                    LocalBroadcastManager.getInstance(getActivity())
//                            .sendBroadcast(intent);
//                    break;
//                case 1: // Failed
//                    intent.putExtra(MainActivity.result, false);
//                    LocalBroadcastManager.getInstance(getActivity())
//                            .sendBroadcast(intent);
//                    break;
//                default:
//                    break;
//            }
        });
        return builder.create();
    }
}
