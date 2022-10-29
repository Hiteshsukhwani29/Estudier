//package com.scriptech.vstudy.adapters;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.scriptech.vstudy.ui.fragments.pdf.Pdf;
//import com.scriptech.vstudy.R;
//import com.scriptech.vstudy.model.notesModel;
//
//import java.util.ArrayList;
//
//
//public class subAdapter extends RecyclerView.Adapter<subAdapter.ViewHolder> {
//    private ArrayList<notesModel> modelArrayList;
//    private Context context;
//
//    public subAdapter(ArrayList<notesModel> modelArrayList, Context context) {
//        this.modelArrayList = modelArrayList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public subAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_subject, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull subAdapter.ViewHolder holder, int position) {
//        notesModel model = modelArrayList.get(position);
//
//        holder.NotesName.setText(model.getNotesName());
//
//        holder.NotesName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new Pdf();
//                Bundle bundle = new Bundle();
//                bundle.putString("Link", model.getNotesLink());
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameContainer1, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return modelArrayList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private final TextView NotesName;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            NotesName = itemView.findViewById(R.id.txt_sub_notes);
//
//        }
//    }
//}