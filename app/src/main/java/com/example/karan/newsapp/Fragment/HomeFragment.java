package com.example.karan.newsapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import com.example.karan.newsapp.Adapter.ViewPagerAdapter;
import com.example.karan.newsapp.R;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private ViewPagerAdapter mAdapter;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = view.findViewById(R.id.viewPagerHome);
        tabLayout = view.findViewById(R.id.tab_home);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        tabLayout.setupWithViewPager(viewPager);
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        setTab();
        onTabSelectedListener();
    }

    private void setTab() {
        View viewAtTab0 = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout, null);
        TextView tvTitle0 = viewAtTab0.findViewById(R.id.tab_title_text);
        tvTitle0.setText("बड़ी खबरें");
        tabLayout.getTabAt(0).setCustomView(viewAtTab0);

        View viewAtTab1 = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout, null);
        TextView tvTitle1 = viewAtTab1.findViewById(R.id.tab_title_text);
        tvTitle1.setText("मेरा गांव मेरा देश");
        tabLayout.getTabAt(1).setCustomView(viewAtTab1);

//        View viewAtTab2 = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout, null);
//        TextView tvTitle2 = viewAtTab2.findViewById(R.id.tab_title_text);
//        tvTitle2.setText("Video");
//        tabLayout.getTabAt(2).setCustomView(viewAtTab2);


    }

    private void onTabSelectedListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    TextView textView = view.findViewById(R.id.tab_title_text);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    TextView textView = view.findViewById(R.id.tab_title_text);
                    textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
