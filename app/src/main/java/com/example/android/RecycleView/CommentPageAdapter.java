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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Comment;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiComment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentPageAdapter extends RecyclerView.Adapter<CommentPageAdapter.ViewHolderComment> {
    private final List<ApiComment> apiResponseList;
    private final CommentPageAdapter.IApiResponseClick mUserDataInterface;
    private Context context;

    public CommentPageAdapter(List<ApiComment> apiResponseList, CommentPageAdapter.IApiResponseClick mUserDataInterface, Context context) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = mUserDataInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentPageAdapter.ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_comment,parent, false);
        return new CommentPageAdapter.ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentPageAdapter.ViewHolderComment holder, int position) {
        ApiComment apiComment=apiResponseList.get(position);
        holder.commentName.setText(apiComment.getCommentedBy());

        Date date = new Date(apiComment.getPostedOn()* 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        holder.commentDate.setText(sdf.format(date)+"");
        holder.comment.setText(apiComment.getMessage());
        //  Glide.with(holder.quesimg.getContext()).load(apiAnswer.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.reply.setOnClickListener(v -> {
            Intent i=new Intent(context, Comment.class);
            context.startActivity(i);
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiComment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }
    public interface IApiResponseClick {
        void onUserClick(ApiComment apiComment);
    }

    public static class ViewHolderComment extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView commentName;
        private final TextView commentDate;
        private final TextView comment;
        private final Button reply;

        public ViewHolderComment(View view) {
            super(view);
            rootView = view;
            commentName = view.findViewById(R.id.comment_name);
            commentDate = view.findViewById(R.id.comment_timestamp);
            comment = view.findViewById(R.id.comment);
            reply = view.findViewById(R.id.reply);
        }
    }
}
