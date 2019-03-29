package com.example.karan.newsapp.Network;

import com.example.karan.newsapp.Model.Article;
import com.example.karan.newsapp.Model.Category;
import com.example.karan.newsapp.Model.ImageSlider;
import com.example.karan.newsapp.Model.News;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public class ApiService {

    private static NewsApiServices newsApiServices;

    static synchronized NewsApiServices getNewsApiServices() {
        if (newsApiServices == null) {
            newsApiServices = ApiClients.getClient().create(NewsApiServices.class);
        }
        return newsApiServices;
    }

    public interface NewsApiServices {

        @GET("news/api/newsapi.php")
        Call<Article> getNews(@Query("action") String action, @Query("subsection") Integer subSection);

        @GET("news/api/newsapi.php")
        Call<List<Category>> getCategories(@Query("action") String action);

        @GET("news/api/newsapi.php")
        Call<Article> getCategoryDetail(@Query("action") String action, @Query("cat_id") Integer catId);

        @GET("news/api/newsapi.php")
        Call<Article> getReadNews(@Query("action") String action, @Query("id") Integer newsId);

        @GET("news/api/newsapi.php")
        Call<ImageSlider> getSliderImages(@Query("action") String action, @Query("subsection") Integer subSection);

        @POST("news/api/newsapi.php")
        Call<ResponseBody> updatePushToken(@Query("action") String action, @Query("device_id") String deviceId);
    }
}
