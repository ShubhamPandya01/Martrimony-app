package com.example.matrimony_demo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.database.MyDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.cvActRegistration)
    CardView cvActRegistration;
    @BindView(R.id.cvActList)
    CardView cvActList;
    @BindView(R.id.cvActFavourite)
    CardView cvActFavorite;
    @BindView(R.id.cvActSearch)
    CardView cvActSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpActionBar(getString(R.string.lbl_dashboard), false);
        new MyDatabase(this).getWritableDatabase();
    }


    @OnClick(R.id.cvActRegistration)
    public void onCvActRegistrationClicked() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvActList)
    public void onCvActListClicked() {
        Intent intent = new Intent(this, ActivityUserListByGender.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvActFavourite)
    public void onCvActFavouriteClicked() {
        Intent intent = new Intent(this, FavoriteUserActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvActSearch)
    public void onCvActSearchClicked() {
        Intent intent = new Intent(this, SearchUserActivity.class);
        startActivity(intent);
    }
}