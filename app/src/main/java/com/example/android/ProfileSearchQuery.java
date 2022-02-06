package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.Model.ApiUser;
import com.example.android.RecycleView.OrganisationAdapter;
import com.example.android.RecycleView.UserAdapter;
import com.example.android.Retorfit.IPostSearch;
import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.Model.UserDto;
import com.example.android.Retorfit.RetrofitSearchBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileSearchQuery extends AppCompatActivity  implements OrganisationAdapter.IApiResponseClick,UserAdapter.IApiResponseClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search_query);

        String search = getIntent().getStringExtra("searchQuery");

        Retrofit retrofit= RetrofitSearchBuilder.getInstance();
        IPostSearch iPostSearch=retrofit.create(IPostSearch.class);

        Call<List<OrganisationDto>> orgresponse=iPostSearch.searchInOrg(search);

        orgresponse.enqueue(new Callback<List<OrganisationDto>>() {
            @Override
            public void onResponse(Call<List<OrganisationDto>> call, Response<List<OrganisationDto>> response) {

                if(response.isSuccessful()&& response.body().size()>0){
                List<ApiOrganisation>  apiOrganisations=new ArrayList<>();

                for(int i=0;i<response.body().size();i++)
                {
                    ApiOrganisation apiOrganisation=new ApiOrganisation();
                    apiOrganisation.setId(response.body().get(i).getId());
                    apiOrganisation.setName(response.body().get(i).getName());
                    apiOrganisation.setDescription(response.body().get(i).getDescription());
                    apiOrganisation.setOwner(response.body().get(i).getOwner());

                    apiOrganisations.add(apiOrganisation);
                }

                RecyclerView recyclerView = findViewById(R.id.org_recycle);
                OrganisationAdapter recycleViewAdapter = new OrganisationAdapter(apiOrganisations, ProfileSearchQuery.this);
                LinearLayoutManager HorizontalLayout = new LinearLayoutManager(ProfileSearchQuery.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);
                }
                else
                {
                    Toast.makeText(ProfileSearchQuery.this,"No such organisation present",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrganisationDto>> call, Throwable t) {
                Toast.makeText(ProfileSearchQuery.this,"No such organisation present",Toast.LENGTH_SHORT).show();

            }
        });

        Call<List<UserDto>> userDto=iPostSearch.searchUser(search);

        userDto.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if(response.isSuccessful() && response.body().size()>0)
                {
                    List<ApiUser> userlist=new ArrayList<>();

                    for(int i=0;i<response.body().size();i++)
                    {
                        ApiUser apiUser=new ApiUser();
                        apiUser.setEmail(response.body().get(i).getEmail());
                        apiUser.setLevel(response.body().get(i).getLevel());
                        apiUser.setPoints(response.body().get(i).getPoints());

                        userlist.add(apiUser);

                        RecyclerView recyclerView = findViewById(R.id.user_recycle);
                        UserAdapter recycleViewAdapter = new UserAdapter(userlist, ProfileSearchQuery.this);
                        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(ProfileSearchQuery.this, LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(HorizontalLayout);
                        recyclerView.setAdapter(recycleViewAdapter);


                    }

                }else{
                    Toast.makeText(ProfileSearchQuery.this,"No such user present",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                Toast.makeText(ProfileSearchQuery.this,"No such user present",Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public void onUserClick(ApiOrganisation apiorg) {

    }

    @Override
    public void onUserClick(ApiUser apiuser) {

    }
}