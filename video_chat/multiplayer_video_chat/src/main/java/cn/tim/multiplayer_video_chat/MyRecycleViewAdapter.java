package cn.tim.multiplayer_video_chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import static cn.tim.multiplayer_video_chat.MainActivity.TAG;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;

    private final List<VideoItemData> videoItemDataList;

    public MyRecycleViewAdapter(Context context) {
        this.context = context;
        videoItemDataList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoItemData videoItemData = videoItemDataList.get(position);
        SurfaceView surfaceView = videoItemData.getSurfaceView();
        int uid = videoItemData.getUid();
        Log.i(TAG, "onBindViewHolder: ");
        FrameLayout viewParent = (FrameLayout) surfaceView.getParent();
        if(null != viewParent){
            viewParent.removeView(surfaceView);
        }
        holder.remoteVideoContainer.addView(surfaceView);
        holder.remoteInfoTextView.setText(new StringBuilder("uid = ").append(MainActivity.handleUid(uid)));

        // 状态保存
        holder.recvVoiceSwitch.setChecked(videoItemData.isVoiceRecvStatus());
        holder.recvVideoSwitch.setChecked(videoItemData.isVideoRecvStatus());
        holder.remoteVideoCurtainTextView.setVisibility(!videoItemData.isVideoRecvStatus()? View.VISIBLE : View.GONE);
        holder.remoteVoiceTipsTextView.setVisibility(!videoItemData.isVoiceRecvStatus()? View.VISIBLE : View.GONE);

        holder.recvVideoSwitch.setOnCheckedChangeListener((btn, checked) -> onItemClickListener
                .clickRecvVideo(uid, checked));

        holder.recvVoiceSwitch.setOnCheckedChangeListener((btn, checked) -> onItemClickListener
                .clickRecvVoice(uid, checked));

        holder.remotePushVideo.setText(new StringBuffer("Ta的视频推流状态: ")
                .append(videoItemData.isVideoStatus() ? "开":"关")
                .append("\n(关闭接收视频流时指示无效)")
        );
    }

    @Override
    public int getItemCount() {
        return videoItemDataList.size();
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        super.onViewRecycled(holder);
        holder.remoteVideoContainer.removeAllViews();
    }

    // 单个用户加入频道
    public void addData (int position, VideoItemData videoItemData) {
        if(videoItemDataList.contains(videoItemData)){
            Log.e(TAG, "addData: 加入重复(videoItemDataList中已存在)");
            return;
        }
        Log.i(TAG, "addData: videoItemData = " + videoItemData);
        videoItemDataList.add(position, videoItemData);
        notifyItemInserted(position);
        // 刷新ItemView
        notifyItemRangeChanged(position, videoItemDataList.size() - position);
    }

    //单个用户离开频道
    public void removeData (VideoItemData videoItemData) {
        int index = videoItemDataList.indexOf(videoItemData);
        if(index == -1) return;
        videoItemDataList.remove(index);
        notifyItemRemoved(index);
        // 刷新ItemView
        notifyItemRangeChanged(index, videoItemDataList.size() - index);
    }

    /**
     * 清除所有条目
     */
    public void clearAllItems(){
        videoItemDataList.clear();
        notifyDataSetChanged();
    }

    // 更新条目
    public void itemStatusChanged(VideoItemData videoItemData) {
        int index = videoItemDataList.indexOf(videoItemData);

        if(index == -1) return;
        Log.i(TAG, "itemStatusChanged: videoItemData = " + videoItemData);
        videoItemDataList.set(index, videoItemData);
        // 通知条目更新
        notifyItemChanged(index);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        SwitchCompat recvVideoSwitch;
        SwitchCompat recvVoiceSwitch;
        FrameLayout remoteVideoContainer;
        TextView remoteInfoTextView;
        TextView remoteVideoCurtainTextView;
        TextView remoteVoiceTipsTextView;
        TextView remotePushVideo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recvVideoSwitch = itemView.findViewById(R.id.item_recv_video_sw);
            recvVoiceSwitch = itemView.findViewById(R.id.item_recv_voice_sw);
            remoteVideoContainer = itemView.findViewById(R.id.item_remote_video_window);
            remoteInfoTextView = itemView.findViewById(R.id.item_remote_info_tv);
            remoteVideoCurtainTextView = itemView.findViewById(R.id.item_remote_video_curtain_tv);
            remoteVoiceTipsTextView = itemView.findViewById(R.id.item_remote_voice_tips_tv);
            remotePushVideo = itemView.findViewById(R.id.item_push_video_stream_tv);
        }
    }

    public interface OnItemClickListener {
        void clickRecvVideo(int uid, boolean checked);
        void clickRecvVoice(int uid, boolean checked);
    }

    public VideoItemData getByUid(int uid){
        VideoItemData videoItemData = new VideoItemData(uid);
        int indexOf = videoItemDataList.indexOf(videoItemData);
        if(indexOf == -1) return null;
        return videoItemDataList.get(indexOf);
    }
}
