package com.scriptech.vstudy;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.scriptech.vstudy.model.booksModel;
import com.scriptech.vstudy.model.notesModel;
import com.scriptech.vstudy.model.videoModel;

import java.util.ArrayList;
import java.util.List;

public class Subject extends Fragment {


    private RecyclerView notesRV, booksRV, videosRV;
    private ArrayList<videoModel> videosArrayList;
    private ArrayList<notesModel> notesArrayList;
    private ArrayList<booksModel> booksArrayList;
    private subAdapter notesRVAdapter;
    private bookAdapter booksRVAdapter;
    private videoAdapter videosRVAdapter;
    private FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_subjects, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#20111111"));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }

        String sub = getArguments().getString("sub");
        String sub_link = getArguments().getString("sub_link");

        TextView tv = (TextView) v.findViewById(R.id.sub_name);
        tv.setText(sub);


        notesRV = v.findViewById(R.id.rv_sub_notes);
        booksRV = v.findViewById(R.id.rv_sub_books);
        videosRV = v.findViewById(R.id.rv_sub_videos);


        db = FirebaseFirestore.getInstance();

        notesArrayList = new ArrayList<>();
        notesRV.setHasFixedSize(true);
        notesRV.setLayoutManager(new LinearLayoutManager(getContext()));

        booksArrayList = new ArrayList<>();
        booksRV.setHasFixedSize(true);
        booksRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        videosArrayList = new ArrayList<>();
        videosRV.setHasFixedSize(true);
        videosRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        notesRVAdapter = new subAdapter(notesArrayList, getActivity());
        booksRVAdapter = new bookAdapter(booksArrayList, getActivity());
        videosRVAdapter = new videoAdapter(videosArrayList, getActivity());

        notesRV.setAdapter(notesRVAdapter);
        booksRV.setAdapter(booksRVAdapter);
        videosRV.setAdapter(videosRVAdapter);

        db.collection(sub_link).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                notesModel c = d.toObject(notesModel.class);

                                notesArrayList.add(c);
                            }
                            notesRVAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection(sub_link + "_books").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                booksModel c = d.toObject(booksModel.class);

                                booksArrayList.add(c);
                            }
                            booksRVAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });

        db.collection(sub_link + "_videos").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                videoModel c = d.toObject(videoModel.class);

                                videosArrayList.add(c);
                            }
                            videosRVAdapter.notifyDataSetChanged();
                        } else {

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
