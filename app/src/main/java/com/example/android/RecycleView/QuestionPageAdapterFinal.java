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
import com.example.android.RecycleView.Model.ApiQuestion;

import java.util.List;

public class QuestionPageAdapterFinal extends RecyclerView.Adapter<QuestionPageAdapterFinal.ViewHolderfinal>{
    private final List<ApiQuestion> apiResponseList;
    private final IApiResponseClick mUserDataInterface;


    public QuestionPageAdapterFinal(List<ApiQuestion> apiResponseList, IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface=iApiResponseClick;

    }

    @NonNull
    @Override
    public ViewHolderfinal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_card, parent, false);
        return new ViewHolderfinal(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderfinal holder, int position) {
        ApiQuestion apiques = apiResponseList.get(position);
        holder.quesperson.setText(apiques.getQuestionBy() + "");
        holder.quescateg.setText(apiques.getCategory()+"");
        holder.quesasked.setText(apiques.getContent()+"");


        //  Glide.with(holder.quesimg.getContext()).load(apiHome.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiques);
            }
        });

    }
    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiQuestion apiorg);
    }

    public static class ViewHolderfinal extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView quesperson;
        private final TextView quescateg;
        private final TextView quesasked;

        public ViewHolderfinal(View view) {
            super(view);
            rootView = view;
            quesperson=view.findViewById(R.id.ques_person);
            quescateg=view.findViewById(R.id.ques_category);
            quesasked=view.findViewById(R.id.ques_searched);

        }
    }
}
