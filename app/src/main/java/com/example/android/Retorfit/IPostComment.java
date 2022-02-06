package com.example.android.Retorfit;

import com.example.android.Retorfit.Model.CommentDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostComment {
    @GET("qna/comment/fetch/{id}/{parentId}")
    Call<List<CommentDto>> fetchcomment(@Path(value = "id") String id, @Path(value = "parentId") String parent);

    @GET("qna/comment/fetch/{id}")
    Call<List<CommentDto>> fetcheachcomment(@Path(value = "id") String id);

    @POST("qna/comment/add")
    Call<Void> savecomment(@Body CommentDto commentDto);
}
