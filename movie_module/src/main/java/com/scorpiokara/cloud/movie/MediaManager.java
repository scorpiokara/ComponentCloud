package com.scorpiokara.cloud.movie;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/2
 */
public class MediaManager implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    private static final String TAG = "MediaManager";
    private Context mContext;
    SurfaceHolder surfaceHolder;
    MediaPlayer mediaPlayer;
    /**
     * 正常 闲置
     */
    private final int NORMAL = 0;
    /**
     * 播放中
     */
    private final int PLAYING = 1;
    /**
     * 暂停
     */
    private final int PAUSING = 2;
    /**
     * 停止
     */
    private final int STOPING = 3;
    /**
     * 当前播放状态
     */
    int currentState = NORMAL;

    private int maxDuration;
    private int currentDuration;

    private boolean isStopUpdateProgress;

    private String path;
    private boolean isSurfaceCreated;

    public MediaManager(Context mContex) {
        this.mContext = mContext;
    }

    protected MediaManager initMedia(SurfaceView mSurfaceView, String url) {
        surfaceHolder = mSurfaceView.getHolder();
        //是采用自己内部的双缓冲区，而是等待别人推送数据
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isSurfaceCreated = false;
                if (mediaPlayer.isPlaying()) {
                    currentDuration = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                }
            }
        });
        this.path = url;
        return this;
    }

    public void play(String path) {
        this.path = path;

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置数据类型
            mediaPlayer.setDataSource(path);
            //设置一下播放器显示的位置
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            //通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 开始
     */
    public void start() {
        if (mediaPlayer != null) {
            if (currentState == PAUSING) {
                mediaPlayer.start();
                currentState = PLAYING;
                //每次在调用刷新线程是都要设置false
                isStopUpdateProgress = false;
                return;
            } else if (currentState == STOPING) {
                //以下判断完美解决了停止后重新播放，释放两个资源的问题
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        }
        play(path);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer != null && currentState == PLAYING) {
            currentDuration = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            currentState = PAUSING;
            isStopUpdateProgress = true;
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            currentState = STOPING;
            isStopUpdateProgress = true;
        }
    }

    public void reStart() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            play(path);
        }
    }

    public void release() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            isStopUpdateProgress = true;
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
        isStopUpdateProgress = false;
        new Thread(new MediaManager.UpdateProgressRunnable()).start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        //设置状态播放中
        currentState = PLAYING;
        mediaPlayer.setOnBufferingUpdateListener(this);
        //获取播放文件总长度
        maxDuration = mediaPlayer.getDuration();

        int m = maxDuration / 1000 / 60;
        int s = maxDuration / 1000 % 60;
        String time = (m + ":" + s);
        if (updateProgressTime != null) {
            updateProgressTime.onProgressTime(maxDuration, time, true);
        }
        isStopUpdateProgress = false;
        new Thread(new UpdateProgressRunnable()).start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        Toast.makeText(mContext, "播放完了，重新再播放", Toast.LENGTH_SHORT).show();
        mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {


    }


    /**
     * 刷新进度和时间的任务
     */
    class UpdateProgressRunnable implements Runnable {

        @Override
        public void run() {
            //每一秒中获取当前的播放进度
            while (!isStopUpdateProgress) {
                int currentPostion = mediaPlayer.getCurrentPosition();
                final int m = currentPostion / 1000 / 60;
                final int s = currentPostion / 1000 % 60;
                String time = m + ":" + s;
                if (updateProgressTime != null) {
                    updateProgressTime.onProgressTime(currentPostion, time, false);
                }
                Log.d(TAG, "" + time);
                SystemClock.sleep(1000);
            }
        }
    }

    OnUpdateProgressTimeListener updateProgressTime;

    public void setUpdateProgressTimeListener(OnUpdateProgressTimeListener updateProgressTime) {
        this.updateProgressTime = updateProgressTime;
    }

    public void setStopUpdateProgress(boolean stopUpdateProgress) {
        isStopUpdateProgress = stopUpdateProgress;
    }

    public interface OnUpdateProgressTimeListener {
        void onProgressTime(int currentPostion, String time, boolean max);
    }
}
