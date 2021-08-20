package com.scriptech.vstudy;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.scriptech.vstudy.model.videoModel;

import java.util.ArrayList;
import java.util.List;

public class AllVideos extends Fragment {


    private RecyclerView trending_allvideosRV, new_allvideosRV;
    private ArrayList<videoModel> trending_allvideosArrayList, new_allvideosArrayList;
    private videoAdapter trending_allvideosRVadapter;
    private allvideosAdapter new_allvideosRVadapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_allvideos, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        trending_allvideosRV = v.findViewById(R.id.rv_trending_video);
        new_allvideosRV = v.findViewById(R.id.rv_new_video);

        trending_allvideosArrayList = new ArrayList<>();
        new_allvideosArrayList = new ArrayList<>();

        trending_allvideosRV.setHasFixedSize(true);
        new_allvideosRV.setHasFixedSize(true);

        trending_allvideosRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        new_allvideosRV.setLayoutManager(new LinearLayoutManager(getContext()));

        trending_allvideosRVadapter = new videoAdapter(trending_allvideosArrayList, getActivity());
        new_allvideosRVadapter = new allvideosAdapter(new_allvideosArrayList, getActivity());

        trending_allvideosRV.setAdapter(trending_allvideosRVadapter);
        new_allvideosRV.setAdapter(new_allvideosRVadapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Videos_trending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                videoModel c = d.toObject(videoModel.class);

                                trending_allvideosArrayList.add(c);
                            }
                            trending_allvideosRVadapter.notifyDataSetChanged();
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


        db.collection("Videos_all").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                videoModel c = d.toObject(videoModel.class);

                                new_allvideosArrayList.add(c);
                            }
                            new_allvideosRVadapter.notifyDataSetChanged();
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
