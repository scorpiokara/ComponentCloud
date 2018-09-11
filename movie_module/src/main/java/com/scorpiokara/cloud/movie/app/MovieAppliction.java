package com.scorpiokara.cloud.movie.app;

import android.app.Application;

import com.scorpiokara.base.app.BaseApplication;
import com.scorpiokara.cloud.movie.AccountService;
import com.scorpiokara.cloud.movie.MovieService;
import com.scorpiokara.component.ServiceFactory;

import cn.bmob.v3.Bmob;

/**
 * Created by .
 * 所有的组件的 Application 都继承 BaseApplication，并在对应的方法中实现操作，
 *
 * @author TaoYJ
 * @date 2018/8/13
 */
public class MovieAppliction extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application application) {
        //初始化 movieService
        ServiceFactory.getInstance().setMovieService(new MovieService());
        //初始化 AccountService
        ServiceFactory.getInstance().setAccountService(new AccountService());

        //第一：默认初始化
//        Bmob.initialize(this, "d22c278495f3db39e3f7c6dd3032db87");
    }

    @Override
    public void initModuleData(Application application) {

    }
}
