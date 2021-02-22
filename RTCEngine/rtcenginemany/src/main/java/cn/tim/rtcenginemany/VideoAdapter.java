package cn.tim.rtcenginemany;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1、继承RecycleView.Adapter
 * 2、绑定ViewHolder
 * 3、实现Adapter的相关方法
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private static final String TAG = "VideoAdapter";
    private final Context context;
    private final RecyclerView recyclerView;
    private List<VideoEntity> dataSource;

    private ChannelManager channelManager;
    public VideoAdapter(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        this.dataSource = new ArrayList<>();
        channelManager = new ChannelManager();
    }

    public void setDataSource(List<VideoEntity> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    // 创建并返回ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }

    // 通过ViewHolder绑定数据
    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: size = " + getItemCount());
        VideoEntity videoEntity = dataSource.get(position);
        RelativeLayout parent = (RelativeLayout) videoEntity.getSurfaceView().getParent();
        if(parent != null) {
            Log.i(TAG, "onBindViewHolder: ");
            parent.removeView(videoEntity.getSurfaceView());
        }
        holder.relativeLayout.addView(videoEntity.getSurfaceView());
        holder.textView.setText("uid = " + videoEntity.getUid());
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

    // 添加一条数据
    public void addData (int position, VideoEntity videoEntity) {
        dataSource.add(position, videoEntity);
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
        TextView textView;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv);
            relativeLayout = itemView.findViewById(R.id.item_video_container);
        }
    }
}
