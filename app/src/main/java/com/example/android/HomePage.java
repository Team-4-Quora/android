package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView question=findViewById(R.id.iv_question_bottom);
        question.setOnClickListener(v ->{
            Intent i=new Intent(HomePage.this,QuestionActivity.class);
            startActivity(i);
        });
    }
}