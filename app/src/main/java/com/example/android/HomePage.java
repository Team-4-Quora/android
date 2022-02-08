package com.example.android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.RecycleView.HomePageAdapter;
import com.example.android.RecycleView.Model.ApiAdvertise;
import com.example.android.RecycleView.Model.ApiHome;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.Model.QuestionDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePage extends AppCompatActivity implements HomePageAdapter.IApiResponseClick , HomePageAdapter.IAddRespClick {

    String cate = "Random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView question = findViewById(R.id.iv_question_bottom);
        question.setOnClickListener(v -> {
            Intent i = new Intent(HomePage.this, QuestionActivity.class);
            startActivity(i);

        });

        ImageView profile = findViewById(R.id.iv_profile_bottom);
        profile.setOnClickListener(v -> {
            Intent i = new Intent(HomePage.this, Profile.class);
            startActivity(i);
        });


        findViewById(R.id.bn_sports).setOnClickListener(v -> {
            cate = "Sports";
            displayeretro();

        });

        findViewById(R.id.bn_lifestyle).setOnClickListener(v -> {
            cate = "LifeStyle";
            displayeretro();

        });

        findViewById(R.id.bn_commerce).setOnClickListener(v -> {
            cate = "E-Commerce";
            displayeretro();

        });

        findViewById(R.id.bn_education).setOnClickListener(v -> {
            cate = "Education";
            displayeretro();
        });

        findViewById(R.id.bn_bollywood).setOnClickListener(v -> {
            cate = "Bollywood";
            displayeretro();

        });

       // displayRecycle(cate);
        SearchView searchView;
        searchView = findViewById(R.id.et_home_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent i = new Intent(getApplicationContext(), HomeSearchQuery.class);
                i.putExtra("searchQuery", query);
                startActivity(i);
                return  true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public void displayRecycle(String cate) {
        List<ApiQuestion> userDataList = new ArrayList<>();
        generatedata(userDataList);


        //for add
        List<ApiAdvertise> userAdsDataList = new ArrayList<>();
        generateads(userAdsDataList);


        //render recycle view
        RecyclerView recyclerView = findViewById(R.id.recycleFeed);
        HomePageAdapter recycleViewAdapter = new HomePageAdapter(userDataList, userAdsDataList, HomePage.this, HomePage.this, HomePage.this,0,0);
        LinearLayoutManager VerticalLayout = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(VerticalLayout);
        recyclerView.setAdapter(recycleViewAdapter);

    }

    private void generatedata(List<ApiQuestion> apiQuestions) {

        apiQuestions.add(new ApiQuestion("Palak", "what is your fav colour?"));
        apiQuestions.add(new ApiQuestion("Palak1", "what is your fav colour?"));
        apiQuestions.add(new ApiQuestion("Palak2", "what is your fav colour?"));

    }

    private  void displayeretro(){

    List<ApiQuestion> userDataList = new ArrayList<>();

    //generateRetrodata(userDataList);


    //for add
    List<ApiAdvertise> userAdsDataList = new ArrayList<>();

    generateads(userAdsDataList);


        Retrofit retrofit= RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna=retrofit.create(IPostQna.class);
        Call<List<QuestionDto>> feedQues=iPostQna.fetchquesByValue("category",cate+"");

        feedQues.enqueue(new Callback<List<QuestionDto>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<QuestionDto>> call, Response<List<QuestionDto>> response) {
                List<QuestionDto> questionDto=response.body();
                System.out.println("I am herre");
                // System.out.println(response.body().get(0).getText());

                for(int i=0;i<questionDto.size();i++)
                {
                    ApiQuestion apiQuestion=new ApiQuestion();
                    apiQuestion.setQuestionBy(questionDto.get(i).getQuestionBy());
                    apiQuestion.setPostedOn(questionDto.get(i).getPostedOn());
                    apiQuestion.setContent(questionDto.get(i).getText());
                    apiQuestion.setCategory(questionDto.get(i).getCategory());
                    apiQuestion.setId(questionDto.get(i).getId());
                    System.out.println(apiQuestion.getContent()+"Question here");
                    userDataList.add(apiQuestion);
                }
                userDataList.sort((a,b) -> (int) (b.getPostedOn()-a.getPostedOn()));
                RecyclerView recyclerView = findViewById(R.id.recycleFeed);
                HomePageAdapter recycleViewAdapter = new HomePageAdapter(userDataList, userAdsDataList, HomePage.this, HomePage.this, HomePage.this,0,0);
                LinearLayoutManager VerticalLayout = new LinearLayoutManager(HomePage.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(VerticalLayout);
                recyclerView.setAdapter(recycleViewAdapter);

//                Toast.makeText(HomePage.this,"Success get ques",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<QuestionDto>> call, Throwable t) {
//                Toast.makeText(HomePage.this,t.getMessage(),Toast.LENGTH_SHORT).show();

                System.out.println(t.getMessage() + "Error here");
            }
        });

}

    private void generateads(List<ApiAdvertise> apiAdvertises)
    {
        apiAdvertises.add(new ApiAdvertise("https://m.media-amazon.com/images/I/81HgVEqBVuL._SL1500_.jpg"));
        apiAdvertises.add(new ApiAdvertise("https://genderbasedstereotypeswithinadvertisements.files.wordpress.com/2017/10/einstein12.jpg"));
        apiAdvertises.add(new ApiAdvertise("https://www.designyourway.net/blog/wp-content/uploads/2017/12/adidas_ad_3_by_mdr9inchnail.jpg"));
        apiAdvertises.add(new ApiAdvertise("https://static.india.com/wp-content/uploads/2019/06/45-1-415x246.jpg"));

    }

    @Override
    public void onUserClick(ApiQuestion apiQuestion) {
//        Intent i=new Intent(HomePage.this,Answer.class);
//        startActivity(i);
    }

    @Override
    public void onUserClickadd(ApiAdvertise apiAdvertise) {

    }
}