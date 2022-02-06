package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.RecycleView.Model.ApiHome;


import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolderfollower> {
    private final List<ApiFollowers> apiResponseList;
    private final IApiResponseClick mUserDataInterface;


    public FollowerAdapter(List<ApiFollowers> apiResponseList, FollowerAdapter.IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface=iApiResponseClick;

    }

    @NonNull
    @Override
    public ViewHolderfollower onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_following, parent, false);
        return new ViewHolderfollower(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderfollower holder, int position) {
        ApiFollowers apiFollowers = apiResponseList.get(position);
        holder.followername.setText(apiFollowers.getEmail() + "");


        //  Glide.with(holder.quesimg.getContext()).load(apiHome.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiFollowers);
            }
        });

    }
    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiFollowers apiproduct);
    }

    public static class ViewHolderfollower extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView followerimg;
        private final TextView followername;

        public ViewHolderfollower(View view) {
            super(view);
            rootView = view;
            followername=view.findViewById(R.id.tv_follow_name);
            followerimg=view.findViewById(R.id.iv_follow_img);

        }
    }
}



