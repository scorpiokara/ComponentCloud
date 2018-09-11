package com.scorpiokara.cloud;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.scorpiokara.base.app.AppConfig;
import com.scorpiokara.component.ServiceFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };
    /**
     * 视频
     */
    private Button mBtnMovie;
    private FrameLayout mFlContainer;
    private ImageView mImageView;
    /**
     * movie
     */
    private Button mButton;
    /**
     * 相机
     */
    private Button mBtnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private void initView() {
        mBtnMovie = (Button) findViewById(R.id.btn_movie);
        mBtnMovie.setOnClickListener(this);
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);

        // 通过组件提供的 Service 实现 Fragment 的实例化
        ServiceFactory.getInstance().getAccountService()
                .newUserFragment(this, R.id.fl_container, this.getSupportFragmentManager(), null, "");
        mImageView = (ImageView) findViewById(R.id.imageView);


        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png?where=super").into(mImageView);


        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        mBtnCamera = (Button) findViewById(R.id.btn_camera);
        mBtnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_movie:
                if (ServiceFactory.getInstance().getAccountService().isLogin()) {
                    String name = ServiceFactory.getInstance().getMovieService().getMovieName();
                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                    ARouter.getInstance().build("/movie/MoviePlayActivity").navigation();
                } else {
                    Toast.makeText(this, "去登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button:
                break;
            case R.id.btn_camera:
                ARouter.getInstance().build(AppConfig.Camera2ActivityPath).navigation();
                break;
        }
    }
}
