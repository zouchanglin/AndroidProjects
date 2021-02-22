package cn.tim.recycleviewdemo;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、继承RecycleView.Adapter
 * 2、绑定ViewHolder
 * 3、实现Adapter的相关方法
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private final Context context;
    private final RecyclerView recyclerView;
    private List<String> dataSource;
    private OnItemClickListener listener;

    public MyRecycleViewAdapter(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        this.dataSource = new ArrayList<>();
    }

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // 创建并返回ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    // 通过ViewHolder绑定数据
    @Override
    public void onBindViewHolder(@NonNull MyRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(getIcon(position));
        holder.textView.setText(dataSource.get(position));
        LinearLayout.LayoutParams params;
        if(StaggeredGridLayoutManager.class.equals(recyclerView.getLayoutManager().getClass())){
            int randomHeight = getRandomHeight();
            // 只在瀑布流布局中使用随机高度
            params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    randomHeight < 50 ? dp2px(context, 50f): randomHeight
            );
        }else{
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.gravity = Gravity.CENTER;
        holder.textView.setLayoutParams(params);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
//        holder.itemView.setOnLongClickListener(v -> {
//            listener.onItemLongClick(position);
//            return false;
//        });
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 返回数据数量
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // 返回不同的随机ItemView高度
    private int getRandomHeight(){
        return (int)(Math.random() * 500);
    }

    // 根据不同的position选择一个图片
    private int getIcon(int position){
        switch (position % 5){
            case 0:
                return R.drawable.ic_4k;
            case 1:
                return R.drawable.ic_5g;
            case 2:
                return R.drawable.ic_360;
            case 3:
                return R.drawable.ic_adb;
            case 4:
                return R.drawable.ic_alarm;
            default:
                return 0;
        }
    }

    // 添加一条数据
    public void addData (int position) {
        dataSource.add(position, "插入的数据");
        notifyItemInserted(position);
        // 刷新ItemView
        notifyItemRangeChanged(position, dataSource.size() - position);
    }

    // 删除一条数据
    public void removeData (int position) {
        dataSource.remove(position);
        notifyItemRemoved(position);

        // 刷新ItemView
        notifyItemRangeChanged(position, dataSource.size() - position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
