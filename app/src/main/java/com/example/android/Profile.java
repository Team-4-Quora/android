package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.Model.UserDto;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {
    private int j;

    TextView points,level,interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        j=getIntent().getIntExtra("j",0);

       points=findViewById(R.id.tv_profile_points);
       level=findViewById(R.id.tv_profile_level);
       interest=findViewById(R.id.tv_profile_interest);


        SearchView searchView;
        searchView = findViewById(R.id.et_profile_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(getApplicationContext(), ProfileSearchQuery.class);
                i.putExtra("searchQuery", query);
                startActivity(i);
                return  true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });

       findViewById(R.id.iv_org_create).setOnClickListener(v -> {
           Intent i=new Intent(Profile.this, OrganizationCreate.class);
           startActivity(i);
       });

       findViewById(R.id.pending).setOnClickListener(v -> {
           Intent i=new Intent(Profile.this,Pending.class);
           startActivity(i);
       });

       findViewById(R.id.organizdetails).setOnClickListener(v -> {
           Intent i=new Intent(Profile.this, OrganizationView.class);
           startActivity(i);
       });

       displaydetails();

        displayRecyclerfollower();

    }

    private void displaydetails(){
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<UserDto> userDtoCall=iPostUser.getUserStats("vinaymatta63@gmail.com");
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
                System.out.println(t.getMessage());

            }
        });
    }

    public void displayRecyclerfollower() {
//        List<UserDto> userDataList = new ArrayList<>();
        // generatedata(userDataList);
//
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<List<FollowerDto>> userDtoCall=iPostUser.fetchFollowerData("followers","vmat358@gmail.com");
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

                RecyclerView recyclerView = findViewById(R.id.recycleList);
                FollowerAdapter recycleViewAdapter = new FollowerAdapter(userData, Profile.this);
                LinearLayoutManager HorizontalLayout = new LinearLayoutManager(Profile.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);
            }

            @Override
            public void onFailure(Call<List<FollowerDto>> call, Throwable t) {

                System.out.println(t.getMessage()+"Error heer");
            }
        });
//
//        RecyclerView recyclerView = findViewById(R.id.recycleList);
//        FollowerAdapter recycleViewAdapter = new FollowerAdapter(userDataList, Profile.this);
//        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(Profile.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(HorizontalLayout);
//        recyclerView.setAdapter(recycleViewAdapter);
    }

    private void generatedata(List<ApiFollowers> apiFollowers) {
        apiFollowers.add(new ApiFollowers("Anush"));
        apiFollowers.add(new ApiFollowers("Palak"));
    }

    @Override
    public void onUserClick(ApiFollowers apiproduct) {
        if(j<=2) {
            Intent i = new Intent(Profile.this, Profile.class);
            i.putExtra("j",j+1);
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