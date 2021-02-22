package com.example.learnfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 列表 Fragment
 */
public class ListFragment extends Fragment {
    private static final String BUNDLE_TITTLE = "bundle_tittle";
    private String tittle;

    public static ListFragment getInstance(String tittle){
        ListFragment fragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITTLE, tittle);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            tittle = arguments.getString(BUNDLE_TITTLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // 创建视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView textView = view.findViewById(R.id.tv_static_load);
        textView.setText(tittle);
        textView.setOnClickListener((v) -> {
            if(mOnTittleListener != null) {
                mOnTittleListener.onClick(tittle);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 1、定义接口
    // 当Tittle被点击的时候可以把Tittle传出去
    public interface OnTittleListener {
        void onClick(String tittle);
    }

    // 2、定义全局变量
    private OnTittleListener mOnTittleListener;

    // 3、设置接口的方法
    public void setOnTittleListener(OnTittleListener onTittleListener) {
        this.mOnTittleListener = onTittleListener;
    }
}
