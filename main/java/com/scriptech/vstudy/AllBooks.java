package com.scriptech.vstudy;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllBooks extends Fragment {


    private RecyclerView trending_allbooksRV,new_allbooksRV;
    private ArrayList<Model> trending_allbooksArrayList,new_allbooksArrayList;
    private bookAdapter trending_allbooksRVadapter;
    private allbookAdapter new_allbooksRVadapter;
    private FirebaseFirestore db;

    public AllBooks() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_allbooks, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.theme_color));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }

        trending_allbooksRV = v.findViewById(R.id.rv_trending_book);
        new_allbooksRV = v.findViewById(R.id.rv_new_book);

        trending_allbooksArrayList = new ArrayList<>();
        new_allbooksArrayList = new ArrayList<>();
        trending_allbooksRV.setHasFixedSize(true);
        new_allbooksRV.setHasFixedSize(true);
        trending_allbooksRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));
        new_allbooksRV.setLayoutManager(new LinearLayoutManager(getContext()));

        trending_allbooksRVadapter = new bookAdapter(trending_allbooksArrayList, getActivity());
        new_allbooksRVadapter = new allbookAdapter(new_allbooksArrayList, getActivity());

        trending_allbooksRV.setAdapter(trending_allbooksRVadapter);
        new_allbooksRV.setAdapter(new_allbooksRVadapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Books_trending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Model c = d.toObject(Model.class);

                                trending_allbooksArrayList.add(c);
                            }
                            trending_allbooksRVadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });


        db.collection("Books_all").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Model c = d.toObject(Model.class);

                                new_allbooksArrayList.add(c);
                            }
                            new_allbooksRVadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });



        return v;
    }

}
