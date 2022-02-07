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

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {
    String cate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

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
                Toast.makeText(getApplicationContext(),
                        category[position],
                        Toast.LENGTH_SHORT)
                        .show();
                               cate=category[position];

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
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
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

