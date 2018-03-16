package com.example.wangpeijiang.zhihudemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.wangpeijiang.zhihudemo.Adapter.ScrollBottomScrollView;
import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.Utils.StaticClass;
import com.example.wangpeijiang.zhihudemo.Utils.UtilLog;
import com.example.wangpeijiang.zhihudemo.entity.Storiesview;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wangpeijiang on 2017/10/25.
 * 新闻页面
 */

public class StoriesActivity extends AppCompatActivity {
    private Storiesview storiesdetail;
    private WebView web_view;
    private ScrollBottomScrollView scrollview;
    private ImageView img;
    private String id;
    private int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stories);
        web_view = (WebView) findViewById(R.id.web_view);
        img = (ImageView) findViewById(R.id.img);
        scrollview=(ScrollBottomScrollView) findViewById(R.id.Scrollview);
        Intent intent = getIntent();
        id = intent.getStringExtra("News_id");
        UtilLog.d(id);
        getlatestInfo();

        scrollview.registerOnScrollViewScrollToBottom(new ScrollBottomScrollView.OnScrollBottomListener() {
            @Override
            public void srollToBottom() {
                String[] str={"9663584","9663725","9662630","9663598"};

                id = str[i];
                UtilLog.d(id);
                getlatestInfo();
                scrollview.scrollTo(0,0);
                web_view.reload();
                if(i<4){
                    i++;
                }

            }
        });

    }


    private void getlatestInfo() {


        RxVolley.get(StaticClass.News_URL+id, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Gson gson = new Gson();
                storiesdetail = gson.fromJson(t, Storiesview.class);//Gson解析数据
                UtilLog.d(t);
                initview();
            }

            @Override
            public void onFailure(VolleyError error) {
                super.onFailure(error);
                UtilLog.d("error");
            }
        });

    }

    private void initview() {
        web_view.setWebViewClient(new WebViewClient());



        WebSettings webSettings = web_view.getSettings();
        //支持JS
        webSettings.setJavaScriptEnabled(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadWithOverviewMode(true);


        /*PicassoUtils.loadImageViewSize(this,storiesdetail.getImage(),width,500,img);*/
        web_view.loadDataWithBaseURL(null,getNewContent(storiesdetail.getBody()) , "text/html", "utf-8", null);
    }







    //使用选择器语法来对元素筛选，设置其为自适应屏幕
    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
      //要素
        Elements elements = doc.getElementsByTag("img");
        int j;
        for(j=1;j<elements.size();j++){
            Element element = elements.get(j);
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }



}

