package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Profile;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class  PendingPageAdapter extends RecyclerView.Adapter<PendingPageAdapter.ViewHolderPending>{
    private final List<ApiFollowers> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private final Context context;


    public PendingPageAdapter(List<ApiFollowers> apiResponseList, IApiResponseClick iApiResponseClick,Context context) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface=iApiResponseClick;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolderPending onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_pending, parent, false);
        return new ViewHolderPending(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderPending holder, int position) {
        ApiFollowers apiFollowers = apiResponseList.get(position);
        holder.pendingname.setText(apiFollowers.getEmail() + "");

        holder.acceptPending.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitUserBuilder.getInstance();
            IPostUser iPostUser=retrofit.create(IPostUser.class);
            Call<Void> pendinglist=iPostUser.acceptRequest("vmat358@gmail.com",apiFollowers.getEmail());

            pendinglist.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Request Accepted",Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(context, Profile.class);
                    context.startActivity(i);

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                   Toast.makeText(context,"Request accept failed",Toast.LENGTH_SHORT).show();
                }
            });
        });


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

    public static class ViewHolderPending extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView pendingimg;
        private final TextView pendingname;
        private final Button acceptPending;

        public ViewHolderPending(View view) {
            super(view);
            rootView = view;
            pendingname=view.findViewById(R.id.pending_email);
            pendingimg=view.findViewById(R.id.pending);
            acceptPending=view.findViewById(R.id.acceptrequest);

        }
    }
}
