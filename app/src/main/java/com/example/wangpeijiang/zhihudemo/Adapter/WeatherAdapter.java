package com.example.wangpeijiang.zhihudemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.entity.Weather;

import java.util.List;

/**
 * Created by wangpeijiang on 2017/12/26.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
    private List<Weather> mweatherlist;
    private Context mContext;//上下文


    static class ViewHolder extends RecyclerView.ViewHolder{
        View  weatherview;
        TextView weather_city;
        TextView weather_date;
        TextView weather_low;
        TextView weather_high;
        TextView weather_type;
        TextView weather_feng;



        public ViewHolder(View View) {
            super(View);
            weatherview =  View;

            weather_city= (TextView) View.findViewById(R.id.weather_city);
            weather_date= (TextView) View.findViewById(R.id.weather_date);
            weather_low= (TextView) View.findViewById(R.id.weather_low);
            weather_high= (TextView) View.findViewById(R.id.weather_high);
            weather_type= (TextView) View.findViewById(R.id.weather_type);
            weather_feng= (TextView) View.findViewById(R.id.weather_feng);

        }
    }
    public WeatherAdapter(List<Weather> data, Context context) {
        //构造方法，用于接收上下文和展示数据
        this.mweatherlist = data;
        this.mContext=context;
    }
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        final Weather weather = mweatherlist.get(position);

        holder.weather_date.setText(weather.getDate());
        holder.weather_low.setText(weather.getLow());
        holder.weather_high.setText(weather.getHigh());
        holder.weather_type.setText(weather.getType());
        holder.weather_feng.setText(weather.getFengxiang());


    }

    @Override
    public int getItemCount() {
        return mweatherlist.size();
    }

}
