package cn.tim.multiplayer_video_chat;

import android.view.SurfaceView;

import java.util.Objects;

public class VideoItemData {
    private int uid;

    private SurfaceView surfaceView;

    // 音频推流状态
    private boolean voiceStatus;

    // 视频推流状态
    private boolean videoStatus;

    // 接收视频流开关
    private boolean videoRecvStatus = true;

    // 接收音频流开关
    private boolean voiceRecvStatus = true;

    public VideoItemData() {
    }

    public VideoItemData(int uid) {
        this.uid = uid;
    }

    public VideoItemData(int uid, SurfaceView surfaceView) {
        this.uid = uid;
        this.surfaceView = surfaceView;
    }

    public VideoItemData(int uid, SurfaceView surfaceView, boolean voiceStatus,
                         boolean videoStatus, boolean videoRecvStatus,
                         boolean voiceRecvStatus) {
        this.uid = uid;
        this.surfaceView = surfaceView;
        this.voiceStatus = voiceStatus;
        this.videoStatus = videoStatus;
        this.videoRecvStatus = videoRecvStatus;
        this.voiceRecvStatus = voiceRecvStatus;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    public boolean isVoiceStatus() {
        return voiceStatus;
    }

    public void setVoiceStatus(boolean voiceStatus) {
        this.voiceStatus = voiceStatus;
    }

    public boolean isVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(boolean videoStatus) {
        this.videoStatus = videoStatus;
    }

    public boolean isVideoRecvStatus() {
        return videoRecvStatus;
    }

    public void setVideoRecvStatus(boolean videoRecvStatus) {
        this.videoRecvStatus = videoRecvStatus;
    }

    public boolean isVoiceRecvStatus() {
        return voiceRecvStatus;
    }

    public void setVoiceRecvStatus(boolean voiceRecvStatus) {
        this.voiceRecvStatus = voiceRecvStatus;
    }

    @Override
    public String toString() {
        return "VideoItemData{" +
                "uid=" + MainActivity.handleUid(uid) +
                ", voiceStatus=" + voiceStatus +
                ", videoStatus=" + videoStatus +
                ", videoRecvStatus=" + videoRecvStatus +
                ", voiceRecvStatus=" + voiceRecvStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoItemData that = (VideoItemData) o;
        return uid == that.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
