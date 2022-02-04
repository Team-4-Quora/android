package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.UserDto;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {

    TextView points,level,interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayRecyclerfollower();

       points=findViewById(R.id.tv_profile_points);
       level=findViewById(R.id.tv_profile_level);
       interest=findViewById(R.id.tv_profile_interest);

       displaydetails();
    }

    private void displaydetails(){
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<UserDto> userDtoCall=iPostUser.getUserStats("fggjh@gmail.com");
        userDtoCall.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                points.setText(response.body().getPoints()+"");
                level.setText(response.body().getLevel()+"");
                Toast.makeText(Profile.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(Profile.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
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
        Intent i=new Intent(Profile.this,Profile.class);
        startActivity(i);
    }
}