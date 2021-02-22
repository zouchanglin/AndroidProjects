package cn.changlin.fragmentlifecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {
    private static final String TAG = "MyFragment";
    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: Fragment创建");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: Fragment绑定布局");
        return inflater.inflate(R.layout.my_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: 依附的Activity创建");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: Fragment启动");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Fragment正在运行");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: Fragment不再交互");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Fragment停止运行");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: Fragment视图销毁");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Fragment销毁");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: Fragment脱离Activity");
    }
}