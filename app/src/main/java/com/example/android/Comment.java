package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Comment extends AppCompatActivity implements CommentPageAdapter.IApiResponseClick{

    TextView commentby,commenton,commentmsg,commentact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentact=findViewById(R.id.addcomment);


        findViewById(R.id.commentpost).setOnClickListener(v -> {
            newComment();
        });
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

    public void newComment()
    {
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
//        IPostComment iPostComment=retrofit.create(IPostComment.class);
        CommentDto commentDto =new CommentDto();
        commentDto.setCommentBy("Anush Mishra");
        commentDto.setAnswerId("61fcbdcfd36d3324443ee830");
        commentDto.setMessage("hey");
        Call<Void> commentCall=retrofit.create(IPostComment.class).savecomment(commentDto);

        commentCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Comment.this,"Comment created",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Comment.this,"Comment failure",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onUserClick(ApiComment apiproduct) {

    }
}