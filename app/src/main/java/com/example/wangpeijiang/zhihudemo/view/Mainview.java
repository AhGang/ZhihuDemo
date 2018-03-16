package com.example.wangpeijiang.zhihudemo.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.fragment.Girlfragment;
import com.example.wangpeijiang.zhihudemo.fragment.Weatherfragment;
import com.example.wangpeijiang.zhihudemo.fragment.ZhihuNewsfragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeijiang on 2017/12/14.
 * 主页面
 */

public class Mainview extends AppCompatActivity  {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);



        initData();
        initView();

    }

    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.zhihu_news));
        mTitle.add(getString(R.string.girl_photos));
        mTitle.add(getString(R.string.weather_));


        mFragment = new ArrayList<>();
        mFragment.add(new ZhihuNewsfragment());
        mFragment.add(new Girlfragment());
        mFragment.add(new Weatherfragment());

    }

    //初始化View
    private void initView() {


        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
