package com.scriptech.vstudy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptech.vstudy.model.videoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class allvideosAdapter extends RecyclerView.Adapter<allvideosAdapter.ViewHolder> {

    private ArrayList<videoModel> modelArrayList;
    private Context context;


    public allvideosAdapter(ArrayList<videoModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public allvideosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_all_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull allvideosAdapter.ViewHolder holder, int position) {
        videoModel model = modelArrayList.get(position);

        Picasso.get().load(model.getVideoImage()).into(holder.VideoImage);

        holder.VideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new VideoPlayer();
                Bundle bundle = new Bundle();
                bundle.putString("video_Link", model.getVideoLink());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView VideoImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            VideoImage = itemView.findViewById(R.id.video_image);
        }
    }
}