package com.scorpiokara.cloud.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.scorpiokara.component.service.IAccountService;

/**
 * Created by .
 * 组件对外提供的数据
 * @author TaoYJ
 * @date 2018/8/13
 */
public class AccountService implements IAccountService {
    @Override
    public boolean isLogin() {
        return true;
    }

    @Override
    public String getAccountId() {
        return null;
    }

    /**
     * 对外提供 fragment 展示
     * @param activity
     * @param containerId
     * @param manager
     * @param bundle
     * @param tag
     * @return
     */
    @Override
    public Fragment newUserFragment(Activity activity, int containerId, FragmentManager manager, Bundle bundle, String tag) {
        return null;
    }
}
