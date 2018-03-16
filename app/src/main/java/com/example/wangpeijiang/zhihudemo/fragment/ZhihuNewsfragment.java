package com.example.wangpeijiang.zhihudemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wangpeijiang.zhihudemo.Adapter.NewsAdapter;
import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.Utils.StaticClass;
import com.example.wangpeijiang.zhihudemo.entity.LatestNews;
import com.example.wangpeijiang.zhihudemo.ui.StoriesActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by wangpeijiang on 2017/12/14.
 * 知乎新闻
 */


public class ZhihuNewsfragment extends Fragment{
    private List<LatestNews> mDatas = new ArrayList<>();//用于储存最新新闻数据
    private LatestNews latestNews;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<LatestNews> bannerList;//banner控件
    private List<String> titles;//存放banner中的标题
    private LinearLayoutManager mLinearLayoutManager;
    private List<String> images;//存放banner中的图片
    private List<Integer> ids;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_zhihu,null);
        recyclerView = view.findViewById(R.id.recycler_view);
        //加载数据
        initData();
        initView();
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void initView(){
        adapter = new NewsAdapter(mDatas,getContext());
    }
    private void initData() {
        mDatas = new ArrayList<>();
       getlatestInfo();
    }
    /*private  void initBeforeDate(){
        //解析接口
        String url = "https://news-at.zhihu.com/api/4/news/before/20151220";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
            }
        });
    }*/
   /* //解析Json
    private void parsingJson(String t) {
        try {
            Gson gson = new Gson();
            latestNews = gson.fromJson(t, LatestNews.class);
            for (int i = 0; i < latestNews.getStories().size(); i++) {
                LatestNews listItem = new LatestNews();
                listItem.setStories(latestNews.getStories());
                mDatas.add(listItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    //网络请求并解析Json数据
    private void getlatestInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(StaticClass.NewsList_URL)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    latestNews = gson.fromJson(responseData, LatestNews.class);//Gson解析数据

                    //传递消息，已完成完成网络请求
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //解决 先网络请求 添加数据 渲染数据的过程。
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    for (int i = 0; i < latestNews.getStories().size(); i++) {
                        LatestNews listItem = new LatestNews();
                        listItem.setStories(latestNews.getStories());
                        mDatas.add(listItem);
                    }
                    //初始化列表
                    titles=new ArrayList<>();
                    ids=new ArrayList<>();
                    images=new ArrayList<>();
                    bannerList = new ArrayList<>();
                    for (int i = 0; i < latestNews.getTop_stories().size(); i++) {
                        LatestNews listItem = new LatestNews();
                        listItem.setTop_stories(latestNews.getTop_stories());
                        titles.add(listItem.getTop_stories().get(i).getTitle());
                        images.add(listItem.getTop_stories().get(i).getImage());
                        ids.add(listItem.getTop_stories().get(i).getId());
                        bannerList.add(listItem);
                    }
                    adapter.notifyDataSetChanged();

                    setHeader(recyclerView, images, titles, ids);
            }

        }
    };
//Banner轮播图
    private void setHeader(RecyclerView view, List<String> urls, List<String> titles, final List<Integer> ids) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.head_view, view, false);
        //找到banner所在的布局
        BGABanner banner = (BGABanner) header.findViewById(R.id.banner);
        //绑定banner
        banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(ZhihuNewsfragment.this)
                        .load(model)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {


                String id=String.valueOf(ids.get(position));

                //显式启动
                Intent intent = new Intent(getContext(),StoriesActivity.class);
                intent.putExtra("News_id",id);
                startActivity(intent);


            }
        });
        banner.setData(urls, titles);
        adapter.setHeadView(header);//向适配器中添加banner
    }
}

