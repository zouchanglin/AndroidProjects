package cn.tim.my_video;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Map;

// 封装MediaPlayer
public class NiceVideoPlayer extends FrameLayout implements INiceVideoPlayer, TextureView.SurfaceTextureListener {
    private static final String TAG = "NiceVideoPlayer";
    private final Context mContext;
    private NiceVideoPlayerController mController;
    private FrameLayout mContainer;
    private String mUrl;
    private Map<String, String> mHeaders;
    private TextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private int mBufferPercentage; // 缓冲百分比
    private int mCurrentState = STATE_IDLE;
    private int mPlayerState = PLAYER_NORMAL;
    private boolean continueFromLastPosition = true;
    private int skipToPosition;
    private AudioManager mAudioManager;

    public static final int STATE_ERROR = -1;          // 播放错误
    public static final int STATE_IDLE = 0;            // 播放未开始
    public static final int STATE_PREPARING = 1;       // 播放准备中
    public static final int STATE_PREPARED = 2;        // 播放准备就绪
    public static final int STATE_PLAYING = 3;         // 正在播放
    public static final int STATE_PAUSED = 4;          // 暂停播放
    // 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
    public static final int STATE_BUFFERING_PLAYING = 5;
    // 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停)
    public static final int STATE_BUFFERING_PAUSED = 6;
    public static final int STATE_COMPLETED = 7;       // 播放完成

    // Player的三种状态
    public static final int PLAYER_NORMAL = 10;        // 普通播放器
    public static final int PLAYER_FULL_SCREEN = 11;   // 全屏播放器
    public static final int PLAYER_TINY_WINDOW = 12;   // 小窗口播放器

    public NiceVideoPlayer(Context context) {
        this(context, null);
    }

    public NiceVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void initAudioManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void init() {
        mContainer = new FrameLayout(mContext);
        mContainer.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContainer, params);
    }

    // 配置播放的视频资源路径（本地/网络资源）
    @Override
    public void setUp(String url, Map<String, String> headers) {
        mUrl = url;
        mHeaders = headers;
    }

    //播放之前设置好Controller
    public void setController(NiceVideoPlayerController controller) {
        mController = controller;
        mController.setNiceVideoPlayer(this);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.addView(mController, params);
    }

    /**
     * 开始播放
     */
    @Override
    public void start() {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(this);
        if (mCurrentState == STATE_IDLE
                || mCurrentState == STATE_ERROR
                || mCurrentState == STATE_COMPLETED) {
            initAudioManager();
            initMediaPlayer();  // 初始化播放器
            initTextureView();  // 初始化展示视频内容的TextureView
            addTextureView();   // 将TextureView添加到容器中
        }
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();

            // 声音输出为媒体声音类型输出
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setScreenOnWhilePlaying(true);
            // MediaPlayer就绪后即可开始播放
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mOnErrorListener);
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            // 设置缓冲进度更新的Listener
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        }
    }

    private final MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            mBufferPercentage = percent;
        }
    };

    private final MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            mCurrentState = STATE_PREPARED;
            mController.setControllerState(mPlayerState, mCurrentState);

            // 从上次的保存位置播放
            if (continueFromLastPosition) {
                int savedPlayPosition = NiceUtil.getSavedPlayPosition(mContext, mUrl);
                mp.seekTo(savedPlayPosition);
            }
            // 跳到指定位置播放
            if (skipToPosition != 0) {
                mp.seekTo(skipToPosition);
            }
        }
    };

    private final MediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = (mp, width, height) -> Log.i(TAG, "onVideoSizeChanged: ");

    private final MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mCurrentState = STATE_COMPLETED;
            mController.setControllerState(mPlayerState, mCurrentState);
            Log.i(TAG, "onCompletion: ");
            NiceVideoPlayerManager.instance().setCurrentNiceVideoPlayer(null);
        }
    };

    private final MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            mCurrentState = STATE_ERROR;
            mController.setControllerState(mPlayerState, mCurrentState);
            Log.i(TAG, "onError: ");
            return false;
        }
    };

    private final MediaPlayer.OnInfoListener mOnInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // 播放器渲染第一帧
                mCurrentState = STATE_PLAYING;
                mController.setControllerState(mPlayerState, mCurrentState);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // MediaPlayer暂时不播放，以缓冲更多的数据
                if (mCurrentState == STATE_PAUSED || mCurrentState == STATE_BUFFERING_PAUSED) {
                    mCurrentState = STATE_BUFFERING_PAUSED;
                } else {
                    mCurrentState = STATE_BUFFERING_PLAYING;
                }
                mController.setControllerState(mPlayerState, mCurrentState);
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                // 填充缓冲区后，MediaPlayer恢复播放/暂停
                if (mCurrentState == STATE_BUFFERING_PLAYING) {
                    mCurrentState = STATE_PLAYING;
                    mController.setControllerState(mPlayerState, mCurrentState);
                }
                if (mCurrentState == STATE_BUFFERING_PAUSED) {
                    mCurrentState = STATE_PAUSED;
                    mController.setControllerState(mPlayerState, mCurrentState);
                }
            }
            return true;
        }
    };

    private void initTextureView() {
        if (mTextureView == null) {
            mTextureView = new TextureView(mContext);
            mTextureView.setSurfaceTextureListener(this);
        }
    }

    private void addTextureView() {
        // 先移除，再添加
        mContainer.removeView(mTextureView);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.addView(mTextureView, 0,  params);
    }

    /**
     * 从指定的位置开始播放
     *
     * @param position 播放位置
     */
    @Override
    public void start(int position) {
        skipToPosition = position;
        mMediaPlayer.start();
    }

    /**
     * 重新播放，播放器被暂停、播放错误、播放完成后，需要调用此方法重新播放
     */
    @Override
    public void restart() {
        if (mCurrentState == STATE_PAUSED) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
            mController.setControllerState(mPlayerState, mCurrentState);
        }
        if (mCurrentState == STATE_BUFFERING_PAUSED) {
            mMediaPlayer.start();
            mCurrentState = STATE_BUFFERING_PLAYING;
            mController.setControllerState(mPlayerState, mCurrentState);
        }
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        if (mCurrentState == STATE_PLAYING) {
            mMediaPlayer.pause();
            mCurrentState = STATE_PAUSED;
            mController.setControllerState(mPlayerState, mCurrentState);
        }
        if (mCurrentState == STATE_BUFFERING_PLAYING) {
            mMediaPlayer.pause();
            mCurrentState = STATE_BUFFERING_PAUSED;
            mController.setControllerState(mPlayerState, mCurrentState);
        }
    }

    /**
     * seek到制定的位置继续播放
     *
     * @param pos 播放位置
     */
    @Override
    public void seekTo(long pos) {
        mMediaPlayer.seekTo((int) pos);
        mMediaPlayer.start();
    }

    /**
     * 设置音量
     *
     * @param volume 音量值
     */
    @Override
    public void setVolume(int volume) {
        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
    }

    /**
     * 开始播放时，是否从上一次的位置继续播放
     *
     * @param continueFromLastPosition true 接着上次的位置继续播放，false从头开始播放
     */
    @Override
    public void continueFromLastPosition(boolean continueFromLastPosition) {
        this.continueFromLastPosition = continueFromLastPosition;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isIdle() {
        return this.mCurrentState == NiceVideoPlayer.STATE_IDLE;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isPreparing() {
        return this.mCurrentState == NiceVideoPlayer.STATE_PREPARING;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isPrepared() {
        return this.mCurrentState == NiceVideoPlayer.STATE_PREPARED;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isBufferingPlaying() {
        return this.mCurrentState == NiceVideoPlayer.STATE_BUFFERING_PLAYING;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isBufferingPaused() {
        return this.mCurrentState == NiceVideoPlayer.STATE_BUFFERING_PAUSED;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isPlaying() {
        return this.mCurrentState == NiceVideoPlayer.STATE_PLAYING;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isPaused() {
        return this.mCurrentState == NiceVideoPlayer.STATE_PAUSED;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isError() {
        return this.mCurrentState == NiceVideoPlayer.STATE_ERROR;
    }

    /**
     * 当前的播放状态：
     */
    @Override
    public boolean isCompleted() {
        return this.mCurrentState == NiceVideoPlayer.STATE_COMPLETED;
    }

    @Override
    public boolean isFullScreen() {
        return this.mCurrentState == NiceVideoPlayer.PLAYER_FULL_SCREEN;
    }

    @Override
    public boolean isTinyWindow() {
        return this.mCurrentState == NiceVideoPlayer.PLAYER_TINY_WINDOW;
    }

    @Override
    public boolean isNormal() {
        return this.mCurrentState == NiceVideoPlayer.PLAYER_NORMAL;
    }

    /**
     * 获取最大音量
     *
     * @return 最大音量值
     */
    @Override
    public int getMaxVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    /**
     * 获取当前音量
     *
     * @return 当前音量值
     */
    @Override
    public int getVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    /**
     * 获取视频总时长，毫秒
     *
     * @return 视频总时长ms
     */
    @Override
    public long getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    /**
     * 获取当前播放的位置，毫秒
     *
     * @return 当前播放位置，ms
     */
    @Override
    public int getCurrentPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    /**
     * 获取视频缓冲百分比
     *
     * @return 缓冲白百分比
     */
    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    /**
     * 进入全屏模式
     */
    @Override
    public void enterFullScreen() {
        if (mPlayerState == PLAYER_FULL_SCREEN) return;

        // 隐藏ActionBar、状态栏，并横屏
        NiceUtil.hideActionBar(mContext);
        NiceUtil.scanForActivity(mContext)
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        this.removeView(mContainer);
        ViewGroup contentView = NiceUtil.scanForActivity(mContext)
                .findViewById(android.R.id.content);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(mContainer, params);

        mPlayerState = PLAYER_FULL_SCREEN;
        mController.setControllerState(mPlayerState, mCurrentState);
    }

    /**
     * 退出全屏模式
     *
     * @return true 退出
     */
    @Override
    public boolean exitFullScreen() {
        if (mPlayerState == PLAYER_FULL_SCREEN) {
            NiceUtil.showActionBar(mContext);
            NiceUtil.scanForActivity(mContext)
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            ViewGroup contentView = NiceUtil.scanForActivity(mContext)
                    .findViewById(android.R.id.content);
            contentView.removeView(mContainer);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(mContainer, params);

            mPlayerState = PLAYER_NORMAL;
            mController.setControllerState(mPlayerState, mCurrentState);
            return true;
        }
        return false;
    }

    /**
     * 进入小窗口模式
     */
    @Override
    public void enterTinyWindow() {
        if (mPlayerState == PLAYER_TINY_WINDOW) return;

        this.removeView(mContainer);

        ViewGroup contentView = NiceUtil.scanForActivity(mContext)
                .findViewById(android.R.id.content);
        // 小窗口的宽度为屏幕宽度的60%，长宽比默认为16:9，右边距、下边距为8dp。
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                (int) (NiceUtil.getScreenWidth(mContext) * 0.6f),
                (int) (NiceUtil.getScreenWidth(mContext) * 0.6f * 9f / 16f));
        params.gravity = Gravity.BOTTOM | Gravity.END;
        params.rightMargin = NiceUtil.dp2px(mContext, 8f);
        params.bottomMargin = NiceUtil.dp2px(mContext, 8f);

        contentView.addView(mContainer, params);

        mPlayerState = PLAYER_TINY_WINDOW;
        mController.setControllerState(mPlayerState, mCurrentState);
    }

    /**
     * 退出小窗口模式
     *
     * @return true 退出小窗口
     */
    @Override
    public boolean exitTinyWindow() {
        if (mPlayerState == PLAYER_TINY_WINDOW) {
            ViewGroup contentView = (ViewGroup) NiceUtil.scanForActivity(mContext)
                    .findViewById(android.R.id.content);
            contentView.removeView(mContainer);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            this.addView(mContainer, params);

            mPlayerState = PLAYER_NORMAL;
            mController.setControllerState(mPlayerState, mCurrentState);
            return true;
        }
        return false;
    }

    /**
     * 此处只释放播放器（如果要释放播放器并恢复控制器状态需要调用{@link #release()}方法）
     * 不管是全屏、小窗口还是Normal状态下控制器的UI都不恢复初始状态
     * 这样以便在当前播放器状态下可以方便的切换不同的清晰度的视频地址
     */
    @Override
    public void releasePlayer() {
        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(null);
            mAudioManager = null;
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mContainer.removeView(mTextureView);
        if (mSurface != null) {
            mSurface.release();
            mSurface = null;
        }
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
        mCurrentState = STATE_IDLE;
        Log.i(TAG, "releasePlayer: ");
    }

    /**
     * 释放INiceVideoPlayer，释放后，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
     * 并且控制器的UI也应该恢复到最初始的状态.
     */
    @Override
    public void release() {
        // 保存播放位置
        if (isPlaying() || isBufferingPlaying() || isBufferingPaused() || isPaused()) {
            NiceUtil.savePlayPosition(mContext, mUrl, getCurrentPosition());
        } else if (isCompleted()) {
            NiceUtil.savePlayPosition(mContext, mUrl, 0);
        }
        // 退出全屏或小窗口
        if (isFullScreen()) {
            exitFullScreen();
        }
        if (isTinyWindow()) {
            exitTinyWindow();
        }
        mCurrentState = STATE_IDLE;

        // 释放播放器
        releasePlayer();

        // 恢复控制器
        if (mController != null) {
            mController.reset();
        }
    }

    /**
     * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The width of the surface
     * @param height  The height of the surface
     */
    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        // surfaceTexture数据通道准备就绪，打开播放器
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
            try {
                mMediaPlayer.setDataSource(mContext.getApplicationContext(), Uri.parse(mUrl), mHeaders);
                mMediaPlayer.setSurface(new Surface(surface));
                mMediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mTextureView.setSurfaceTexture(mSurfaceTexture);
        }


        if (mSurfaceTexture == null) {
            mSurfaceTexture = surface;
            openMediaPlayer();
        } else {
            mTextureView.setSurfaceTexture(mSurfaceTexture);
        }
    }

    /**
     * Invoked when the {@link SurfaceTexture}'s buffers size changed.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The new width of the surface
     * @param height  The new height of the surface
     */
    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
     * If returns true, no rendering should happen inside the surface texture after this method
     * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
     * Most applications should return true.
     *
     * @param surface The surface about to be destroyed
     */
    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is updated through
     * {@link SurfaceTexture#updateTexImage()}.
     *
     * @param surface The surface just updated
     */
    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }

    private void openMediaPlayer() {
        // 屏幕常亮
        mContainer.setKeepScreenOn(true);
        // 设置监听
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        mMediaPlayer.setOnInfoListener(mOnInfoListener);
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        // 设置dataSource
        try {
            mMediaPlayer.setDataSource(mContext.getApplicationContext(), Uri.parse(mUrl), mHeaders);
            if (mSurface == null) {
                mSurface = new Surface(mSurfaceTexture);
            }
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.prepareAsync();
            mCurrentState = STATE_PREPARING;
            mController.onPlayStateChanged(mCurrentState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
