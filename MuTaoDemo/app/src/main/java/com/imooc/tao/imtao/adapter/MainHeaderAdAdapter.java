package com.imooc.tao.imtao.adapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MainHeaderAdAdapter extends PagerAdapter {

    protected  Context context;
    protected  List<ImageView> images;
    public MainHeaderAdAdapter(Context context, List<ImageView> images){
        this.context=context;
        this.images=images;
    }
    @Override
    public int getCount() {
        return null!=images?images.size():0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       container.addView(images.get(position));
        return images.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }
}
