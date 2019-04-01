package com.example.karan.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.karan.newsapp.R;
import com.example.karan.newsapp.ReadNewsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;
    private List<Integer> imagesNews = new ArrayList<>();

    public SlidingImageAdapter(Context context, ArrayList<String> images, List<Integer> imagesNews) {
        this.context = context;
        this.images=images;
        this.imagesNews = imagesNews;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }



    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slide_image_layout, view, false);

        assert imageLayout != null;
        ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


//        imageView.setImageResource(images.get(position));
        Picasso.with(context).load(images.get(position)).into(imageView, new Callback() {

            @Override
            public void onSuccess() {
                Log.e("success","success");
            }

            @Override
            public void onError() {
                Log.e("failure","failure");
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagesNews != null && imagesNews.size() > 0) {
                    Intent intent = new Intent(context, ReadNewsActivity.class);
                    intent.putExtra("news_id", imagesNews.get(position));
                    context.startActivity(intent);
                }
            }
        });


        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
