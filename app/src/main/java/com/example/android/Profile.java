package com.example.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.Retorfit.IPostLogin;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.Model.LogoutDto;
import com.example.android.Retorfit.Model.SigninResponse;
import com.example.android.Retorfit.Model.UserDto;
import com.example.android.Retorfit.RetrofitLoginBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {
    private int j;

    TextView points,level,interest,email,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        j=getIntent().getIntExtra("j",0);

       points=findViewById(R.id.tv_profile_points);
       level=findViewById(R.id.tv_profile_level);
       interest=findViewById(R.id.tv_profile_interest);
       email=findViewById(R.id.tv_mainprofile_email);
       name=findViewById(R.id.tv_mainprofile_name);

//
        ImageView button = findViewById(R.id.iv_logout);
//button.setSize(SignInButton.SIZE_STANDARD);

        button.setOnClickListener(v -> {
//           / signOut();
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);

            Retrofit retrofit= RetrofitLoginBuilder.getInstance();
            IPostLogin iPostloginApi=retrofit.create(IPostLogin.class);

            LogoutDto logout=new LogoutDto(
                    sharedPreferences.getString("em",""),
                    "3"
            );
            Call<SigninResponse> logoutdetail=iPostloginApi.logout(logout);
            logoutdetail.enqueue(new Callback<SigninResponse>() {
                @Override
                public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                    Toast.makeText(Profile.this,"Logout succesfull",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SigninResponse> call, Throwable t) {
                    Toast.makeText(Profile.this,"Logout fail''",Toast.LENGTH_SHORT).show();

                }
            });


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();


            Intent intent=new Intent(Profile.this,Login.class);
            startActivity(intent);
            finish();
        });
//        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
//        String em=sharedPreferences.getString("em","");
//        String prof=sharedPreferences.getString("name","");
//       email.setText(em+"");
//       name.setText(prof+"");


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


        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
        String em=sharedPreferences.getString("em","");

        Call<UserDto> userDtoCall=iPostUser.getUserStats(em);
        userDtoCall.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                points.setText(response.body().getPoints()+"");
                level.setText(response.body().getLevel()+"");
                email.setText(response.body().getEmail()+"");
               name.setText(response.body().getName()+"");
               interest.setText(sharedPreferences.getString("cate",""));

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
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
        String email=sharedPreferences.getString("em","");

        Call<List<FollowerDto>> userDtoCall=iPostUser.fetchFollowerData("followers",email);
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