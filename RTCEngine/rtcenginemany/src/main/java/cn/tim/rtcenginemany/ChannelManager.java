package cn.tim.rtcenginemany;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChannelManager {
    List<Integer> userIdList;
    Set<Integer> userIdSet;
    Map<Integer, VideoEntity> videoAdapterMap;

    public ChannelManager() {
        userIdList = new CopyOnWriteArrayList<>();
        userIdSet = new CopyOnWriteArraySet<>();
        videoAdapterMap = new ConcurrentHashMap<>();
    }

    /**
     * 用户加入当前Channel
     * @param userId 用户Id
     * @param listener 监听
     */
    public void userJoinChannel(int userId, ChannelListener listener){
        if(userIdSet.add(userId)){
            //videoAdapterMap.put(userId, new VideoEntity());
            userIdList.add(userId);
            listener.userJoinChannel();
        }
    }

    /**
     * 用户退出当前Channel
     * @param userId 用户Id
     * @param listener 监听
     */
    public void userExitChannel(int userId, ChannelListener listener){
        if(userIdSet.contains(userId)){
            userIdSet.remove(userId);
            userIdList.remove(Integer.valueOf(userId));
            videoAdapterMap.remove(userId);
            listener.userExitChannel();
        }
    }

    /**
     * 根据index获取VideoEntity
     * @param index userId index
     * @return VideoEntity
     */
    public VideoEntity get(int index){
        Integer userId = userIdList.get(index);
        return videoAdapterMap.get(userId);
    }

    /**
     * @return 当前Channel中用户数量
     */
    public int size(){
        return videoAdapterMap.size();
    }


    public interface ChannelListener {
        /**
         * 用户加入频道
         */
        void userJoinChannel();

        /**
         * 用户退出频道
         */
        void userExitChannel();
    }
}
