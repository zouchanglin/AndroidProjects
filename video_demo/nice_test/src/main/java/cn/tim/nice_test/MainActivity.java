package cn.tim.nice_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

public class MainActivity extends AppCompatActivity {

    private NiceVideoPlayer videoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoPlayer = findViewById(R.id.nice_video_player);
        videoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE);

        videoPlayer.setUp("https://www.w3school.com.cn/example/html5/mov_bbb.mp4", null);
        //videoPlayer.setUp("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8", null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("tal's tim");
        controller.setImage(R.drawable.ic_launcher_background);
        videoPlayer.setController(controller);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

}