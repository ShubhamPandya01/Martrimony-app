package com.example.matrimony_demo.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.matrimony_demo.fragment.UserListFragment;
import com.example.matrimony_demo.util.Constant;

public class GenderViewPagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[]{"Male", "Female"};
    private Context context;

    public GenderViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UserListFragment.getInstance(Constant.MALE);
        } else {
            return UserListFragment.getInstance(Constant.FEMALE);
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
