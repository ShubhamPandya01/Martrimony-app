package com.example.matrimony_demo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.activity.RegistrationActivity;
import com.example.matrimony_demo.adapter.UserListAdapter;
import com.example.matrimony_demo.database.TableUser;
import com.example.matrimony_demo.model.UserModel;
import com.example.matrimony_demo.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserListFragment extends Fragment {

    @BindView(R.id.rcvUserList)
    RecyclerView rcvUserList;

    ArrayList<UserModel> userList = new ArrayList<>();
    UserListAdapter adapter;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    MyFavoriteChange myFavoriteChange = new MyFavoriteChange();

    public static UserListFragment getInstance(int gender) {
        UserListFragment fragment = new UserListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.GENDER, gender);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, null);
        ButterKnife.bind(this, v);
        setAdapter();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myFavoriteChange, new IntentFilter(Constant.FAVORITE_CHANGE_FILTER));
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myFavoriteChange);
    }

    void openAddUserScreen() {
        Intent intent = new Intent(getActivity(), RegistrationActivity.class);
        startActivity(intent);
    }

    void showAlertDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure want to delete this user?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    int deletedUserId = new TableUser(UserListFragment.this.getActivity()).deleteUserById(userList.get(position).getUserId());
                    if (deletedUserId > 0) {
                        Toast.makeText(UserListFragment.this.getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        userList.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(0, userList.size());
                        UserListFragment.this.checkAndVisibleView();
                    } else {
                        Toast.makeText(UserListFragment.this.getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


    void setAdapter() {

        userList.addAll(new TableUser(getActivity()).getUserListByGender(getArguments().getInt(Constant.GENDER)));

        if(checkAndVisibleView()){
            rcvUserList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            adapter = new UserListAdapter(getActivity(), userList, new UserListAdapter.OnViewClickListener() {
                @Override
                public void OnDeleteClick(int position) {
                    showAlertDialog(position);
                }

                @Override
                public void onFavoriteClick(int position) {
                    int lastUpdatedUserID = new TableUser(getActivity()).updateFavoriteStatus(userList.get(position).getIsFavorite() == 0 ? 1 : 0, userList.get(position).getUserId());
                    if (lastUpdatedUserID > 0) {
                        userList.get(position).setIsFavorite(userList.get(position).getIsFavorite() == 0 ? 1 : 0);
                        adapter.notifyItemChanged(position);
                    }
                }

                @Override
                public void OnItemClick(int position) {
                    Intent intent = new Intent(getActivity(), RegistrationActivity.class);
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
            tvNoDataFound.setVisibility(View.GONE);
            rcvUserList.setVisibility(View.VISIBLE);
            dataFound = true;
        } else {
            tvNoDataFound.setVisibility(View.VISIBLE);
            rcvUserList.setVisibility(View.GONE);
            dataFound =false;
        }

        return dataFound;
    }

    @OnClick(R.id.btnAddUser)
    public void onViewClicked() {
        openAddUserScreen();
    }

    class MyFavoriteChange extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Constant.USER_ID)) {
                UserModel model = (UserModel) intent.getSerializableExtra(Constant.USER_ID);
                checkUserIdAndUpdateStatus(model.getUserId());
            }
        }
    }

    void checkUserIdAndUpdateStatus(int userID) {
        for (int i = 0; i < userList.size(); i++) {
            if (userID == userList.get(i).getUserId()) {
                int isFavorite = userList.get(i).getIsFavorite();
                userList.get(i).setIsFavorite(isFavorite == 0 ? 1 : 0);
                adapter.notifyItemChanged(i);
                return;
            }
        }
    }
}