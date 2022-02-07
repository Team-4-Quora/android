package com.example.android.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.RecycleView.Model.ApiFollowers;
import com.example.android.RecycleView.Model.ApiOrganisation;

import java.util.List;

public class OrganisationAdapter extends RecyclerView.Adapter<OrganisationAdapter.ViewHolderOrg>{
    private final List<ApiOrganisation> apiResponseList;
    private final IApiResponseClick mUserDataInterface;


    public OrganisationAdapter(List<ApiOrganisation> apiResponseList, IApiResponseClick iApiResponseClick) {
        this.apiResponseList = apiResponseList;
        this.mUserDataInterface=iApiResponseClick;

    }

    @NonNull
    @Override
    public ViewHolderOrg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_org, parent, false);
        return new ViewHolderOrg(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderOrg holder, int position) {
        ApiOrganisation apiorg = apiResponseList.get(position);
        holder.orgname.setText(apiorg.getName() + "");
        holder.orgowner.setText(apiorg.getOwner()+"");
        holder.orgdesc.setText(apiorg.getDescription()+"");


        //  Glide.with(holder.quesimg.getContext()).load(apiHome.getImage()).placeholder(R.drawable.ic_login).into(holder.quesimg);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDataInterface.onUserClick(apiorg);
            }
        });

    }
    @Override
    public int getItemCount() {
        return apiResponseList.size();
    }

    public interface IApiResponseClick {
        void onUserClick(ApiOrganisation apiorg);
    }

    public static class ViewHolderOrg extends RecyclerView.ViewHolder {
        private final View rootView;
        private final ImageView orgimg;
        private final TextView orgname;
        private final TextView orgowner;
        private final TextView orgdesc;

        public ViewHolderOrg(View view) {
            super(view);
            rootView = view;
            orgname=view.findViewById(R.id.tv_org_name);
            orgimg=view.findViewById(R.id.iv_org_img);
            orgowner=view.findViewById(R.id.tv_org_owner);
            orgdesc=view.findViewById(R.id.tv_org_desc);

        }
    }
}
