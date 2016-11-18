package com.virgil.databindingnews.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.virgil.databindingnews.R;
import com.virgil.databindingnews.bean.ResultBean;
import com.virgil.databindingnews.databinding.ItemNewsBinding;

import java.util.List;

/**
 * Created by virgil on 2016/11/18 09:52.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<ResultBean.NewsBean> totalList;
    private Context mContext;

    public NewsAdapter(List<ResultBean.NewsBean> list, Context context) {
        totalList = list;
        mContext = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        //给ViewHolder绑定
        holder.bind(totalList.get(position));
        Glide.with(mContext).load(totalList.get(position).getPicUrl()).into(holder.imageView_itemNews_pic);
    }

    @Override
    public int getItemCount() {
        return totalList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        //绑定类
        private ItemNewsBinding itemNewsBinding;
        private ImageView imageView_itemNews_pic;

        public NewsViewHolder(View itemView) {
            super(itemView);
            //为每一个item设置
            itemNewsBinding = DataBindingUtil.bind(itemView);
            imageView_itemNews_pic = (ImageView) itemView.findViewById(R.id.imageView_itemNews_pic);
        }

        public void bind(@NonNull ResultBean.NewsBean news) {
            itemNewsBinding.setNews(news);
        }
    }
}
