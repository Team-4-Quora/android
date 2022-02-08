package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiOrganisation;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.RecycleView.OrganisationAdapter;
import com.example.android.RecycleView.QuestionPageAdapter;
import com.example.android.Retorfit.IPostSearch;
import com.example.android.Retorfit.Model.OrganisationDto;
import com.example.android.Retorfit.Model.QuestionDto;
import com.example.android.Retorfit.RetrofitSearchBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeSearchQuery extends AppCompatActivity implements QuestionPageAdapter.IApiResponseClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search_query);String search = getIntent().getStringExtra("searchQuery");

        Retrofit retrofit= RetrofitSearchBuilder.getInstance();
        IPostSearch iPostSearch=retrofit.create(IPostSearch.class);

        Call<List<QuestionDto>> quesresponse=iPostSearch.searchQues(search);

        quesresponse.enqueue(new Callback<List<QuestionDto>>() {
            @Override
            public void onResponse(Call<List<QuestionDto>> call, Response<List<QuestionDto>> response) {

                if(response.isSuccessful()&& response.body().size()>0){
                    List<ApiQuestion>  apiQuestions=new ArrayList<>();

                    for(int i=0;i<response.body().size();i++)
                    {
                        ApiQuestion apiQuestion=new ApiQuestion();
                        apiQuestion.setQuestionBy(response.body().get(i).getQuestionBy());
                        apiQuestion.setContent(response.body().get(i).getText());
                        apiQuestion.setCategory(response.body().get(i).getCategory());
                        apiQuestions.add(apiQuestion);
                    }

                    RecyclerView recyclerView = findViewById(R.id.ques_recycle);
                    QuestionPageAdapter recycleViewAdapter = new QuestionPageAdapter(apiQuestions, HomeSearchQuery.this);
                    LinearLayoutManager HorizontalLayout = new LinearLayoutManager(HomeSearchQuery.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(HorizontalLayout);
                    recyclerView.setAdapter(recycleViewAdapter);
                }
                else
                {
                    Toast.makeText(HomeSearchQuery.this,"No such question present",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<QuestionDto>> call, Throwable t) {
                Toast.makeText(HomeSearchQuery.this,"No such question present",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onUserClick(ApiQuestion apiques) {

    }
}