package com.scorpiokara.cloud.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/2
 */
@Route(path = "/movie/MoviePlayActivity")
public class MoviePlayActivity extends AppCompatActivity implements View.OnClickListener, MediaManager.OnUpdateProgressTimeListener ,SeekBar.OnSeekBarChangeListener{
    private SurfaceView mSurfaceView;
    private SeekBar mSeekBar;
    /**
     * 播放
     */
    private Button mBtnPlay;
    /**
     * 暂停
     */
    private Button mBtnPause;
    /**
     * 停止
     */
    private Button mBtnStop;
    /**
     * 重播
     */
    private Button mBtnRestart;

    MediaManager manager;
    final String url = "http://bmob-cdn-21033.b0.upaiyun.com/2018/08/14/98ad231740f56567809f3ed1adfba3a2.mp4";
    /**
     * TextView
     */
    private TextView mMovieTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity_movieplay);
        initView();

        initMediaPlayer();
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnPause.setOnClickListener(this);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnStop.setOnClickListener(this);
        mBtnRestart = (Button) findViewById(R.id.btn_restart);
        mBtnRestart.setOnClickListener(this);
        mMovieTime = (TextView) findViewById(R.id.movie_time);
    }

    public void initMediaPlayer() {
        manager = new MediaManager(this);
        manager.setUpdateProgressTimeListener(this);
        manager.initMedia(mSurfaceView, url);

        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_play) {
            manager.start();
        } else if (i == R.id.btn_pause) {
            manager.pause();
        } else if (i == R.id.btn_stop) {
            manager.stop();
        } else if (i == R.id.btn_restart) {
            manager.reStart();
        } else {

        }
    }

    @Override
    public void onProgressTime(final int duration, final String time, final boolean max) {
        //设置seekbar
        if (max){
            mSeekBar.setMax(duration);
        }else {
            mSeekBar.setProgress(duration);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMovieTime.setText(time);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        manager.setStopUpdateProgress(true);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        manager.seekTo(progress);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.release();
    }
}
