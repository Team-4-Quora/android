package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiAdvertise;
import com.example.android.RecycleView.Model.ApiHome;
import com.example.android.RecycleView.Model.ApiQuestion;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements HomePageAdapter.IApiResponseClick , HomePageAdapter.IAddRespClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView question=findViewById(R.id.iv_question_bottom);
        question.setOnClickListener(v ->{
            Intent i=new Intent(HomePage.this,QuestionActivity.class);
            startActivity(i);

        });

        ImageView profile=findViewById(R.id.iv_profile_bottom);
        profile.setOnClickListener(v ->{
            Intent i=new Intent(HomePage.this,Profile.class);
            startActivity(i);
        });

       displayRecycle();

    }

    public void displayRecycle(){
        List<ApiQuestion> userDataList=new ArrayList<>();
        generatedata(userDataList);
        List<ApiAdvertise> userAdsDataList=new ArrayList<>();
        generateads(userAdsDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleFeed);
        HomePageAdapter recycleViewAdapter=new HomePageAdapter(userDataList,userAdsDataList , HomePage.this,HomePage.this,HomePage.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(HomePage.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }

    private void generatedata(List<ApiQuestion> apiQuestions)
    {
        apiQuestions.add(new ApiQuestion("Palak","what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?"));
        apiQuestions.add(new ApiQuestion("Palak1","what is your fav colour?"));
        apiQuestions.add(new ApiQuestion("Palak2","what is your fav colour?"));

    }

    private void generateads(List<ApiAdvertise> apiAdvertises)
    {
        apiAdvertises.add(new ApiAdvertise("https://m.media-amazon.com/images/I/81HgVEqBVuL._SL1500_.jpg"));
    }

    @Override
    public void onUserClick(ApiQuestion apiQuestion) {
//        Intent i=new Intent(HomePage.this,Answer.class);
//        startActivity(i);
    }

    @Override
    public void onUserClickadd(ApiAdvertise apiAdvertise) {

    }
}