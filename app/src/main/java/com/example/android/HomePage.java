package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiHome;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements HomePageAdapter.IApiResponseClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView question=findViewById(R.id.iv_question_bottom);
        question.setOnClickListener(v ->{
            Intent i=new Intent(HomePage.this,QuestionActivity.class);
            startActivity(i);
        });

        ImageView profile=findViewById(R.id.iv_top_profile);
        profile.setOnClickListener(v ->{
            Intent i=new Intent(HomePage.this,Profile.class);
            startActivity(i);
        });

       displayRecycle();

    }

    public void displayRecycle(){
        List<ApiHome> userDataList=new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleFeed);
        HomePageAdapter recycleViewAdapter=new HomePageAdapter(userDataList,HomePage.this , HomePage.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(HomePage.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }

    private void generatedata(List<ApiHome> apiHomes)
    {
        apiHomes.add(new ApiHome("Palak","what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?"));
        apiHomes.add(new ApiHome("Palak1","what is your fav colour?"));
        apiHomes.add(new ApiHome("Palak2","what is your fav colour?"));

    }


    @Override
    public void onUserClick(ApiHome apiproduct) {
    }
}