package com.imooc.tao.imtao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.imooc.tao.imtao.R;
import com.imooc.tao.imtao.adapter.MainHeaderAdAdapter;
import com.imooc.tao.imtao.adapter.MainMenuAdapter;
import com.imooc.tao.imtao.util.DataUtil;

/**
 * 主界面视图
 */
public class MainFragment extends Fragment {

    protected  int [] icons={R.mipmap.header_pic_ad1,R.mipmap.header_pic_ad2,R.mipmap.header_pic_ad1};
    protected ViewPager mVPagerHeaderAd;//广告头

    //菜单图标
    protected  int [] menuIons={R.mipmap.menu_airport,R.mipmap.menu_car
             ,R.mipmap.menu_course,R.mipmap.menu_hatol,
            R.mipmap.menu_nearby,R.mipmap.me_menu_go,
            R.mipmap.menu_ticket,R.mipmap.menu_train};
    protected  String [] menus;
    protected RecyclerView mRecycleViewMenu;//主菜单

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        menus=this.getActivity().getResources().getStringArray(R.array.main_menu);

        mVPagerHeaderAd= (ViewPager) getView().findViewById(R.id.vpager_main_header_ad);
        mRecycleViewMenu= (RecyclerView) getView().findViewById(R.id.recycleview_main_menu);


        MainHeaderAdAdapter adapter=new MainHeaderAdAdapter(getActivity(), DataUtil.getHeaderAddInfo(getActivity(),icons));
        mVPagerHeaderAd.setAdapter(adapter);

        //菜单
        //布局样式
        mRecycleViewMenu.setLayoutManager(new GridLayoutManager(getActivity(),4));
        MainMenuAdapter mainMenuAdapter=new MainMenuAdapter(getActivity(),DataUtil.getMainMenus(menuIons,menus));
        mRecycleViewMenu.setAdapter(mainMenuAdapter);
    }
}
