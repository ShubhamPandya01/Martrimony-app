package com.example.matrimony_demo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.adapter.UserListAdapter;
import com.example.matrimony_demo.database.TableUser;
import com.example.matrimony_demo.model.UserModel;
import com.example.matrimony_demo.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteUserActivity extends BaseActivity {


    @BindView(R.id.rcvUserList)
    RecyclerView rcvUserList;

    @BindView(R.id.tvUserListDataNotFound)
    TextView tvUserListDataNotFound;

    ArrayList<UserModel> userList = new ArrayList<>();
    UserListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        setUpActionBar(getString(R.string.favourite_list_activity_name), true);
        setAdapter();
    }

    void showAlertDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure want to delete this user?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    int deletedUserId = new TableUser(FavoriteUserActivity.this).deleteUserById(userList.get(position).getUserId());
                    if (deletedUserId > 0) {
                        Toast.makeText(FavoriteUserActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        userList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(0, userList.size());

                    } else {
                        Toast.makeText(FavoriteUserActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    void setAdapter() {
        userList.addAll(new TableUser(this).getFavoriteUserList());

        if(checkAndVisibleView()){
            rcvUserList.setLayoutManager(new GridLayoutManager(this, 1));

            adapter = new UserListAdapter(this, userList, new UserListAdapter.OnViewClickListener() {
                @Override
                public void OnDeleteClick(int position) {
                    showAlertDialog(position);
                }

                @Override
                public void onFavoriteClick(int position) {
                    int lastUpdatedUserID = new TableUser(FavoriteUserActivity.this).updateFavoriteStatus(0, userList.get(position).getUserId());
                    if (lastUpdatedUserID > 0) {
                        sendFavoriteChangeBroadCast(userList.get(position));

                        userList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(0, userList.size());
                        checkAndVisibleView();
                    }
                }

                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(FavoriteUserActivity.this, RegistrationActivity.class);
                    intent.putExtra(Constant.USER_OBJECT, userList.get(position));
                    startActivity(intent);
                }
            });
            rcvUserList.setAdapter(adapter);
        }

    }

    boolean checkAndVisibleView() {

        boolean dataFound = false;
        if (userList.size() > 0) {
            tvUserListDataNotFound.setVisibility(View.GONE);
            rcvUserList.setVisibility(View.VISIBLE);
            dataFound = true;
        } else {
            tvUserListDataNotFound.setVisibility(View.VISIBLE);
            rcvUserList.setVisibility(View.GONE);
            dataFound =false;
        }

        return dataFound;
    }

    void sendFavoriteChangeBroadCast(UserModel userModel) {
        Intent intent = new Intent(Constant.FAVORITE_CHANGE_FILTER);
        intent.putExtra(Constant.USER_ID, userModel);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}