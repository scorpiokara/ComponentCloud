package com.scorpiokara.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout mUiTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_main);
        initView();
    }

    private void initView() {
        mUiTabLayout = (TabLayout) findViewById(R.id.ui_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);


        mUiTabLayout.setupWithViewPager(mViewPager,true);
    }
}
