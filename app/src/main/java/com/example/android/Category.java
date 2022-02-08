package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.Retorfit.IPostLogin;
import com.example.android.Retorfit.Model.InterestDto;
import com.example.android.Retorfit.RetrofitLoginBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Category extends AppCompatActivity {
    String cate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);


        Spinner spinnerUsersList = findViewById(R.id.interestcategories);
        List<String> users = new ArrayList<String>();

        String[] category = {"Sports", "Bollywood", "Education", "E-Commerce", "LifeStyle"};

        Spinner spino = findViewById(R.id.interestcategories);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);


        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   Toast.makeText(getApplicationContext(),category[position],Toast.LENGTH_SHORT).show();
                               cate=category[position];

                Retrofit retrofit= RetrofitLoginBuilder.getInstance();
                IPostLogin iPostLogin=retrofit.create(IPostLogin.class);
                InterestDto interest=new InterestDto();
                interest.setAppId("3");
                interest.setUserEmail(sharedPreferences.getString("em",""));
                List<String> interestlist=new ArrayList<>();
                String temp="";
                if(cate=="Sports")
                {
                    temp="1";
                }
                if(cate=="LifeStyle")
                {
                    temp="2";
                }
                if(cate=="E-Commerce")
                {
                    temp="3";
                }
                if(cate=="Education")
                {
                    temp="4";
                }
                if(cate=="Bollywood")
                {
                    temp="5";
                }
                interestlist.add(temp);
                interest.setInterests(interestlist);

                Call<Void> interestresponse=iPostLogin.updateInterest(interest);

                interestresponse.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                      //  Toast.makeText(Category.this,"Category sent",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                      //  Toast.makeText(Category.this,"Category cannot be sent",Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Intent k = new Intent(Category.this, Login.class);
//        startActivity(k);
//        finish();
        Button profile = findViewById(R.id.bn_continue);
        profile.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cate",cate);
            editor.apply();
            Intent i = new Intent(Category.this, Login.class);
            startActivity(i);
        });
        Button profile1 = findViewById(R.id.bn_skip);
        profile1.setOnClickListener(v -> {
            Intent i = new Intent(Category.this, Login.class);
            startActivity(i);
        });
    }
}

