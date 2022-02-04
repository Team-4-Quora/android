package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        ques = findViewById(R.id.ques_post);

        ques.setOnClickListener(view -> {
            addquestion();
        });

    }

    public void addquestion()
    {
        Retrofit retrofit=RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna=retrofit.create(IPostQna.class);

        QuestionDto questionDto=new QuestionDto();
        questionDto.setQuestionBy("vinaymatta63@gmail.com");
        questionDto.setQues("where are you now?");

        Call<Void> quesresponse=iPostQna.saveques(questionDto);
        quesresponse.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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