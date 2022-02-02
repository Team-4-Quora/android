package com.example.android.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.RecycleView.Model.ApiHome;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private final List<ApiHome> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    public HomePageAdapter(List<ApiHome> apiResponseList, IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_feed_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiHome apiHome = apiResponseList.get(position);
//        holder.quesimg.setText(apiHome.g);
        holder.quesName.setText(apiHome.getQuestionBy()+"");
        holder.ques.setText(apiHome.getQuestion()+"");
        holder.quesdate.setText(apiHome.getQuesPostedOn()+"");
        holder.ans.setText(apiHome.getAnswer()+"");
        holder.ansdate.setText(apiHome.getAnsPostedOn()+"");
        holder.ansName.setText(apiHome.getAnswerBy()+"");


      //  Glide.with(holder.ivProduct.getContext()).load(apiProduct.getImage()).placeholder(R.drawable.ic_login).into(holder.ivProduct);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiHome);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiHome apiproduct);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView quesName;
        private final ImageView quesimg;
        private final TextView quesdate;
        private final TextView ques;
        private final TextView ansName;
        private final ImageView ansimg;
        private final TextView ansdate;
        private final TextView ans;


        public ViewHolder(View view) {
            super(view);
            rootView = view;
          ques=view.findViewById(R.id.feed_ques);
          quesName=view.findViewById(R.id.feed_ques_name);
          quesimg=view.findViewById(R.id.feed_ques_iv);
          quesdate=view.findViewById(R.id.feed_ques_time);
          ansimg=view.findViewById(R.id.answer_prof);
          ans=view.findViewById(R.id.answer);
          ansName=view.findViewById(R.id.ans_name);
          ansdate=view.findViewById(R.id.ans_timestamp);
        }
    }
}
