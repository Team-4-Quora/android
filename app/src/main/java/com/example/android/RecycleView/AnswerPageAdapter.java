package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.Model.ReactionDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnswerPageAdapter extends RecyclerView.Adapter<AnswerPageAdapter.ViewHolderAns>{
    private final List<ApiAnswer> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private final Context context ;
    public AnswerPageAdapter(List<ApiAnswer> apiResponseList, IApiResponseClick iApiResponseClick ,Context context) {
        this.context=context;
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
    }


    @NonNull
    @Override
    public ViewHolderAns onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_card,parent, false);
        return new ViewHolderAns(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAns holder, int position) {
        ApiAnswer apiAnswer = apiResponseList.get(position);
        holder.ansName.setText(apiAnswer.getAnswerBy()+"");
        holder.ans.setText(apiAnswer.getMessage()+"");
        holder.ansdate.setText(apiAnswer.getPostedOn()+"");

        holder.upvote.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitQnaBuilder.getInstance();
            IPostQna iPostQna=retrofit.create(IPostQna.class);

            ReactionDto reactionDto=new ReactionDto();
            Call<Void> reactionresponse=iPostQna.save(reactionDto);
            reactionresponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Liked",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"cannot liked",Toast.LENGTH_SHORT).show();

                }
            });
        });


        holder.downvote.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitQnaBuilder.getInstance();
            IPostQna iPostQna=retrofit.create(IPostQna.class);

            ReactionDto reactionDto=new ReactionDto();
            Call<Void> reactionresponse=iPostQna.save(reactionDto);
            reactionresponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Dislike",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"cannot dislike",Toast.LENGTH_SHORT).show();

                }
            });
        });


        //  Glide.with(holder.quesimg.getContext()).load(apiAnswer.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiAnswer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiAnswer apiAnswer);
    }

    public static class ViewHolderAns extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView ansName;
        private final ImageView ansimg;
        private final TextView ansdate;
        private final TextView ans;
        private final ImageButton upvote;
        private final ImageButton downvote;
        private final ImageButton share;



        public ViewHolderAns(View view) {
            super(view);
            rootView = view;
            ans=view.findViewById(R.id.answer);
            ansName=view.findViewById(R.id.ans_name);
            ansimg=view.findViewById(R.id.answer_prof);
            ansdate=view.findViewById(R.id.ans_timestamp);
            share=view.findViewById(R.id.share_bn);
            upvote=view.findViewById(R.id.upvote);
            downvote=view.findViewById(R.id.downvote);


        }
    }

}
