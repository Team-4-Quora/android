package com.example.android.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.Model.ApiUser;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolderfollower>{

    private final List<ApiUser> apiResponseList;
    private final IApiResponseClick mUserDataInterface;


    public UserAdapter(List<ApiUser> apiResponseList, IApiResponseClick iApiResponseClick) {
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
        ApiUser apiuser = apiResponseList.get(position);
        holder.followername.setText(apiuser.getEmail() + "");


        //  Glide.with(holder.quesimg.getContext()).load(apiHome.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiuser);
            }
        });

    }
    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiUser apiuser);
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
