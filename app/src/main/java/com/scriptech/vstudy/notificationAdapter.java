package com.scriptech.vstudy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptech.vstudy.model.notificationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {

    private ArrayList<notificationModel> modelArrayList;
    private Context context;


    public notificationAdapter(ArrayList<notificationModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public notificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notificationAdapter.ViewHolder holder, int position) {
        notificationModel model = modelArrayList.get(position);

        Picasso.get().load(model.getNotificationImage()).into(holder.NotificationImage);
        holder.NotificationName.setText(model.getNotificationName());
        holder.NotificationDate.setText(model.getNotificationDate());
        holder.Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new webview();
                Bundle bundle = new Bundle();
                bundle.putString("URL", model.getNotificationLink());
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
        private final ImageView NotificationImage;
        private final TextView NotificationDate;
        private final TextView NotificationName;
        private final RelativeLayout Notification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Notification = itemView.findViewById(R.id.notification);

            NotificationImage = itemView.findViewById(R.id.notification_img);
            NotificationName = itemView.findViewById(R.id.notification_name);
            NotificationDate = itemView.findViewById(R.id.notification_date);
        }
    }
}