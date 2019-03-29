package com.example.karan.newsapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.karan.newsapp.Fragment.NewsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new NewsFragment(1);
            case 1:
                return new NewsFragment(2);
            default:
                return new NewsFragment(3);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}
