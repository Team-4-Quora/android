package com.example.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.CommentPageAdapter;
import com.example.android.RecycleView.Model.ApiComment;
import com.example.android.empty.ResponseItem;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderComment> {

    private final List<ResponseItem> responseItemList;
    private final IResponseItemClick responseItemClick;
    private Context context;

    public CommentAdapter(List<ResponseItem> responseItemList, IResponseItemClick responseItemClick, Context context) {
        this.responseItemList = responseItemList;
        this.responseItemClick = responseItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_commentfinal,parent, false);
        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComment holder, int position) {
        ResponseItem responseItem=responseItemList.get(position);
        holder.commentName.setText(responseItem.getCommentBy());
        holder.commentDate.setText(responseItem.getPostedOn()+"");
        holder.comment.setText(responseItem.getMessage());
        //  Glide.with(holder.quesimg.getContext()).load(apiAnswer.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.reply.setOnClickListener(v -> {
            Intent i=new Intent(context, Comment.class);
            context.startActivity(i);
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseItemClick.onUserClick(responseItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseItemList.size();
    }
    public interface IResponseItemClick {
        void onUserClick(ResponseItem responseItem);
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
            commentName = view.findViewById(R.id.comment_name2);
            commentDate = view.findViewById(R.id.comment_timestamp2);
            comment = view.findViewById(R.id.comment2);
            reply = view.findViewById(R.id.reply2);
        }
    }
}