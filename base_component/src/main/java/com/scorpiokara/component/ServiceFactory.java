package com.scorpiokara.component;

import com.scorpiokara.component.service.IAccountService;
import com.scorpiokara.component.service.IMovieService;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/13
 */
public class ServiceFactory {

    /**
     * 视频组件接口类
     */
    IMovieService movieService;
    /**
     * 账户登录组件接口类
     */
    IAccountService accountService;

    /**
     * 禁止外部创建 ServiceFactory 对象
     */
    private ServiceFactory() {
    }

    /**
     * 通过静态内部类方式实现 ServiceFactory 的单例
     */
    private static class Inner {
        private static ServiceFactory serviceFactory = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    public IMovieService getMovieService() {
        if (movieService == null) {
            movieService = new IMovieService.EmptyMovieService();
        }
        return movieService;
    }

    public void setMovieService(IMovieService movieService) {
        this.movieService = movieService;
    }

    public IAccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
}
