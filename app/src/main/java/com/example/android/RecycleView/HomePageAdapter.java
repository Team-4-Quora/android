package com.example.android.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Answer;
import com.example.android.R;
import com.example.android.RecycleView.Model.ApiAdvertise;
import com.example.android.RecycleView.Model.ApiHome;
import com.example.android.RecycleView.Model.ApiQuestion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG="RecyclerAdapter";
    private final List<ApiQuestion> apiResponseList;
    private final List<ApiAdvertise> apiAdvertiseList;
    private final IApiResponseClick mUserDataInterface;
    private static Integer postsize=0;
    private static Integer adsize=0;
    private final IAddRespClick iAddRespClick;
    private Context context;

    public HomePageAdapter(List<ApiQuestion> apiResponseList, List<ApiAdvertise> apiAdvertiseList, IApiResponseClick mUserDataInterface, IAddRespClick iAddRespClick, Context context) {
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
            viewHolder.viewmore.setOnClickListener(v -> {
                Intent i=new Intent(context, Answer.class);
              //  i.putExtra("QuestionId",apiHome.getId());
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
        private final Button  viewmore;



        public ViewHolderOne(@NonNull View view) {
            super(view);
            rootView = view;
            ques=view.findViewById(R.id.ques);
            quesName=view.findViewById(R.id.ques_name);
            quesimg=view.findViewById(R.id.ques_iv);
            quesdate=view.findViewById(R.id.ques_time);
            viewmore=view.findViewById(R.id.bn_feed);

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