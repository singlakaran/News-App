package com.example.karan.newsapp.Network;

import com.example.karan.newsapp.Model.Article;
import com.example.karan.newsapp.Model.Category;
import com.example.karan.newsapp.Model.ImageSlider;
import com.example.karan.newsapp.Model.News;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.List;

public class ApiCalls {

    public static Call<Article> getNews(Integer subSection){
        return ApiService.getNewsApiServices().getNews("Getnews", subSection);
    }

    public static Call<List<Category>> getCategories() {
        return ApiService.getNewsApiServices().getCategories("Category");
    }

    public static Call<Article> getCategoryDetail(Integer catId) {
        return ApiService.getNewsApiServices().getCategoryDetail("CategoryDetail", catId);
    }

    public static Call<Article> getReadNews(Integer newsId) {
        return ApiService.getNewsApiServices().getReadNews("Getnewsid", newsId);
    }

    public static Call<ImageSlider> getSliderImages(Integer subSection) {
        return ApiService.getNewsApiServices().getSliderImages("Getnewsslider" , subSection);
    }

    public static Call<ResponseBody> updatePushToken(String pushToken) {
        return ApiService.getNewsApiServices().updatePushToken("InsertDeviceid",pushToken);
    }
}
