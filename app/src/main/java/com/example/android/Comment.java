package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.RecycleView.AnswerPageAdapter;
import com.example.android.RecycleView.CommentPageAdapter;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiComment;

import java.util.ArrayList;
import java.util.List;

public class Comment extends AppCompatActivity implements CommentPageAdapter.IApiResponseClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        displayRecyclecomment();
    }

    public void displayRecyclecomment(){
        List<ApiComment> userDataList=new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recyclecomment);
        CommentPageAdapter recycleViewAdapter=new CommentPageAdapter(userDataList,Comment.this,Comment.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Comment.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    private void generatedata(List<ApiComment> apiComments)
    {
        apiComments.add(new ApiComment("Hello everyone","Anush"));
        apiComments.add(new ApiComment("Project kardo","Anush"));
        apiComments.add(new ApiComment("Byee","Saurav"));

    }

    @Override
    public void onUserClick(ApiComment apiproduct) {
    }
}