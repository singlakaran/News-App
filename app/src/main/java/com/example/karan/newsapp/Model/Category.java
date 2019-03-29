package com.example.karan.newsapp.Model;

public class Category {

    int cat_id;
    String cat_name;
    String cat_description;
    String cat_image;

    public int getCatId() {
        return cat_id;
    }

    public void setCatId(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCatName() {
        return cat_name;
    }

    public void setCatName(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCatDescription() {
        return cat_description;
    }

    public void setCatDescription(String cat_description) {
        this.cat_description = cat_description;
    }

    public String getCatImage() {
        return cat_image;
    }

    public void setCatImage(String cat_image) {
        this.cat_image = cat_image;
    }
}
