package com.example.karan.newsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.karan.newsapp.Adapter.NewsAdapter;
import com.example.karan.newsapp.Libs.Utils;
import com.example.karan.newsapp.Model.Article;
import com.example.karan.newsapp.Model.News;
import com.example.karan.newsapp.Network.ApiCalls;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvNews;

    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout clMain;
    private TextView tvHeading;
    private ImageView backbtn;

    private int catId;
    private String heading;

@Override
protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    swipeRefresh = findViewById(R.id.swipe_refresh_layout);
    rvNews = findViewById(R.id.rv_news);
    mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
    clMain = findViewById(R.id.cl_main);
    tvHeading = findViewById(R.id.tv_heading);
    backbtn = findViewById(R.id.back_button);

    catId = getIntent().getIntExtra("cat_id", -1);
    heading = getIntent().getStringExtra("heading");
    getNewsApiCall();
    tvHeading.setText(heading);
    rvNews.setVisibility(View.GONE);

    addSwipeRefreshListeners();
    swipeRefresh.setVisibility(View.VISIBLE);

    backbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    }

    private void addSwipeRefreshListeners() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getNewsApiCall();
            }
        });

        rvNews.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                    swipeRefresh.setEnabled(true);
                } else {
                    swipeRefresh.setEnabled(false);
                }
            }
        });
    }

    private void getNewsApiCall() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        rvNews.setVisibility(View.GONE);
        newsList.clear();
        ApiCalls.getCategoryDetail(catId).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if( response.body().getArticles() != null && response.body().getArticles().size() > 0) {
                        newsList.addAll(response.body().getArticles());
                        setDataInView();
                    }
                } else {
                    Utils.showSnackBarForUnsuccessfulResponse(response, NewsActivity.this, clMain);
                }
                stopSwipeRefreshing();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                if (t instanceof IOException && !Utils.isOnline()) {
                    Utils.showSnackBarForError(NewsActivity.this, getResources().getString(R.string.no_data_found_internet_failed), clMain);
                } else {
                    Utils.showSnackBarForError(NewsActivity.this, getResources().getString(R.string.something_went_wrong), clMain);
                }
                stopSwipeRefreshing();
            }
        });
    }

    public void stopSwipeRefreshing() {
        if(swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @SuppressLint("WrongConstant")
    private void setDataInView() {

        rvNews.setVisibility(View.VISIBLE);
        rvNews.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(newsList, this);
        rvNews.setAdapter(newsAdapter);
        rvNews.setNestedScrollingEnabled(false);
    }

}
