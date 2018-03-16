package com.example.wangpeijiang.zhihudemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wangpeijiang.zhihudemo.Adapter.WeatherAdapter;
import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.Utils.StaticClass;
import com.example.wangpeijiang.zhihudemo.entity.Weather;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeijiang on 2017/12/25.
 */

public class Weatherfragment extends Fragment{

    private TextView text;
    private TextView Cityname;
    private TextView tem;
    private TextView time;
    private TextView humidity;
    private TextView Sunrise;
    private TextView Sunset;
    private String xmldata;
    private String City= "";
    private List<Weather> mweatherlist = new ArrayList<>();
    private String responseData;
    private Spinner spinner;
    String date="";
    String low="";
    String high="";
    String type="";
    String fengxiang="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.fragment_weather,null);

         text= view.findViewById(R.id.text);
         spinner =view.findViewById(R.id.spinner);
         Cityname=view.findViewById(R.id.Cityname);
         tem=view.findViewById(R.id.tem);
         time=view.findViewById(R.id.time);
         humidity=view.findViewById(R.id.humidity);
         Sunrise=view.findViewById(R.id.Sunrise);
         Sunset=view.findViewById(R.id.Sunset);

        /*Spinner*/
        // 初始化控件
        Spinner spinner = view. findViewById(R.id.spinner);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.Data);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件r
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] Citylist = getResources().getStringArray(R.array.Data);
                City=Citylist[pos];
               // UtilLog.d(City);
                initdata();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        /*Spinner*/


        RecyclerView recyclerView = view.findViewById(R.id.weather_view);
        WeatherAdapter weatherAdapter = new WeatherAdapter(mweatherlist,getContext());
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        recyclerView.setAdapter(weatherAdapter);



        return view;
    }
    private void initdata() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               try{
                   OkHttpClient client = new OkHttpClient();
                   com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                           .url(StaticClass.Weather_URL+City)
                           .build();

                   Response response = client.newCall(request).execute();
                   responseData = response.body().string();
                   Message message = new Message();
                   message.what = 1;
                   handler.sendMessage(message);

               }catch (Exception e){
                   e.printStackTrace();
               }
           }
       }).start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    parseXMLWithPull(responseData);

                    /*Weather weather = new Weather();
                    weather.setLow(low);
                    weather.setDate(date);
                    weather.setHigh(high);
                    weather.setType(type);
                    weather.setFengxiang(fengxiang);
                    mweatherlist.add(weather);*/

                 /*   UtilLog.d(mweatherlist.get(0).getDate());
                    UtilLog.d(mweatherlist.get(0).getType());
                    UtilLog.d(mweatherlist.get(0).getLow());
                    UtilLog.d(mweatherlist.get(0).getHigh());
                    UtilLog.d(mweatherlist.get(0).getFengxiang());*/



            }
        }
    };
    private void parseXMLWithPull(String xmldata) {
     try{
         XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
         XmlPullParser xmlPullParser = factory.newPullParser();
         xmlPullParser.setInput(new StringReader(xmldata));
         int eventType = xmlPullParser.getEventType();
         String city ="";
         String updatetime = "";
         String shidu ="";
         String sunrise_1 ="";
         String sunset_1 ="";
         String wendu ="";
         //forecast
         date="";
         low="";
         high="";
         type="";
         fengxiang="";
         while(eventType !=XmlPullParser.END_DOCUMENT){
             String nodeName =xmlPullParser.getName();
             switch (eventType){
                 case XmlPullParser.START_TAG:{
                     //今日
                     if("city".equals(nodeName)){
                         city=xmlPullParser.nextText();
                     }else if("updatetime".equals(nodeName)){
                         updatetime=xmlPullParser.nextText();
                     }else if("wendu".equals(nodeName)){
                         wendu=xmlPullParser.nextText();
                     }else if("shidu".equals(nodeName)){
                         shidu=xmlPullParser.nextText();
                     }else if("sunrise_1".equals(nodeName)){
                         sunrise_1=xmlPullParser.nextText();
                     } else if("sunset_1".equals(nodeName)){
                         sunset_1=xmlPullParser.nextText();
                     }
                     //forecast
                     else if("date".equals(nodeName)){
                         date=xmlPullParser.nextText();
                     }else if("low".equals(nodeName)){
                         low=xmlPullParser.nextText();
                     } else if("high".equals(nodeName)){
                         high=xmlPullParser.nextText();
                     }else if("type".equals(nodeName)){
                         type=xmlPullParser.nextText();
                     }else if("fengxiang".equals(nodeName)){
                         fengxiang=xmlPullParser.nextText();
                     }
                     break;
                 }
                 //完成解析某个节点
                 case XmlPullParser.END_TAG:{
                     if("resp".equals(nodeName)){
                         /*UtilLog.d(city);
                         UtilLog.d(updatetime);
                         UtilLog.d(wendu);
                         UtilLog.d(shidu);
                         UtilLog.d(sunrise_1);
                         UtilLog.d(sunset_1);*/
                     Cityname.setText(city);
                     time.setText(updatetime);
                     tem.setText(wendu+"°C");
                     humidity.setText("湿度:"+shidu);
                     Sunset.setText("日出："+sunrise_1);
                     Sunrise.setText("日落："+sunset_1);
                     }
                     else if("weather".equals(nodeName)){

                          /*UtilLog.d(date);
                         UtilLog.d(high);
                         UtilLog.d(low);
                         UtilLog.d(type);
                         UtilLog.d(fengxiang);*/
                     }
                     break;
                 }
                 default:
                     break;
             }
             eventType=xmlPullParser.next();
         }
     }catch (Exception e){
         e.printStackTrace();
     }
    }
}
