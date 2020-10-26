package com.example.matrimony_demo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matrimony_demo.R;
import com.example.matrimony_demo.database.TableUser;
import com.example.matrimony_demo.model.UserModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {

    Context context;
    ArrayList<UserModel> userList;
    OnViewClickListener onViewClickListener;

    public UserListAdapter(Context context, ArrayList<UserModel> userList, OnViewClickListener onViewClickListener) {
        this.context = context;
        this.userList = userList;
        this.onViewClickListener = onViewClickListener;
    }

    public interface OnViewClickListener {
        void OnDeleteClick(int position);

        void onFavoriteClick(int position);

        void OnItemClick(int position);
    }

    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.view_row_user_list, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        holder.tvFullName.setText(userList.get(position).getFirstName() + " " + userList.get(position).getMiddleName() + " " +userList.get(position).getLastName());
        holder.tvLanguage.setText(userList.get(position).getLanguage() + " | " + userList.get(position).getCity());
        holder.tvDateOfBirth.setText(userList.get(position).getDateOfBirth());
        holder.ivFavouriteUser.setImageResource(userList.get(position).getIsFavorite() == 0 ? R.mipmap.ic_star : R.mipmap.ic_rate_star);

        holder.ivDeleteUser.setOnClickListener(view -> {
            if (onViewClickListener != null) {
                onViewClickListener.OnDeleteClick(position);
            }



        });

        holder.ivFavouriteUser.setOnClickListener(view -> {
            if (onViewClickListener != null) {
                onViewClickListener.onFavoriteClick(position);
            }
        });

        holder.itemView.setOnClickListener(view -> {
            if (onViewClickListener != null) {
                onViewClickListener.OnItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFullName)
        TextView tvFullName;
        @BindView(R.id.tvLanguage)
        TextView tvLanguage;
        @BindView(R.id.tvDateOfBirth)
        TextView tvDateOfBirth;
        @BindView(R.id.ivDeleteUser)
        ImageView ivDeleteUser;
        @BindView(R.id.ivFavoriteUser)
        ImageView ivFavouriteUser;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
