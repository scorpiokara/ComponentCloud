package com.scorpiokara.cloud;

import android.app.Application;
import android.os.Environment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scorpiokara.base.app.AppConfig;
import com.scorpiokara.base.app.BaseApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/10
 */
public class MainApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler().getExceptionHandler());
    }

    @Override
    public void initModuleApp(Application application) {
        for (String moduleapp: AppConfig.modulesApps){
            try {
                Class clazz = Class.forName(moduleapp);
                BaseApplication baseApplication = (BaseApplication) clazz.newInstance();
                baseApplication.initModuleApp(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initModuleData(Application application) {
        for (String moduleapp: AppConfig.modulesApps){
            try {
                Class clazz = Class.forName(moduleapp);
                BaseApplication baseApplication = (BaseApplication) clazz.newInstance();
                baseApplication.initModuleData(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
