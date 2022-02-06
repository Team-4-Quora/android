package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.OrganisationDto;
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
        Call<OrganisationDto> organizationDtoCall=iPostUser.getAnOrganization("organization1");
        organizationDtoCall.enqueue(new Callback<OrganisationDto>() {
            @Override
            public void onResponse(Call<OrganisationDto> call, Response<OrganisationDto> response) {
                orgemail.setText(response.body().getOwner()+"");
                orgname.setText(response.body().getName()+"");
                orgdesc.setText(response.body().getDescription()+"");
                Toast.makeText(Organization.this,"Success organization",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrganisationDto> call, Throwable t) {
                Toast.makeText(Organization.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());

            }
    });
}

public void newOrganization()
{
    Retrofit retrofit= RetrofitUserBuilder.getInstance();
    IPostUser iPostUser=retrofit.create(IPostUser.class);
    OrganisationDto organisationDto =new OrganisationDto();
    organisationDto.setId(orgemail.getText().toString());
    organisationDto.setName(orgname.getText().toString());
    organisationDto.setDescription(orgdesc.getText().toString());
    organisationDto.setOwner("vpalak106@gmail.com");
    Call<Void> organizationCall=iPostUser.addOrg(organisationDto);

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