package com.scriptech.vstudy;


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

import java.util.ArrayList;
import java.util.List;

public class Subject extends Fragment {


    private RecyclerView courseRV;
    private ArrayList<Model> modelArrayList;
    private subAdapter courseRVAdapter;
    private FirebaseFirestore db;
    public Subject() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.frag_subjects, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.theme_color));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }
        Bundle bundle = this.getArguments();

        String sub = getArguments().getString("sub");
        String sub_link = getArguments().getString("sub_link");

        TextView tv = (TextView) v.findViewById(R.id.txt_IT);
        tv.setText(sub);



        courseRV = v.findViewById(R.id.recyclerProfile);
        db = FirebaseFirestore.getInstance();

        modelArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(getContext()));

        courseRVAdapter = new subAdapter(modelArrayList, getActivity());

        courseRV.setAdapter(courseRVAdapter);

        db.collection(sub_link).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Model c = d.toObject(Model.class);

                                modelArrayList.add(c);
                            }
                            courseRVAdapter.notifyDataSetChanged();
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
