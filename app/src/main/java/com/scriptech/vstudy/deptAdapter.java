package com.scriptech.vstudy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.scriptech.vstudy.model.subjectsModel;

import java.util.ArrayList;


public class deptAdapter extends RecyclerView.Adapter<deptAdapter.ViewHolder> {
    private ArrayList<subjectsModel> modelArrayList;
    private Context context;

    public deptAdapter(ArrayList<subjectsModel> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public deptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_subject, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull deptAdapter.ViewHolder holder, int position) {
        subjectsModel model = modelArrayList.get(position);

        holder.SubName.setText(model.getSubName());

        holder.SubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Subject();
                Bundle bundle = new Bundle();
                bundle.putString("sub", model.getSubName());
                bundle.putString("sub_link", model.getSubLink());
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

        private final TextView SubName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SubName = itemView.findViewById(R.id.txt_sub_notes);
        }
    }
}