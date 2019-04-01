package com.example.karan.newsapp.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.karan.newsapp.Adapter.NewsAdapter;
import com.example.karan.newsapp.Adapter.TopNewsAdapter;
import com.example.karan.newsapp.Libs.Utils;
import com.example.karan.newsapp.Model.Article;
import com.example.karan.newsapp.Model.ImageSlider;
import com.example.karan.newsapp.Model.ImagesModel;
import com.example.karan.newsapp.Model.News;
import com.example.karan.newsapp.Network.ApiCalls;
import com.example.karan.newsapp.NewsActivity;
import com.example.karan.newsapp.R;
import com.example.karan.newsapp.ReadNewsActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;


public class NewsFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView rvNews;
    private RecyclerView rvTopNews;
    private LinearLayout llNewsImage;

    private NewsAdapter newsAdapter;
    private TopNewsAdapter topNewsAdapter;
    private List<News> newsList = new ArrayList<>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private ConstraintLayout clMain;
    private int pos;
    List<ImagesModel> imageList = new ArrayList<>();

    public NewsFragment() {}

    @SuppressLint("ValidFragment")
    public NewsFragment(int pos) {
        this.pos = pos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        swipeRefresh = view.findViewById(R.id.swipe_refresh_layout);
        rvNews = view.findViewById(R.id.rv_news);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        clMain = view.findViewById(R.id.cl_main);
        llNewsImage = view.findViewById(R.id.ll_news_image);
        sendPushToken();
        rvNews.setVisibility(View.GONE);
        addSwipeRefreshListeners();
        swipeRefresh.setVisibility(View.VISIBLE);
        return view;
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

        public void stopSwipeRefreshing() {
            if(swipeRefresh != null && swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }


//        swipeRefresh.setOnRefreshListener(()->{
//            swipeRefresh.setRefreshing(true);
////            mOnEventSuccessListener.onSuccess(true, 0);
//        });

    private void sendPushToken() {
        ApiCalls.updatePushToken( Utils.getPushToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    getNewsApiCall();
                }  else {
                    Utils.showSnackBarForUnsuccessfulResponse(response, getContext(), clMain);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t instanceof IOException && !Utils.isOnline()) {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.no_data_found_internet_failed), clMain);
                } else {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.something_went_wrong), clMain);
                }
            }
        });
    }

    private void getNewsApiCall() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        rvNews.setVisibility(View.GONE);
        newsList.clear();
        ApiCalls.getNews(pos).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null) {
                    if(response.body().getArticles() != null && response.body().getArticles().size() > 0) {
                        newsList.addAll((ArrayList) response.body().getArticles());
                        getImageList();
                    }
                } else {
                    Utils.showSnackBarForUnsuccessfulResponse(response, getContext(), clMain);
                }
                stopSwipeRefreshing();
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                if (t instanceof IOException && !Utils.isOnline()) {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.no_data_found_internet_failed), clMain);
                } else {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.something_went_wrong), clMain);
                }
                stopSwipeRefreshing();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void setDataInView() {

        if(imageList.size() > 0) {
            List<String> images = new ArrayList<>();
            List<Integer> imageNews = new ArrayList<>();
            for(int i=0;i<imageList.size();i++) {
                images.add(imageList.get(i).getSlider_image());
                imageNews.add(imageList.get(i).getSlider_news_id());
            }
//            images.add("https://tineye.com/images/widgets/mona.jpg");
            if(images.size() > 0) {
                llNewsImage.setVisibility(View.VISIBLE);
                Utils.addFragment(getFragmentManager(), R.id.ll_news_image, new ImageSliderFragment(getContext(),images, imageNews));
            } else {
                llNewsImage.setVisibility(View.GONE);
            }
        } else {
            llNewsImage.setVisibility(View.GONE);
        }

        rvNews.setVisibility(View.VISIBLE);
//        rvNews.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(newsList, getContext());
        rvNews.setAdapter(newsAdapter);
        rvNews.setNestedScrollingEnabled(false);

    }

    private void getImageList() {
        ApiCalls.getSliderImages(pos).enqueue(new Callback<ImageSlider>() {
            @Override
            public void onResponse(Call<ImageSlider> call, Response<ImageSlider> response) {
                if(response.isSuccessful() && response.body() != null) {
                    if (response.body().getNewslistslider() != null && response.body().getNewslistslider().size() > 0) {
                        imageList = response.body().getNewslistslider();
                    }
                    setDataInView();
                }
            }

            @Override
            public void onFailure(Call<ImageSlider> call, Throwable t) {

            }
        });
    }



    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    @Override
   public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }
}
