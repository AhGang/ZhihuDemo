/**
  * Copyright 2017 bejson.com 
  */
package com.example.wangpeijiang.zhihudemo.entity;

import java.util.List;

/**
 * Created by wangpeijiang on 2017/12/20.
 * 最新新闻列表实体
 */
public class LatestNews {

    private String date;
    private List<Stories> stories;
    private List<Top_stories> top_stories;
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

    public void setTop_stories(List<Top_stories> top_stories) {
         this.top_stories = top_stories;
     }
     public List<Top_stories> getTop_stories() {
         return top_stories;
     }

}