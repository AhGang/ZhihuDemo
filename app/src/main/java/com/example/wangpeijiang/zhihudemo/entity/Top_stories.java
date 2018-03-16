/**
  * Copyright 2017 bejson.com 
  */
package com.example.wangpeijiang.zhihudemo.entity;

/**
 * Created by wangpeijiang on 2017/12/20.
 * 顶部新闻实体
 */
public class Top_stories {

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    public void setImage(String image) {
         this.image = image;
     }
     public String getImage() {
         return image;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setGa_prefix(String ga_prefix) {
         this.ga_prefix = ga_prefix;
     }
     public String getGa_prefix() {
         return ga_prefix;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

}