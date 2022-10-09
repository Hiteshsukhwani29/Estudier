package com.scriptech.vstudy.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptech.vstudy.R;
import com.scriptech.vstudy.model.contributorModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class contributorsAdapter extends RecyclerView.Adapter<contributorsAdapter.ViewHolder> {
    private ArrayList<contributorModel> modelArrayList;
    private Context context;

    public contributorsAdapter(ArrayList<contributorModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public contributorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_contributor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull contributorsAdapter.ViewHolder holder, int position) {
        contributorModel model = modelArrayList.get(position);

        Picasso.get().load(model.getContributorImage()).into(holder.contibutorImage);

        holder.contibutorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(model.getContributorLink()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView contibutorImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contibutorImage = itemView.findViewById(R.id.contributor_img);
        }
    }
}