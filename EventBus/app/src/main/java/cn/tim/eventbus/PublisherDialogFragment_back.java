package cn.tim.eventbus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PublisherDialogFragment_back extends DialogFragment {
    private static final String TAG = "PublisherDialogFragment";

    private OnEventListener onEventListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Publisher");
        String[] items = new String[]{"Success", "Failed"};
        builder.setItems(items, (dialog, which) -> {
            switch (which){
                case 0: // Success
                    onEventListener.onSuccess();
                    break;
                case 1: //Failed
                    onEventListener.onFailed();
                    break;
                default:
                    break;
            }
        });
        return builder.create();
    }

    public interface OnEventListener{
        void onSuccess();
        void onFailed();
    }

    public void setEventListener(OnEventListener listener){
        this.onEventListener = listener;
    }
}
