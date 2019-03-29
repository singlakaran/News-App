package com.example.karan.newsapp.Model;

import java.util.List;

public class Article {

    List<News> newslist;
    List<ImagesModel> newslistslider;

    public List<News> getArticles() {
        return newslist;
    }

    public void setArticles(List<News> articles) {
        this.newslist = articles;
    }

    public List<ImagesModel> getNewslistslider() {
        return newslistslider;
    }

    public void setNewslistslider(List<ImagesModel> newslistslider) {
        this.newslistslider = newslistslider;
    }
}
