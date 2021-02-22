package com.tal.live;

import android.content.Context;
import android.widget.ImageView;

import com.xiao.nicevideoplayer.TxVideoPlayerController;

/**
 * 视频播放组件的控制器
 */
public class MyVideoPlayerController extends TxVideoPlayerController {

    public MyVideoPlayerController(Context context) {
        super(context);
        ImageView imageView = imageView();
        imageView.setImageResource(R.drawable.ic_launcher_background);
    }
}
