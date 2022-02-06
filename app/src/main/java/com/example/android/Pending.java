package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.RecycleView.PendingPageAdapter;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Pending extends AppCompatActivity implements PendingPageAdapter.IApiResponseClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        displayRecyclerpending();
    }

    public void displayRecyclerpending() {
//        List<UserDto> userDataList = new ArrayList<>();
        // generatedata(userDataList);
//
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<List<FollowerDto>> userDtoCall=iPostUser.fetchFollowerData("pending","vmat358@gmail.com");
        userDtoCall.enqueue(new Callback<List<FollowerDto>>() {
            @Override
            public void onResponse(Call<List<FollowerDto>> call, Response<List<FollowerDto>> response) {
                List<ApiFollowers> userData=new ArrayList<>();
                for(int i=0;i<response.body().size();i++)
                {
                    ApiFollowers followerDto=new ApiFollowers();
                    followerDto.setEmail(response.body().get(i).getRequesterId());
                    userData.add(followerDto);
                }

                RecyclerView recyclerView = findViewById(R.id.recycle_pending);
                PendingPageAdapter recycleViewAdapter = new PendingPageAdapter(userData, Pending.this,Pending.this);
                LinearLayoutManager HorizontalLayout = new LinearLayoutManager(Pending.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);
            }

            @Override
            public void onFailure(Call<List<FollowerDto>> call, Throwable t) {

                System.out.println(t.getMessage()+"Error here");
            }
        });
//
//        RecyclerView recyclerView = findViewById(R.id.recycleList);
//        FollowerAdapter recycleViewAdapter = new FollowerAdapter(userDataList, Profile.this);
//        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(Profile.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(HorizontalLayout);
//        recyclerView.setAdapter(recycleViewAdapter);
    }

    @Override
    public void onUserClick(ApiFollowers apiproduct) {

    }
}