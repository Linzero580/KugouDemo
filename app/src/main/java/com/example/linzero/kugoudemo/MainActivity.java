package com.example.linzero.kugoudemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    private TextView mListenTextView;
    private TextView mWatchTextView;
    private TextView mSingTextView;

    private LinearLayout mListenLinearLayout;
    private LinearLayout mWatchLinearLayout;
    private LinearLayout mSingLinearLayout;

    private ImageView mTabLine;
    //  1/3的距离
    private int mScreen1_3;
    // 当前的位置
    private int mCurrentPageIndex;

    private SlidingMenu menu;
    private LinearLayout exit;
    private LinearLayout setting;
    private ImageView img_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SlidingMenu();

        initTabLine();

        initView();

        setSelect(0);

    }

    private void initTabLine() {
        //指示器
        mTabLine = (ImageView) findViewById(R.id.id_tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3 = outMetrics.widthPixels / 4;
        ViewGroup.LayoutParams lp = mTabLine.getLayoutParams();
        lp.width = mScreen1_3;
        mTabLine.setLayoutParams(lp);
    }

    //初始化控件
    private void initView() {
        exit = (LinearLayout) findViewById(R.id.exit);
        exit.setOnClickListener(this);
        setting = (LinearLayout) findViewById(R.id.setting);
        setting.setOnClickListener(this);
        img_head = (ImageView) findViewById(R.id.img_head);
        img_head.setOnClickListener(this);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mListenTextView = (TextView) findViewById(R.id.id_tv_listen);
        mWatchTextView = (TextView) findViewById(R.id.id_tv_watch);
        mSingTextView = (TextView) findViewById(R.id.id_tv_sing);

        mListenLinearLayout = (LinearLayout) findViewById(R.id.id_ll_listen);
        mListenLinearLayout.setOnClickListener(this);
        mWatchLinearLayout = (LinearLayout) findViewById(R.id.id_ll_watch);
        mWatchLinearLayout.setOnClickListener(this);
        mSingLinearLayout = (LinearLayout) findViewById(R.id.id_ll_sing);
        mSingLinearLayout.setOnClickListener(this);

        mDatas = new ArrayList<Fragment>();
        ListenMainTabFragment tab01 = new ListenMainTabFragment();
        WatchMainTabFragment tab02 = new WatchMainTabFragment();
        SingMainTabFragment tab03 = new SingMainTabFragment();
        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);

        TabFragment();
    }

    private void TabFragment() {
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0) {//从第0页到第1页
                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 0) {//从第1页到第0页
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 1) {//从第1页到第2页
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + positionOffset * mScreen1_3);
                } else if (mCurrentPageIndex == 2 && position == 1) {//从第2页到第1页
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
                }
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTab(int i) {
        resetTextView();
        switch (i) {
            case 0:
                mListenTextView.setTextColor(Color.parseColor("#008000"));
                break;

            case 1:
                mWatchTextView.setTextColor(Color.parseColor("#008000"));
                break;

            case 2:
                mSingTextView.setTextColor(Color.parseColor("#008000"));
                break;
        }
    }

    public void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    private void resetTextView() {
        mListenTextView.setTextColor(Color.BLACK);
        mWatchTextView.setTextColor(Color.BLACK);
        mSingTextView.setTextColor(Color.BLACK);
    }

    //侧滑菜单
    public void SlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        //设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        //设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.layout_left_menu);
    }

    //点击返回键关闭滑动菜单
    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                finish();
                break;

            case R.id.setting:
                Intent intent_setting = new Intent();
                intent_setting.setClass(MainActivity.this, SettingActivity.class);
                startActivity(intent_setting);
                break;

            case R.id.img_head:
                menu.showMenu();
                break;

            case R.id.id_ll_listen:
                setSelect(0);
                break;

            case R.id.id_ll_watch:
                setSelect(1);
                break;

            case R.id.id_ll_sing:
                setSelect(2);
                break;
        }
    }
}