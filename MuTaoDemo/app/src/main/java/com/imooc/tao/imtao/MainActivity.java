package com.imooc.tao.imtao;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.imooc.tao.imtao.fragment.FindFragment;
import com.imooc.tao.imtao.fragment.MainFragment;
import com.imooc.tao.imtao.fragment.MeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected LinearLayout mMenuMain;
    protected LinearLayout mMenuFind;
    protected LinearLayout mMenuMe;
    protected MainFragment mMainFragment=new MainFragment();//首页
    protected FindFragment mFindFragmenr=new FindFragment();//发现
    protected MeFragment mMeFragment=new MeFragment();//我的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        //获取管理类
        this.getSupportFragmentManager()
                .beginTransaction()
            .add(R.id.container_content,mMainFragment)
            .add(R.id.container_content,mFindFragmenr)
                .hide(mFindFragmenr)
                .add(R.id.container_content,mMeFragment)
                .hide(mMeFragment)
        //事物添加  默认：显示首页  其他页面：隐藏
        //提交
        .commit();
    }


    /**
     * 初始化视图
     */
    public void initView(){
        mMenuMain= (LinearLayout) this.findViewById(R.id.menu_main);
        mMenuFind= (LinearLayout) this.findViewById(R.id.menu_find);
        mMenuMe= (LinearLayout) this.findViewById(R.id.menu_me);

        mMenuMain.setOnClickListener(this);
        mMenuFind.setOnClickListener(this);
        mMenuMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.menu_main://首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mMainFragment)
                        .hide(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case  R.id.menu_find://发现
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainFragment)
                        .show(mFindFragmenr)
                        .hide(mMeFragment)
                        .commit();
                break;
            case  R.id.menu_me://我的
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mMainFragment)
                        .hide(mFindFragmenr)
                        .show(mMeFragment)
                        .commit();
                break;
        }
    }
}
