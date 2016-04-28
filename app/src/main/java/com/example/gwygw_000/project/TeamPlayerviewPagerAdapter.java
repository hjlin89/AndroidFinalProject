package com.example.gwygw_000.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gwygw_000 on 2016/4/14.
 */
public class TeamPlayerviewPagerAdapter extends FragmentStatePagerAdapter {
    final List<Fragment> mFragmentList = new ArrayList<>();
    final List<String> mFragmentTitle = new ArrayList<>();

    public TeamPlayerviewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentTitle.add(title);
        mFragmentList.add(fragment);
    }
}
