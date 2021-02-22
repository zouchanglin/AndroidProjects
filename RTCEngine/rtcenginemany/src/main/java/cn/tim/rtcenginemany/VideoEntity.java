package cn.tim.rtcenginemany;

import android.view.SurfaceView;

public class VideoEntity {
    private SurfaceView surfaceView;

    private int uid;

    public VideoEntity(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    public VideoEntity(SurfaceView surfaceView, int uid) {
        this.surfaceView = surfaceView;
        this.uid = uid;
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
}
