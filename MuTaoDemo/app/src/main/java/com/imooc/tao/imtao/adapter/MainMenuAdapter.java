package com.imooc.tao.imtao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imooc.tao.imtao.R;
import com.imooc.tao.imtao.entity.Menu;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuViewholder>{

    protected Context context;
   protected  List<Menu> menus;
    public MainMenuAdapter(Context context, List<Menu> menus){
        this.context=context;
        this.menus=menus;
    }
    @Override
    public MainMenuViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainMenuViewholder(LayoutInflater.from(context).inflate(R.layout.item_main_menu,null));
    }

    @Override
    public void onBindViewHolder(MainMenuViewholder holder, int position) {

        Menu menu=menus.get(position);
        holder.mImgMenuIcon.setImageResource(menu.icon);
        holder.mTxtMenuName.setText(menu.menuName);
    }

    @Override
    public int getItemCount() {
        return null!=menus?menus.size():0;
    }
}
class  MainMenuViewholder extends RecyclerView.ViewHolder{

    public ImageView mImgMenuIcon;
    public TextView mTxtMenuName;
    public MainMenuViewholder(View itemView) {
        super(itemView);
        mImgMenuIcon= (ImageView) itemView.findViewById(R.id.img_menu_icon);
        mTxtMenuName= (TextView) itemView.findViewById(R.id.txt_menu_name);
    }
}
