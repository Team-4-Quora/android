package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.RecycleView.AnswerPageAdapter;
import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiQuestion;

import java.util.ArrayList;
import java.util.List;

public class Answer extends AppCompatActivity implements AnswerPageAdapter.IApiResponseClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        displayRecycle();
    }

    public void displayRecycle(){
        List<ApiAnswer> userDataList=new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleans);
        AnswerPageAdapter recycleViewAdapter=new AnswerPageAdapter(userDataList,Answer.this,Answer.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Answer.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }

    private void generatedata(List<ApiAnswer> apiQuestions)
    {
        apiQuestions.add(new ApiAnswer("Palak","what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?"));
        apiQuestions.add(new ApiAnswer("Palak1","what is your fav colour?"));
        apiQuestions.add(new ApiAnswer("Palak2","what is your fav colour?"));

    }

    @Override
    public void onUserClick(ApiAnswer apiproduct) {
    }

}