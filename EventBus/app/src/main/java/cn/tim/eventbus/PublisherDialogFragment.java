package cn.tim.eventbus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.greenrobot.eventbus.EventBus;

public class PublisherDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = new String[]{"Success", "Failed"};
        builder.setItems(items, (dialog, which) -> {
            switch (which){
                case 0: // Success
                    EventBus.getDefault().post(new MyEvent(true));
                    break;
                case 1: // Failed
                    EventBus.getDefault().post(new MyEvent(false));
                    break;
                default:
                    break;
            }
        });
        return builder.create();
    }
}
