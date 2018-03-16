package com.example.wangpeijiang.zhihudemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.wangpeijiang.zhihudemo.Adapter.GirlAdapter;
import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.Utils.StaticClass;
import com.example.wangpeijiang.zhihudemo.entity.Girldata;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeijiang on 2017/12/21.
 * 美女图片
 */

public class Girlfragment extends Fragment {
    //列表
    private GridView mGridView;
    //数据
    private List<Girldata> mList = new ArrayList<>();
    //适配器
    private GirlAdapter mAdapter;

    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {

        mGridView = (GridView) view.findViewById(R.id.mGridView);



        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {

                parsingJson(t);
            }
        });


    }

    //解析Json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);

                Girldata data = new Girldata();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GirlAdapter(getActivity(), mList);
            //设置适配器
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}