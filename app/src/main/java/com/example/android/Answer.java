package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.android.Retorfit.RetrofitUserBuilder;

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
    String quesId,quesText;
  private Boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        i=getIntent();
        quesText=i.getExtras().getString("QuesText");
        quesId=i.getExtras().getString("QuestionId");

        System.out.println("Question Id Here::::::"+quesId);
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
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.android", Context.MODE_PRIVATE);
        String email=sharedPreferences.getString("em","");
        answerDto.setAnswerBy(email);
        answerDto.setQuestionId(quesId);
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
     //   generatedata(userDataList);

        Retrofit retrofit= RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna=retrofit.create(IPostQna.class);
        Call<List<AnswerDto>> answerlist=iPostQna.fetchByQuestionId(quesId);
        answerlist.enqueue(new Callback<List<AnswerDto>>() {
            @Override
            public void onResponse(Call<List<AnswerDto>> call, Response<List<AnswerDto>> response) {
                List<AnswerDto> temp=response.body();

                for(int i=0;i<temp.size();i++)
                {
                    ApiAnswer answerDto=new ApiAnswer();
                    answerDto.setMessage(temp.get(i).getMessage());
                    answerDto.setAnswerBy(temp.get(i).getAnswerBy());
                    answerDto.setPostedOn(temp.get(i).getPostedOn());
                    answerDto.setId(temp.get(i).getId());
                    answerDto.setQuestionId(quesId);

                    userDataList.add(answerDto);


                    RecyclerView recyclerView=findViewById(R.id.recycleans);
                    AnswerPageAdapter recycleViewAdapter=new AnswerPageAdapter(userDataList,Answer.this,b,Answer.this);
                    LinearLayoutManager VerticalLayout= new LinearLayoutManager(Answer.this,LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(VerticalLayout);
                    recyclerView.setAdapter(recycleViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<AnswerDto>> call, Throwable t) {

            }
        });

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