package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiAdvertise;
import com.example.android.RecycleView.Model.ApiHome;
import com.example.android.RecycleView.Model.ApiQuestion;
import com.example.android.Retorfit.IPostQna;
import com.example.android.Retorfit.Model.AnswerDto;
import com.example.android.Retorfit.RetrofitQnaBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG="RecyclerAdapter";
    private final List<ApiQuestion> apiResponseList;
    private final List<ApiAdvertise> apiAdvertiseList;
    private final IApiResponseClick mUserDataInterface;
//    private final static Integer postsize;
//    private final static Integer adsize;
    private final IAddRespClick iAddRespClick;
    private Context context;

    public HomePageAdapter(List<ApiQuestion> apiResponseList, List<ApiAdvertise> apiAdvertiseList, IApiResponseClick mUserDataInterface, IAddRespClick iAddRespClick, Context context)
    {
        this.apiResponseList = apiResponseList;
        this.apiAdvertiseList = apiAdvertiseList;
        this.mUserDataInterface = mUserDataInterface;
        this.iAddRespClick = iAddRespClick;
        this.context = context;

    }

    //    public HomePageAdapter(List<ApiHome> apiResponseList, List<ApiAdvertise> apiAdvertiseList, IApiResponseClick mUserDataInterface, Context context) {
//        this.apiResponseList = apiResponseList;
//        this.apiAdvertiseList = apiAdvertiseList;
//        this.mUserDataInterface = mUserDataInterface;
//        this.context = context;
//    }
    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 0;
        }
        return 1;
//        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.question_card, parent, false);
            return new ViewHolderOne(view);}

        else{ view = layoutInflater.inflate(R.layout.card_advertise, parent, false);
            return new EmptyViewHolder(view);}
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position%2==0 && apiResponseList.size()>postsize )
        {
            ViewHolderOne viewHolder=(ViewHolderOne) holder;
            ApiQuestion apiHome = apiResponseList.get(postsize);
            postsize++;
            viewHolder.quesName.setText(apiHome.getQuestionBy());
            viewHolder.ques.setText(apiHome.getContent()+"");

            if(apiHome.getPostedOn()!=null){
            Date date = new Date(apiHome.getPostedOn()* 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            viewHolder.quesdate.setText(sdf.format(date)+"");}


            if(apiHome.getId()!=null) {
                Retrofit retrofit = RetrofitQnaBuilder.getInstance();
                IPostQna iPostQna = retrofit.create(IPostQna.class);
                Call<AnswerDto> acceptedans = iPostQna.getAcceptedAnswer(apiHome.getId());
                System.out.println("Accepted answer not displayed" + apiHome.getContent());

                acceptedans.enqueue(new Callback<AnswerDto>() {
                    @Override
                    public void onResponse(Call<AnswerDto> call, Response<AnswerDto> response) {
                        viewHolder.ans.setText(response.body().getMessage());

                        Date date = new Date(response.body().getPostedOn()* 1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        viewHolder.ansdate.setText(sdf.format(date)+"");
                   //     viewHolder.ansdate.setText(response.body().getPostedOn() + "");
                        viewHolder.ansName.setText(response.body().getAnswerBy() + "");

                        Toast.makeText(context, "Accepted answer displayed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<AnswerDto> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("Accepted view"+t.getMessage());

                    }
                });
            }

            viewHolder.viewmore.setOnClickListener(v -> {
                Intent i=new Intent(context, Answer.class);
                System.out.println("Die here::::"+apiHome.getId());
               i.putExtra("QuestionId",apiHome.getId());
                i.putExtra("QuesText",apiHome.getContent());
                context.startActivity(i);
            });
            ((ViewHolderOne) holder).rootView.setOnClickListener(v -> mUserDataInterface.onUserClick(apiHome));
        }
        else if (apiAdvertiseList.size() >adsize && position%2!=0){
//           EmptyViewHolder emptyViewHolder=(EmptyViewHolder) holder;
            ApiAdvertise apiAdvertise = apiAdvertiseList.get(adsize);
            adsize++;
//            Glide.with(((EmptyViewHolder) holder).advertiseimg.getContext()).load(apiAdvertise.getImage()).placeholder(R.drawable.ic_login).into(((EmptyViewHolder) holder).advertiseimg);
//            RequestOptions options = new RequestOptions().dontTransform()
//                    .diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(placeholder);
//            if (null != context && !TextUtils.isEmpty("https://m.media-amazon.com/images/I/81HgVEqBVuL._SL1500_.jpg") && null != imageView) {//                Glide.with(context).load(getGlideUrl("https://m.media-amazon.com/images/I/81HgVEqBVuL._SL1500_.jpg")).apply(options).into(imageView);//            }
            ((EmptyViewHolder) holder).rootView2.setOnClickListener(v ->
                    iAddRespClick.onUserClickadd(apiAdvertise));
        }
    }

    @Override
    public int getItemCount() {
        return apiResponseList.size()+apiAdvertiseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiQuestion apiQuestion);
    }

    public interface IAddRespClick{
        void onUserClickadd(ApiAdvertise apiAdvertise);
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView quesName;
        private final ImageView quesimg;
        private final TextView quesdate;
        private final TextView ques;
        private final TextView ans;
        private final TextView ansName;
        private final TextView ansdate;

        private final Button  viewmore;



        public ViewHolderOne(@NonNull View view) {
            super(view);
            rootView = view;
            ques=view.findViewById(R.id.ques);
            quesName=view.findViewById(R.id.ques_name);
            quesimg=view.findViewById(R.id.ques_iv);
            quesdate=view.findViewById(R.id.ques_time);
            viewmore=view.findViewById(R.id.bn_feed);
            ans=view.findViewById(R.id.feed_answer);
            ansName=view.findViewById(R.id.feed_ans_name);
            ansdate=view.findViewById(R.id.feed_ans_timestamp);


        }
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        private final View rootView2;
        private final ImageView advertiseimg;

        public EmptyViewHolder(@NonNull View view) {
            super(view);
            rootView2 = view;
            advertiseimg=view.findViewById(R.id.iv_advert_img);

        }
    }
}