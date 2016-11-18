package com.virgil.databindingnews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virgil.databindingnews.adapter.NewsAdapter;
import com.virgil.databindingnews.bean.ResultBean;
import com.virgil.databindingnews.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    private Context mContext = this;
    private final static String NEWS_URL = "http://api.tianapi.com/keji/?key=3cd7cc6ab7736c7b29b2280b0f282c39&num=30&rand";
    private List<ResultBean.NewsBean> totalList;
    Gson gson = new Gson();
    ActivityMainBinding mBinding;
    NewsAdapter newsAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(mContext, "网络访问失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    mBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
                    mBinding.recyclerViewItemNewsNews.setLayoutManager(new LinearLayoutManager(mContext));
                    newsAdapter = new NewsAdapter(totalList, mContext);
                    mBinding.recyclerViewItemNewsNews.setAdapter(newsAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromHttp();//调用获取新闻列表数据的方法
    }

    /**
     * 网络访问获取新闻列表数据
     *
     * @return
     */
    public void getDataFromHttp() {
        OkHttpClient okhttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(NEWS_URL).build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //网络访问失败，发送消息 0
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String string = body.string();
                ResultBean resultBean = gson.fromJson(string, new TypeToken<ResultBean>() {
                }.getType());
                totalList = resultBean.getNewslist();
                //网络访问成功，发送消息 1
                handler.sendEmptyMessage(1);
            }
        });
    }
}
