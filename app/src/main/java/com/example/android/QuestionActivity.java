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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.Model.QuestionDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuestionActivity extends AppCompatActivity {
    Button ques;
    EditText ques_content;
    String cate="";
    String[] category={"Sports","Bollywood","Education","E-Commerce","LifeStyle"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ques = findViewById(R.id.ques_post);

        ques.setOnClickListener(view -> {
            addquestion();
            Intent i= new Intent(QuestionActivity.this,HomePage.class);
            startActivity(i);
            finish();
        });

        Spinner spino = findViewById(R.id.categories);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);


        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

        spino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        category[position],
//                        Toast.LENGTH_SHORT)
//                        .show();
                cate=category[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addquestion()
    {
        Retrofit retrofit=RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna=retrofit.create(IPostQna.class);

        ques_content=findViewById(R.id.et_ques);

        QuestionDto questionDto=new QuestionDto();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
        String email=sharedPreferences.getString("em","");
       // System.out.println(email+"Here");

        questionDto.setQuestionBy(email);
        questionDto.setText(ques_content.getText().toString());
        System.out.println("===="+questionDto.getText());
        questionDto.setCategory(cate + "");

        //ques_content.getText().clear();

        Call<Void> quesresponse=iPostQna.saveques(questionDto);
        quesresponse.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("=============="+response.body().toString());
                Toast.makeText(QuestionActivity.this,"successfull",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(QuestionActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println("hello");

            }
        });
    }


}