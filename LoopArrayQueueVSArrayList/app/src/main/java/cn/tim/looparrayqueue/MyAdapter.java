package cn.tim.looparrayqueue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final Context context;
    private LoopArrayQueue<Integer> dataSource;

    public MyAdapter(Context context,
                     LoopArrayQueue<Integer> queue) {
        this.context = context;
        this.dataSource = queue;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(new StringBuilder().append(dataSource.getByPosition(position)));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void setDataSource(LoopArrayQueue<Integer> queue) {
        this.dataSource = queue;
        notifyDataSetChanged();
    }


    // 添加一条数据
    public void addData(int data) {
        dataSource.enqueue(data);
        changeDataSource();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv);
        }
    }

    // 刷新数据源
    private void changeDataSource(){
        notifyDataSetChanged();
    }
}
