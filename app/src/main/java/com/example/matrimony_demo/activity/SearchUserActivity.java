package com.example.matrimony_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.adapter.UserListAdapter;
import com.example.matrimony_demo.database.TableUser;
import com.example.matrimony_demo.fragment.UserListFragment;
import com.example.matrimony_demo.model.UserModel;
import com.example.matrimony_demo.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserActivity extends BaseActivity {

    @BindView(R.id.etUserSearch)
    EditText etUserSearch;
    @BindView(R.id.rcvUsers)
    RecyclerView rcvUsers;

    ArrayList<UserModel> userList = new ArrayList<>();
    ArrayList<UserModel> tempUserList = new ArrayList<>();
    UserListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);
        setUpActionBar(getString(R.string.lbl_search_user), true);
        setAdapter();
        setSearchUser();
    }

    void resetAdapter() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    void setSearchUser() {
        etUserSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tempUserList.clear();
                if (charSequence.toString().length() > 0) {
                    for (int j = 0; j < userList.size(); j++) {
                        if (userList.get(j).getFirstName().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || userList.get(j).getMiddleName().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || userList.get(j).getLastName().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || userList.get(j).getEmail().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || userList.get(j).getMobileNumber().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            tempUserList.add(userList.get(j));
                        }
                    }
                }
                if (charSequence.toString().length() == 0 && tempUserList.size() == 0) {
                    tempUserList.addAll(userList);
                }
                resetAdapter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void showAlertDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure want to delete this user?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    int deletedUserId = new TableUser(SearchUserActivity.this).deleteUserById(userList.get(position).getUserId());
                    if (deletedUserId > 0) {
                        Toast.makeText(SearchUserActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        userList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(0, userList.size());

                    } else {
                        Toast.makeText(SearchUserActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    void setAdapter() {
        rcvUsers.setLayoutManager(new GridLayoutManager(this, 1));
        userList.addAll(new TableUser(this).getUserList());
        tempUserList.addAll(userList);
        adapter = new UserListAdapter(this, userList, new UserListAdapter.OnViewClickListener() {
            @Override
            public void OnDeleteClick(int position) {
                showAlertDialog(position);
            }

            @Override
            public void onFavoriteClick(int position) {
                int lastUpdatedUserID = new TableUser(SearchUserActivity.this).updateFavoriteStatus(userList.get(position).getIsFavorite() == 0 ? 1 : 0, userList.get(position).getUserId());
                if (lastUpdatedUserID > 0) {
                    userList.get(position).setIsFavorite(userList.get(position).getIsFavorite() == 0 ? 1 : 0);
                    adapter.notifyItemChanged(position);
                }
            }

            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(SearchUserActivity.this, RegistrationActivity.class);
                intent.putExtra(Constant.USER_OBJECT, userList.get(position));
                startActivity(intent);
            }
        });
        rcvUsers.setAdapter(adapter);
    }
}