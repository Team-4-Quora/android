package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RecycleView.AnswerPageAdapter;
import com.example.android.RecycleView.CommentPageAdapter;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiComment;
import com.example.android.Retorfit.IPostComment;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.CommentDto;
import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;
import com.example.android.empty.Response;
import com.example.android.empty.ResponseItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Retrofit;

//give proper name to activities

public class Comment extends AppCompatActivity implements CommentPageAdapter.IApiResponseClick,CommentAdapter.IResponseItemClick{

    TextView commentby,commenton,commentmsg,commentact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentact=findViewById(R.id.addcomment);


        findViewById(R.id.commentpost).setOnClickListener(v -> {
            newComment();
        });

        getComment();
    }

    // unused method
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

    public void newComment()
    {
        Retrofit retrofit= RetrofitQnaBuilder.getInstance();
//        IPostComment iPostComment=retrofit.create(IPostComment.class);
        CommentDto commentDto =new CommentDto();

        SharedPreferences sharedPreferences=getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
        String email=sharedPreferences.getString("em","default");
        commentDto.setCommentBy(email);
        String ansid=getIntent().getStringExtra("answerid");
        commentDto.setAnswerId(ansid);
        commentDto.setMessage(commentact.getText().toString());
        Call<Void> commentCall=retrofit.create(IPostComment.class).savecomment(commentDto);

        commentCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Toast.makeText(Comment.this,"Comment created",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Comment.this,Comment.class);
                i.putExtra("answerid",ansid);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Comment.this,"Comment failure",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getComment()
    {
        Retrofit retrofit= RetrofitQnaBuilder.getInstance();
        IPostComment iPostComment = retrofit.create(IPostComment.class);
//        Call<Response> commentCall=retrofit.create(IPostComment.class).fetcheachcomment("61fcbdcfd36d3324443ee830");
        Call<List<ResponseItem>> responseCall = iPostComment.fetcheachcomment(getIntent().getStringExtra("answerid"));

        responseCall.enqueue(new Callback<List<ResponseItem>>() {

            @Override
            public void onResponse(Call<List<ResponseItem>> call, retrofit2.Response<List<ResponseItem>> response) {
                if(response.isSuccessful()) {
                    RecyclerView recyclerView = findViewById(R.id.recyclecomment);
                    CommentAdapter commentAdapter = new CommentAdapter(response.body(), Comment.this, Comment.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Comment.this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerView.setAdapter(commentAdapter);
                }else{
                    Toast.makeText(Comment.this,response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseItem>> call, Throwable t) {
//                Toast.makeText(Comment.this,"Comment can't fetch",Toast.LENGTH_SHORT).show();
//                System.out.println(t.getMessage()+"Error here");
                Toast.makeText(Comment.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onUserClick(ResponseItem responseItem) {

    }

    @Override
    public void onUserClick(ApiComment apiComment) {

    }
}