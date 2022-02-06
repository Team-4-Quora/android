package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.OrganizationDto;
import com.example.android.Retorfit.Model.UserDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Organization extends AppCompatActivity {
    TextView orgemail,orgname,orgdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);

        orgemail=findViewById(R.id.et_organization_email);
        orgname=findViewById(R.id.et_organization_name);
        orgdesc=findViewById(R.id.et_organization_description);

        findViewById(R.id.bn_organization_submit).setOnClickListener(v -> {
            newOrganization();
        });
    }

    public void addOrganization()
    {
        Retrofit retrofit= RetrofitUserBuilder.getInstance();
        IPostUser iPostUser=retrofit.create(IPostUser.class);
        Call<OrganizationDto> organizationDtoCall=iPostUser.getAnOrganization("organization1");
        organizationDtoCall.enqueue(new Callback<OrganizationDto>() {
            @Override
            public void onResponse(Call<OrganizationDto> call, Response<OrganizationDto> response) {
                orgemail.setText(response.body().getOwner()+"");
                orgname.setText(response.body().getName()+"");
                orgdesc.setText(response.body().getDescription()+"");
                Toast.makeText(Organization.this,"Success organization",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrganizationDto> call, Throwable t) {
                Toast.makeText(Organization.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
    });
}

public void newOrganization()
{
    Retrofit retrofit= RetrofitUserBuilder.getInstance();
    IPostUser iPostUser=retrofit.create(IPostUser.class);
    OrganizationDto organizationDto=new OrganizationDto();
    organizationDto.setId(orgemail.getText().toString());
    organizationDto.setName(orgname.getText().toString());
    organizationDto.setDescription(orgdesc.getText().toString());
    organizationDto.setOwner("vpalak106@gmail.com");
    Call<Void> organizationCall=iPostUser.addOrg(organizationDto);

    organizationCall.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            Toast.makeText(Organization.this,"Success creating organization",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(Organization.this,"Failure",Toast.LENGTH_SHORT).show();

        }
    });

}


}