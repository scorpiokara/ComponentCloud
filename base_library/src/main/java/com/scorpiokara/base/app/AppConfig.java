package com.scorpiokara.base.app;

/**
 * Created by .
 * 其中的 moduleApps 是一个静态的 String 数组，我们将需要初始化的组件的 Application 的完整类名放入到这个数组中。
 *
 * @author TaoYJ
 * @date 2018/8/13
 */
public class AppConfig {

    private static final String MovieModuleApp = "com.scorpiokara.cloud.movie.app.MovieAppliction";

    public static String[] modulesApps = {MovieModuleApp};


    public static final String CameraActivityPath = "/movie/camera/CameraActivity";
    public static final String Camera2ActivityPath = "/movie/camera/Camera2Activity";
}
