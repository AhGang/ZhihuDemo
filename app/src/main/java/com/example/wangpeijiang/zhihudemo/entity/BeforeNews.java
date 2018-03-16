/**
 * Copyright 2017 bejson.com
 */
package com.example.wangpeijiang.zhihudemo.entity;
import java.util.List;
/**
 * Created by wangpeijiang on 2017/12/20.
 * 过往新闻列表实体
 */
public class BeforeNews {

    private String date;
    private List<Stories> stories;

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }
    public List<Stories> getStories() {
        return stories;
    }


}