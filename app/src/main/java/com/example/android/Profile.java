package com.example.android;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayRecyclerfollower();
    }

    public void displayRecyclerfollower(){
        List<ApiFollowers> userDataList=new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleList);
        FollowerAdapter recycleViewAdapter=new FollowerAdapter(userDataList,Profile.this);
        LinearLayoutManager HorizontalLayout= new LinearLayoutManager(Profile.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }
    private void generatedata(List<ApiFollowers> apiFollowers)
    {
        apiFollowers.add(new ApiFollowers("Anush"));
        apiFollowers.add(new ApiFollowers("Palak"));
    }

    @Override
    public void onUserClick(ApiFollowers apiproduct) {

    }
}