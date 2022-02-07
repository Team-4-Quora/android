package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.Comment;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiAnswer;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.IPostUser;
import com.example.android.Retorfit.Model.PointRequest;
import com.example.android.Retorfit.Model.ReactionDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;
import com.example.android.Retorfit.RetrofitUserBuilder;
import com.google.gson.internal.$Gson$Preconditions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AnswerPageAdapter extends RecyclerView.Adapter<AnswerPageAdapter.ViewHolderAns>{
    private final List<ApiAnswer> apiResponseList;
    private final IApiResponseClick mUserDataInterface;
    private final Boolean b;
    private final Context context ;
    int like=0,dislike=0;


    public AnswerPageAdapter(List<ApiAnswer> apiResponseList, IApiResponseClick mUserDataInterface, Boolean b, Context context) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface = mUserDataInterface;
        this.b = false;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderAns onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_card,parent, false);
        return new ViewHolderAns(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAns holder, int position) {
        ApiAnswer apiAnswer = apiResponseList.get(position);
        holder.ansName.setText(apiAnswer.getAnswerBy()+"");
        holder.ans.setText(apiAnswer.getMessage()+"");

        if(apiAnswer.getPostedOn()!=null){
            Date date = new Date(apiAnswer.getPostedOn()* 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            holder.ansdate.setText(sdf.format(date)+"");}

//        holder.ansdate.setText(apiAnswer.getPostedOn()+"");

        Retrofit retrofit1= RetrofitQnaBuilder.getInstance();
        IPostQna iPostQna1=retrofit1.create(IPostQna.class);
        Call<List<ReactionDto>> responsereact=iPostQna1.fetchByValue("answer",apiAnswer.getId());
        responsereact.enqueue(new Callback<List<ReactionDto>>() {
            @Override
            public void onResponse(Call<List<ReactionDto>> call, Response<List<ReactionDto>> response) {
                List<ReactionDto> react=response.body();

                for(int i=0;i<react.size();i++)
                {
                    if(react.get(i).getLike()==true && react.get(i)!=null)
                    {
                        like=like+1;

                    }
                    else
                        dislike=dislike+1;
                }
                holder.upcount.setText(like+"");
                holder.downcount.setText(dislike+"");


            }

            @Override
            public void onFailure(Call<List<ReactionDto>> call, Throwable t) {

            }
        });

//        holder.upcount.setText(like+"");
//        holder.downcount.setText(dislike+"");


        holder.upvote.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitQnaBuilder.getInstance();
            IPostQna iPostQna=retrofit.create(IPostQna.class);

            ReactionDto reactionDto=new ReactionDto();
            reactionDto.setLike(true);
            reactionDto.setAnswerId(apiAnswer.getId());
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.android",Context.MODE_PRIVATE);
            String email=sharedPreferences.getString("em","");
            reactionDto.setReactionBy(email);
            Call<Void> reactionresponse=iPostQna.save(reactionDto);
            reactionresponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Liked",Toast.LENGTH_SHORT).show();
//                 holder.upcount.setText(like+"");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"cannot liked",Toast.LENGTH_SHORT).show();

                }
            });
        });


        holder.downvote.setOnClickListener(v -> {
            Retrofit retrofit= RetrofitQnaBuilder.getInstance();
            IPostQna iPostQna=retrofit.create(IPostQna.class);

            ReactionDto reactionDto=new ReactionDto();
            reactionDto.setLike(false);
            reactionDto.setAnswerId(apiAnswer.getId());
            System.out.println("Answer id:::::::: "+apiAnswer.getId());
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.example.android",Context.MODE_PRIVATE);
         String email=sharedPreferences.getString("em","");
            reactionDto.setReactionBy(email);

            Call<Void> reactionresponse=iPostQna.save(reactionDto);
            reactionresponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context,"Dislike",Toast.LENGTH_SHORT).show();
//                    holder.downcount.setText(dislike+"");

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context,"cannot dislike",Toast.LENGTH_SHORT).show();

                }
            });
        });
        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=RetrofitQnaBuilder.getInstance();
                IPostQna iPostQna=retrofit.create(IPostQna.class);
                Call<Void> acceptanswer=iPostQna.setAcceptedAnswer(apiAnswer.getId());
                System.out.println("Check this: answer here"+apiAnswer.getId());

                acceptanswer.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(context,"Accepted answer set",Toast.LENGTH_SHORT).show();
                        String answerBy=apiAnswer.getAnswerBy();

                        Retrofit retrofit2=RetrofitUserBuilder.getInstance();
                        IPostUser iPostUser=retrofit2.create(IPostUser.class);
                        PointRequest pointRequest=new PointRequest();
                        pointRequest.setAmount(Long.parseLong("5"));
                        pointRequest.setEmail(answerBy);
                        pointRequest.setInc(true);
                        Call<Void> userpoints=iPostUser.incUserPoints(pointRequest);
                        userpoints.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(context,"Points increased",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context,"Points increase failed",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Answer cannot be set",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        //  Glide.with(holder.quesimg.getContext()).load(apiAnswer.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.comment.setOnClickListener(v -> {
            Intent i=new Intent(context, Comment.class);
            i.putExtra("answerid",apiAnswer.getId());
            context.startActivity(i);
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiAnswer);
            }
        });

    }

    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiAnswer apiAnswer);
        Boolean b =false;

    }

    public static class ViewHolderAns extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView ansName;
        private final ImageView ansimg;
        private final TextView ansdate;
        private final TextView ans;
        private final ImageButton upvote;
        private final ImageButton downvote;
        private final ImageButton share;
        private final Button  comment;
        private final Button answer;
        private  final TextView upcount;
        private final TextView downcount;



        public ViewHolderAns(View view) {
            super(view);
            rootView = view;
            ans=view.findViewById(R.id.answer);
            ansName=view.findViewById(R.id.ans_name);
            ansimg=view.findViewById(R.id.answer_prof);
            ansdate=view.findViewById(R.id.ans_timestamp);
            share=view.findViewById(R.id.share_bn);
            upvote=view.findViewById(R.id.upvote);
            downvote=view.findViewById(R.id.downvote);
            comment=view.findViewById(R.id.commentbutton);
            answer=view.findViewById(R.id.acceptAnswer);
            upcount=view.findViewById(R.id.upvote_count);
            downcount=view.findViewById(R.id.downvote_count);

        }
    }

}
