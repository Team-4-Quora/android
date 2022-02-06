package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RecycleView.FollowerAdapter;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.OrganisationAdapter;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.FollowerDto;
import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.RetrofitUserBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrganizationView extends AppCompatActivity implements FollowerAdapter.IApiResponseClick {
    TextView orgname,orgdesc,orgowner;
    String orgid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_view);

        String id = addOrganization();
        orgowner=findViewById(R.id.tv_orgprofile_owner);
        orgdesc=findViewById(R.id.tv_org_profiledesc);
        orgname=findViewById(R.id.tv_orgprofile_name);

    }


    public String addOrganization()
    {
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<OrganisationDto> organizationDtoCall=iPostUser.fetchOrganization("vinay63@gmail.com");
        organizationDtoCall.enqueue(new Callback<OrganisationDto>() {
            @Override
            public void onResponse(Call<OrganisationDto> call, Response<OrganisationDto> response) {
                orgowner.setText(response.body().getOwner()+"");
                orgname.setText(response.body().getName()+"");
                orgdesc.setText(response.body().getDescription()+"");
                orgid=response.body().getId();
                show_follower(orgid);

                System.out.println("add Organisation id heree:::::"+response.body().getId());
                Toast.makeText(OrganizationView.this,"Success organization",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrganisationDto> call, Throwable t) {
                Toast.makeText(OrganizationView.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
        });
        return orgid;
    }

    public void show_follower(String id)
    {
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        System.out.println("show folowwer Organisation id heree:::::"+id);

        Call<List<FollowerDto>> followerdetails=iPostUser.getFollowers(id);

        followerdetails.enqueue(new Callback<List<FollowerDto>>() {
            @Override
            public void onResponse(Call<List<FollowerDto>> call, Response<List<FollowerDto>> response) {
                List<ApiFollowers> userData=new ArrayList<>();
                for(int i=0;i<response.body().size();i++)
                {
                    ApiFollowers followerDto=new ApiFollowers();
                    followerDto.setEmail(response.body().get(i).getRequesterId());
                    userData.add(followerDto);
                }

                RecyclerView recyclerView = findViewById(R.id.recycleorgList);
                FollowerAdapter recycleViewAdapter = new FollowerAdapter(userData, OrganizationView.this);
                LinearLayoutManager HorizontalLayout = new LinearLayoutManager(OrganizationView.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(recycleViewAdapter);
                Toast.makeText(OrganizationView.this,"success",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<FollowerDto>> call, Throwable t) {
                Toast.makeText(OrganizationView.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onUserClick(ApiFollowers apiproduct) {

    }
}