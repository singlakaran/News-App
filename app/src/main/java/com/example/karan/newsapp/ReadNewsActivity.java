package com.example.karan.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.karan.newsapp.Fragment.ImageSliderFragment;
import com.example.karan.newsapp.Libs.Utils;
import com.example.karan.newsapp.Model.Article;
import com.example.karan.newsapp.Model.News;
import com.example.karan.newsapp.Network.ApiCalls;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadNewsActivity extends AppCompatActivity {

    private LinearLayout tvNewsImage;
    private TextView tvHeading;
    private TextView tvSubHeading;
    private ImageButton backButton;
    private TextView tvShare;
    private TextView tvBottomTitle;
    private LinearLayout llBottom;

    private String imageUrl;
    private String heading;
    protected String subHeading;
    private int newsId;
    private RelativeLayout clMain;
    private String  youtubeUrl;
    List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        tvNewsImage = findViewById(R.id.tv_news_image);
        tvHeading = findViewById(R.id.tv_heading);
        tvSubHeading = findViewById(R.id.tv_sub_heading);
        backButton = findViewById(R.id.back_button);
        tvShare = findViewById(R.id.tv_share);
        clMain = findViewById(R.id.cl_main);
        tvBottomTitle = findViewById(R.id.tv_bottom_title);
        llBottom = findViewById(R.id.ll_bottom);

        newsId = getIntent().getIntExtra("news_id", -1);
//        imageUrl = getIntent().getStringExtra("image_url");
//        heading = getIntent().getStringExtra("heading");
//        subHeading = getIntent().getStringExtra("sub_heading");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=" + "com.example.karan.newsapp";
                String uri = heading + "\n" +"Hey check out my app at: " + url;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, uri);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share using"));
            }
        });

        llBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
                startActivity(browserIntent);
            }
        });
        getDataFromApi();
    }

    private void getDataFromApi() {
        ApiCalls.getReadNews(newsId).enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful() && response.body() != null) {
//                    imageUrl = response.body().getArticles().getImage();
                    heading = response.body().getArticles().get(0).getTitle();
                    subHeading = response.body().getArticles().get(0).getDescription();
                    if(Utils.isValidString(response.body().getArticles().get(0).getYoutubeurl())) {
                        youtubeUrl = response.body().getArticles().get(0).getYoutubeurl();
                        slideUp(llBottom);
                        tvBottomTitle.setText(heading);
                    }
                    if(response.body().getNewslistslider() != null && response.body().getNewslistslider().size() > 0) {
                        for(int i=0;i<response.body().getNewslistslider().size();i++) {
                            images.add(response.body().getNewslistslider().get(i).getSlider_image());
                        }
                    }
                    setDataInView();
                } else {
                    Utils.showSnackBarForUnsuccessfulResponse(response, getApplicationContext(), clMain);
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                if (t instanceof IOException && !Utils.isOnline()) {
                    Utils.showSnackBarForError(getApplicationContext(), getResources().getString(R.string.no_data_found_internet_failed), clMain);
                } else {
                    Utils.showSnackBarForError(getApplicationContext(), getResources().getString(R.string.something_went_wrong), clMain);
                }
            }
        });
    }

    private void setDataInView() {

//        Picasso.with(this).load(imageUrl).fit().centerCrop().placeholder(R.drawable.ic_clear_all_black_24dp)
//                .into(ivNewsImage);
        if(images.size() > 0) {
            Utils.addFragment(ReadNewsActivity.this.getSupportFragmentManager(), R.id.tv_news_image, new ImageSliderFragment(this, images, null));
        } else {
            tvNewsImage.setVisibility(View.GONE);
        }
        tvHeading.setText(heading);
        tvSubHeading.setText(subHeading);

    }

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                100,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }




}
