package com.example.matrimony_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.adapter.GenderViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class ActivityUserListByGender extends BaseActivity {

    @BindView(R.id.tlGenders)
    TabLayout tlGenders;
    @BindView(R.id.vpUserList)
    ViewPager vpUserList;
    GenderViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_wise_list);
        ButterKnife.bind(this);
        setUpActionBar(getString(R.string.lbl_user_list), true);
        setUpViewPagerAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ivFavouriteUserActivity) {
            Intent intent = new Intent(this, FavoriteUserActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    void setUpViewPagerAdapter() {
        adapter = new GenderViewPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, this);
        vpUserList.setAdapter(adapter);
        tlGenders.setupWithViewPager(vpUserList);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityUserListByGender.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
