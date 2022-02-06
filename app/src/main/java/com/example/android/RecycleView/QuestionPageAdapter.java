package com.example.android.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.Model.ApiQuestion;

import java.util.List;

public class QuestionPageAdapter extends RecyclerView.Adapter<QuestionPageAdapter.ViewHolderquestion>{

    private final List<ApiQuestion> apiResponseList;
    private final QuestionPageAdapter.IApiResponseClick mUserDataInterface;

    public QuestionPageAdapter(List<ApiQuestion> apiResponseList, IApiResponseClick mUserDataInterface) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = mUserDataInterface;
    }

    @NonNull
    @Override
    public ViewHolderquestion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_quessearch, parent, false);
        return new QuestionPageAdapter.ViewHolderquestion(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderquestion holder, int position) {
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
        void onUserClick(ApiQuestion apiques);
    }

    public static class ViewHolderquestion extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView quesperson;
        private final TextView quescateg;
        private final TextView quesasked;

        public ViewHolderquestion(View view) {
            super(view);
            rootView = view;
            quesperson=view.findViewById(R.id.ques_person);
            quescateg=view.findViewById(R.id.ques_category);
            quesasked=view.findViewById(R.id.ques_searched);

        }
    }
}
