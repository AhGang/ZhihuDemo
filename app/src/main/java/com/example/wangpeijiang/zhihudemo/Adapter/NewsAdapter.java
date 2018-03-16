package com.example.wangpeijiang.zhihudemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wangpeijiang.zhihudemo.R;
import com.example.wangpeijiang.zhihudemo.Utils.UtilLog;
import com.example.wangpeijiang.zhihudemo.entity.BeforeNews;
import com.example.wangpeijiang.zhihudemo.entity.LatestNews;

import java.util.List;

/**
 * Created by wangpeijiang on 2017/10/15.
 */

public  class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<LatestNews> mData;
    private Context mContext;//上下文
    private List<BeforeNews> mbeforeData;
    private RecyclerView recyclerView;



    static class ViewHolder extends RecyclerView.ViewHolder{
        View  newsview;

        TextView title;//标题
        TextView date;//日期
        ImageView item_image;//图片
        View headView;

        public ViewHolder(View View) {
            super(View);
            newsview =  View;

            title= (TextView) View.findViewById(R.id.item_title);
            date= (TextView) View.findViewById(R.id.date);
            item_image= (ImageView) View.findViewById(R.id.item_image);

        }
    }
     public NewsAdapter(List<LatestNews> data, Context context) {

        //构造方法，用于接收上下文和展示数据

        this.mData = data;
         this.mContext=context;
    }

    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (headView!=null && viewType==TYPE_HEADER) return new ViewHolder(headView);
        View view =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.latestrecycler_item,parent,false);

        final ViewHolder holder = new ViewHolder(view);
        //点击事件
        holder.newsview.setOnClickListener(new View.OnClickListener(){

            @Override
                    public void onClick(View v){
                int position =holder.getAdapterPosition();
                LatestNews news =mData.get(position);
                //获取id并将id转为string
               String id=String.valueOf(mData.get(position).getStories().get(position).getId());

                //隐式启动
                Intent intent = new Intent("ACTION_START");
                intent.putExtra("News_id",id);
                UtilLog.d(id);
                mContext.startActivity(intent);
                UtilLog.d("u clicked view");
            }
        });
        holder.item_image.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                int position =holder.getAdapterPosition();
                LatestNews news =mData.get(position);
                String id=String.valueOf(mData.get(position).getStories().get(position).getId());

                //隐式启动
                Intent intent = new Intent("ACTION_START");
                intent.putExtra("News_id",id);
                UtilLog.d(id);
                mContext.startActivity(intent);
                UtilLog.d("u clicked view");

            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final LatestNews news = mData.get(position);
        holder.title.setText(news.getStories().get(position).getTitle());
        holder.date.setText(news.getDate());
        Glide.with(mContext).load(news.getStories().get(position).getImages().get(0)).into(holder.item_image);
    }

    public int getItemCount() {

        return mData.size() ;
    }




    //Banner
    public static final int TYPE_HEADER = 0;//显示headvuiew
    public static final int TYPE_NORMAL = 1;//显示普通的item
    //Banner
    private View headView;
    public void setHeadView(View headView){
        this.headView=headView;
        notifyItemInserted(0);
    }
    public View getHeadView(){
        return headView;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView==null)
            return TYPE_NORMAL;
        if (position==0)
            return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position=holder.getLayoutPosition();
        return headView==null? position:position-1;
    }
}
