package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {
    private int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent i=new Intent();
        i.getStringExtra("j");
        displayRecyclerfollower();
    }

    public void displayRecyclerfollower() {
        List<ApiFollowers> userDataList = new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView = findViewById(R.id.recycleList);
        FollowerAdapter recycleViewAdapter = new FollowerAdapter(userDataList, Profile.this);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(Profile.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    private void generatedata(List<ApiFollowers> apiFollowers) {
        apiFollowers.add(new ApiFollowers("Anush"));
        apiFollowers.add(new ApiFollowers("Palak"));
    }

    @Override
    public void onUserClick(ApiFollowers apiproduct) {
        if(j<=2) {
            Intent i = new Intent(Profile.this, Profile.class);
            i.putExtra("j",j+=1);
            Toast.makeText(Profile.this,j+"",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
//        if(j>1)
//
//            if(val<2){
//            i.putExtra("j",j++);
//            Toast.makeText(Profile.this,j.toString(),Toast.LENGTH_SHORT).show();
//            System.out.println(j+"j here ");
//        }
    }
}