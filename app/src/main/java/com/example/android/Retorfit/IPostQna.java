package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.AnswerDto;
import com.example.android.Retorfit.Model.QuestionDto;
import com.example.android.Retorfit.Model.ReactionDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IPostQna {
        @POST("qna/reaction/add")
        Call<Void> save(@Body ReactionDto reactionDto);


        @POST("qna/question/add")
        Call<Void> saveques(@Body QuestionDto questionDto);



        @POST("qna/answer/add")
        Call<Void> saveans(@Body AnswerDto answerDto);

}
