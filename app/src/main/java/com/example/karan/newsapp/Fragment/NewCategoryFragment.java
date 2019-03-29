package com.example.karan.newsapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import com.example.karan.newsapp.Libs.Utils;
import com.example.karan.newsapp.Fragment.NewsFragment;
import com.example.karan.newsapp.Model.Category;
import com.example.karan.newsapp.Network.ApiCalls;
import com.example.karan.newsapp.NewsActivity;
import com.example.karan.newsapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewCategoryFragment extends Fragment {

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;
    private CardView cardView5;
    private CardView cardView6;
    private CardView cardView7;
    private CardView cardView8;
    private CardView cardView9;

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;

    private ShimmerFrameLayout shimmerViewContainer;
    private ConstraintLayout clMain;
    private List<Category> categoryList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_category, container, false);

        cardView1 = view.findViewById(R.id.card_view_1);
        cardView2 = view.findViewById(R.id.card_view_2);
        cardView3 = view.findViewById(R.id.card_view_3);
        cardView4 = view.findViewById(R.id.card_view_4);
        cardView5 = view.findViewById(R.id.card_view_5);
        cardView6 = view.findViewById(R.id.card_view_6);
        cardView7 = view.findViewById(R.id.card_view_7);
        cardView8 = view.findViewById(R.id.card_view_8);
        cardView9 = view.findViewById(R.id.card_view_9);

        iv1 = view.findViewById(R.id.iv_1);
        iv2 = view.findViewById(R.id.iv_2);
        iv3 = view.findViewById(R.id.iv_3);
        iv4 = view.findViewById(R.id.iv_4);
        iv5 = view.findViewById(R.id.iv_5);
        iv6 = view.findViewById(R.id.iv_6);
        iv7 = view.findViewById(R.id.iv_7);
        iv8 = view.findViewById(R.id.iv_8);
        iv9 = view.findViewById(R.id.iv_9);

        tv1 = view.findViewById(R.id.tv_1);
        tv2 = view.findViewById(R.id.tv_2);
        tv3 = view.findViewById(R.id.tv_3);
        tv4 = view.findViewById(R.id.tv_4);
        tv5 = view.findViewById(R.id.tv_5);
        tv6 = view.findViewById(R.id.tv_6);
        tv7 = view.findViewById(R.id.tv_7);
        tv8 = view.findViewById(R.id.tv_8);
        tv9 = view.findViewById(R.id.tv_9);

        clMain = view.findViewById(R.id.cl_main);
        shimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        cardView1.setOnClickListener(mCorkyListener);
        cardView2.setOnClickListener(mCorkyListener);
        cardView3.setOnClickListener(mCorkyListener);
        cardView4.setOnClickListener(mCorkyListener);
        cardView5.setOnClickListener(mCorkyListener);
        cardView6.setOnClickListener(mCorkyListener);
        cardView7.setOnClickListener(mCorkyListener);
        getCategoriesApiCall();
        return view;
    }

    private void getCategoriesApiCall() {
        shimmerViewContainer.setVisibility(View.VISIBLE);
        shimmerViewContainer.startShimmerAnimation();
        ApiCalls.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                shimmerViewContainer.stopShimmerAnimation();
                shimmerViewContainer.setVisibility(View.GONE);
                if(response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    categoryList = response.body();
                    setDataInView(response.body());
                } else {
                    Utils.showSnackBarForUnsuccessfulResponse(response, getContext(), clMain);
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                if (t instanceof IOException && !Utils.isOnline()) {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.no_data_found_internet_failed), clMain);
                } else {
                    Utils.showSnackBarForError(getContext(), getResources().getString(R.string.something_went_wrong), clMain);
                }
            }
        });
    }

    private void setDataInView(List<Category> categoryList) {

        if(categoryList.size() == 1) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            cardView3.setVisibility(View.GONE);
            cardView4.setVisibility(View.GONE);
            cardView5.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 2) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.GONE);
            cardView4.setVisibility(View.GONE);
            cardView5.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 3) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.GONE);
            cardView5.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 4) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.GONE);
            cardView6.setVisibility(View.GONE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 5) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.GONE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 6) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 7) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView8.setVisibility(View.GONE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 8) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView8.setVisibility(View.VISIBLE);
            cardView9.setVisibility(View.GONE);
        } else if(categoryList.size() == 9) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
            cardView3.setVisibility(View.VISIBLE);
            cardView4.setVisibility(View.VISIBLE);
            cardView5.setVisibility(View.VISIBLE);
            cardView6.setVisibility(View.VISIBLE);
            cardView7.setVisibility(View.VISIBLE);
            cardView8.setVisibility(View.VISIBLE);
            cardView9.setVisibility(View.VISIBLE);
        }
        setImageAndTextInView(categoryList);
    }

    private void setImageAndTextInView(List<Category> categoryList) {
        if(categoryList.size() > 0) {
            tv1.setText(categoryList.get(0).getCatName());
            if(Utils.isValidString(categoryList.get(0).getCatImage())) {
                Picasso.with(getContext()).load(categoryList.get(0).getCatImage()).into(iv1, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("success","success");
                    }

                    @Override
                    public void onError() {
                        Log.e("failure","failure");
                    }
                });
            }
        }
        if(categoryList.size() > 1) {
            tv2.setText(categoryList.get(1).getCatName());
            if(Utils.isValidString(categoryList.get(1).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(1).getCatImage()).into(iv2);
        }
        if(categoryList.size() > 2) {
            tv3.setText(categoryList.get(2).getCatName());
            if(Utils.isValidString(categoryList.get(2).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(2).getCatImage()).into(iv3);

        }
        if(categoryList.size() > 3) {
            tv4.setText(categoryList.get(3).getCatName());
            if(Utils.isValidString(categoryList.get(3).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(3).getCatImage()).into(iv4);
        }
        if(categoryList.size() > 4) {
            tv5.setText(categoryList.get(4).getCatName());
            if(Utils.isValidString(categoryList.get(4).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(4).getCatImage()).into(iv5);

        }
        if(categoryList.size() > 5) {
            tv6.setText(categoryList.get(5).getCatName());
            if(Utils.isValidString(categoryList.get(5).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(5).getCatImage()).into(iv6);
        }
        if(categoryList.size() > 6) {
            tv7.setText(categoryList.get(6).getCatName());
            if(Utils.isValidString(categoryList.get(6).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(6).getCatImage()).into(iv7);

        }
        if(categoryList.size() > 7) {
            tv8.setText(categoryList.get(7).getCatName());
            if(Utils.isValidString(categoryList.get(7).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(7).getCatImage()).into(iv8);
        }
        if(categoryList.size() > 8) {
            tv9.setText(categoryList.get(8).getCatName());
            if(Utils.isValidString(categoryList.get(8).getCatImage()))
            Picasso.with(getContext()).load(categoryList.get(8).getCatImage()).into(iv9);
        }
    }


    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_view_1:
                    Intent intent = new Intent(getContext(), NewsActivity.class);
                    intent.putExtra("cat_id",categoryList.get(0).getCatId());
                    intent.putExtra("heading", categoryList.get(0).getCatName());
                    startActivity(intent);

                    break;

                case R.id.card_view_2:
                    Intent intent1 = new Intent(getContext(), NewsActivity.class);
                    intent1.putExtra("cat_id",categoryList.get(1).getCatId());
                    intent1.putExtra("heading", categoryList.get(1).getCatName());
                    startActivity(intent1);
                    break;

                case R.id.card_view_3:
                    Intent intent2 = new Intent(getContext(), NewsActivity.class);
                    intent2.putExtra("cat_id",categoryList.get(2).getCatId());
                    intent2.putExtra("heading", categoryList.get(2).getCatName());
                    startActivity(intent2);
                    break;

                case R.id.card_view_4:
                    Intent intent3 = new Intent(getContext(), NewsActivity.class);
                    intent3.putExtra("cat_id",categoryList.get(3).getCatId());
                    intent3.putExtra("heading", categoryList.get(3).getCatName());
                    startActivity(intent3);
                    break;

                case R.id.card_view_5:
                    Intent intent4 = new Intent(getContext(), NewsActivity.class);
                    intent4.putExtra("cat_id",categoryList.get(4).getCatId());
                    intent4.putExtra("heading", categoryList.get(4).getCatName());
                    startActivity(intent4);
                    break;

                case R.id.card_view_6:
                    Intent intent5 = new Intent(getContext(), NewsActivity.class);
                    intent5.putExtra("cat_id",categoryList.get(5).getCatId());
                    intent5.putExtra("heading", categoryList.get(5).getCatName());
                    startActivity(intent5);
                    break;

                case R.id.card_view_7:
                    Intent intent6 = new Intent(getContext(), NewsActivity.class);
                    intent6.putExtra("cat_id",categoryList.get(6).getCatId());
                    intent6.putExtra("heading", categoryList.get(6).getCatName());
                    startActivity(intent6);
                    break;

                case R.id.card_view_8:
                    Intent intent7 = new Intent(getContext(), NewsActivity.class);
                    intent7.putExtra("cat_id",categoryList.get(7).getCatId());
                    intent7.putExtra("heading", categoryList.get(7).getCatName());
                    startActivity(intent7);
                    break;

                case R.id.card_view_9:
                    Intent intent8 = new Intent(getContext(), NewsActivity.class);
                    intent8.putExtra("cat_id",categoryList.get(8).getCatId());
                    intent8.putExtra("heading", categoryList.get(8).getCatName());
                    startActivity(intent8);
                    break;
            }
        }
    };

}
