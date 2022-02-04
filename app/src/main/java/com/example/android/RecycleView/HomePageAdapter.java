package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiHome;
import com.example.android.RecycleView.Model.ApiQuestion;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private final List<ApiQuestion> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private Context context;
    public HomePageAdapter(List<ApiQuestion> apiResponseList, IApiResponseClick iApiResponseClick,Context context) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = iApiResponseClick;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiQuestion apiQuestion = apiResponseList.get(position);
        holder.quesName.setText(apiQuestion.getQuestionBy()+"");
        holder.ques.setText(apiQuestion.getContent()+"");
        holder.quesdate.setText(apiQuestion.getPostedOn()+"");
        holder.viewmore.setOnClickListener(v -> {
            Intent i=new Intent(context, Answer.class);
            context.startActivity(i);

        });


      //  Glide.with(holder.quesimg.getContext()).load(apiHome.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiQuestion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiQuestion apiQuestion);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView quesName;
        private final ImageView quesimg;
        private final TextView quesdate;
        private final TextView ques;
        private final Button  viewmore;



        public ViewHolder(View view) {
            super(view);
            rootView = view;
          ques=view.findViewById(R.id.ques);
          quesName=view.findViewById(R.id.ques_name);
          quesimg=view.findViewById(R.id.ques_iv);
          quesdate=view.findViewById(R.id.ques_time);
          viewmore=view.findViewById(R.id.bn_ques);

        }
    }
}
