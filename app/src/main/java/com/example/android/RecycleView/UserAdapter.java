package com.example.android.RecycleView;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.Model.ApiUser;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolderfollower>{

    private final List<ApiUser> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private final Context context;


    public UserAdapter(List<ApiUser> apiResponseList, IApiResponseClick iApiResponseClick,Context context) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface=iApiResponseClick;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolderfollower onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user_search, parent, false);
        return new ViewHolderfollower(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderfollower holder, int position) {
        ApiUser apiuser = apiResponseList.get(position);
        holder.followername.setText(apiuser.getEmail() + "");

        holder.follow.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitUserBuilder.getInstance();
            IPostUser iPostUser=retrofit.create(IPostUser.class);

            FollowerDto followerDto=new FollowerDto();
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
            followerDto.setRequesterId(sharedPreferences.getString("em",""));
            followerDto.setEmail(apiuser.getEmail());
            followerDto.setStatus(0);
            Call<Void> follow=iPostUser.followUser(followerDto);
            follow.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Added to pending",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"not added to pending",Toast.LENGTH_SHORT).show();

                }
            });
        });

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
        private final Button follow;

        public ViewHolderfollower(View view) {
            super(view);
            rootView = view;
            followername=view.findViewById(R.id.search_follow_name);
            followerimg=view.findViewById(R.id.search_follow_img);
            follow=view.findViewById(R.id.search_user);
        }
    }
}
