package com.scorpiokara.component.service;

import android.app.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/13
 */
public interface IAccountService {
    public boolean isLogin();

    public String getAccountId();

    /**
     * 创建 UserFragment
     *
     * @param activity
     * @param containerId
     * @param manager
     * @param bundle
     * @param tag
     * @return
     */
    Fragment newUserFragment(Activity activity, int containerId, FragmentManager manager, Bundle bundle, String tag);

}
