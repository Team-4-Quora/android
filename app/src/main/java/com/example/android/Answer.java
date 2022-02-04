package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.RecycleView.AnswerPageAdapter;
import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.Model.AnswerDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Answer extends AppCompatActivity implements AnswerPageAdapter.IApiResponseClick{

    EditText ans_text;
    Button post_ans;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        i=getIntent();
        String quesText=i.getExtras().getString("QuesText");
//        String quesId=i.getExtras().getString("QuestionId");

        TextView ques_text=findViewById(R.id.tv_curr_ques);
        ques_text.setText(quesText);
        post_ans=findViewById(R.id.ans_post);
        post_ans.setOnClickListener(v -> {
            saveAnswer();
            ans_text.getText().clear();
        });
        displayRecycle();
    }

    private  void saveAnswer()
    {
        Retrofit retrofit= RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna=retrofit.create(IPostQna.class);

        ans_text=findViewById(R.id.et_ans);
        AnswerDto answerDto=new AnswerDto();
        answerDto.setMessage(ans_text.getText().toString());
        Call<Void> ansresponse=iPostQna.saveans(answerDto);

        ansresponse.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(Answer.this,"Answer posted sucessfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(Answer.this,"Answer post unsucessfull",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void displayRecycle(){
        List<ApiAnswer> userDataList=new ArrayList<>();
        generatedata(userDataList);
        RecyclerView recyclerView=findViewById(R.id.recycleans);
        AnswerPageAdapter recycleViewAdapter=new AnswerPageAdapter(userDataList,Answer.this);
        LinearLayoutManager VerticalLayout= new LinearLayoutManager(Answer.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }

    private void generatedata(List<ApiAnswer> apiQuestions)
    {
        apiQuestions.add(new ApiAnswer("Palak","what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?what is your fav colour?"));
        apiQuestions.add(new ApiAnswer("Palak1","what is your fav colour?"));
        apiQuestions.add(new ApiAnswer("Palak2","what is your fav colour?"));

    }

    @Override
    public void onUserClick(ApiAnswer apiproduct) {
    }

}