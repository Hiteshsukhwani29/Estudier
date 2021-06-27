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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Departments extends Fragment {


    private RecyclerView courseRV;
    private ArrayList<Model> modelArrayList;
    private deptAdapter courseRVAdapter;
    private FirebaseFirestore db;
    public Departments() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.frag_departments, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.theme_color));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }

        Bundle bundle = this.getArguments();

        String dept = getArguments().getString("dept");
        String dept_link = getArguments().getString("dept_link");

        TextView tv = (TextView) v.findViewById(R.id.txt_IT);
        tv.setText(dept);



        courseRV = v.findViewById(R.id.recyclerProfile);
        db = FirebaseFirestore.getInstance();

        modelArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(getContext()));

        courseRVAdapter = new deptAdapter(modelArrayList, getActivity());

        courseRV.setAdapter(courseRVAdapter);

        db.collection(dept_link).get()
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
