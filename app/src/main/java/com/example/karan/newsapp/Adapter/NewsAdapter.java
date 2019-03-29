package com.example.karan.newsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.karan.newsapp.Model.News;
import com.example.karan.newsapp.R;
import com.example.karan.newsapp.ReadNewsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> newsList;
    private Context context;

    public NewsAdapter(List<News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new ViewHolderCard(view);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderCard viewHolderCard = (ViewHolderCard) holder;
        viewHolderCard.onBind(position);
    }

    public class ViewHolderCard extends RecyclerView.ViewHolder {

//        @BindView(R.id.iv_news)
        ImageView ivNews;
//        @BindView(R.id.tv_heading)
        TextView tvHeading;
//        @BindView(R.id.tv_sub_heading)
        TextView tvSubHeading;

        public ViewHolderCard(View view) {
            super(view);
//            ButterKnife.bind(this, view);
            ivNews = view.findViewById(R.id.iv_news);
            tvHeading = view.findViewById(R.id.tv_heading);
            tvSubHeading = view.findViewById(R.id.tv_sub_heading);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ReadNewsActivity.class);
                    intent.putExtra("news_id",newsList.get(getAdapterPosition()).getId());
//                    intent.putExtra("image_url",newsList.get(getAdapterPosition()).getUrl());
//                    intent.putExtra("heading", newsList.get(getAdapterPosition()).getTitle());
//                    intent.putExtra("sub_heading", newsList.get(getAdapterPosition()).getContent());
                    context.startActivity(intent);
                }
            });
        }


        public void onBind(int pos){

            Picasso.with(context).load(newsList.get(pos).getUrl()).fit().centerCrop().placeholder(R.drawable.ic_clear_all_black_24dp)
                .into(ivNews);

            tvHeading.setText(newsList.get(pos).getTitle());
            tvSubHeading.setText(newsList.get(pos).getDescription());


        }

    }

}