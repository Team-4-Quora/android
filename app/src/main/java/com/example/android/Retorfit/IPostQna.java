package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.AnswerDto;
import com.example.android.Retorfit.Model.QuestionDto;
import com.example.android.Retorfit.Model.ReactionDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostQna {
        @POST("qna/reaction/add")
        Call<Void> save(@Body ReactionDto reactionDto);


        @POST("qna/question/add")
        Call<Void> saveques(@Body QuestionDto questionDto);

        @GET("qna/answer/fetch/{id}")
        Call<List<AnswerDto>> fetchByQuestionId(@Path(value = "id") String id);

        @POST("qna/answer/add")
        Call<Void> saveans(@Body AnswerDto answerDto);


        @GET("qna/question/fetch/{type}/{value}")
        Call<List<QuestionDto>> fetchquesByValue(@Path(value = "type") String type, @Path(value="value") String value);


        @POST("qna/answer/accepted/{ansId}")
        Call<Void> setAcceptedAnswer(@Path(value = "ansId")String ansId);

}
