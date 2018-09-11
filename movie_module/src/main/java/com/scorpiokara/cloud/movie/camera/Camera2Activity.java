package com.scorpiokara.cloud.movie.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scorpiokara.base.app.AppConfig;
import com.scorpiokara.cloud.movie.R;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/21
 */
@Route(path = AppConfig.Camera2ActivityPath)
public class Camera2Activity extends AppCompatActivity {
    private FrameLayout mFlContianer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity_camera2);
        initView();
    }

    private void initView() {
        mFlContianer = (FrameLayout) findViewById(R.id.fl_contianer);
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.fl_contianer,Camera2Fragment.newInstance())
              .commit();
    }
}
