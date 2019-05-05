package com.example.mvvm.view.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> tabs;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> tabs) {
        super(fm);
        this.list = list;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
