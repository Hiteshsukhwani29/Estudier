package com.scriptech.vstudy;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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


public class Home extends Fragment implements View.OnClickListener {

    private RecyclerView courseRV;
    private ArrayList<Model> modelArrayList;
    private bookAdapter courseRVAdapter;

    private RecyclerView VideoRV;
    private videoAdapter videoRVAdapter;
    private ArrayList<Model> videoArrayList;

    private FirebaseFirestore db;
    public Home() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.frag_home, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.theme_color));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }

        ((CardView) v.findViewById(R.id.card_CS)).setOnClickListener(this);
        ((CardView) v.findViewById(R.id.card_IT)).setOnClickListener(this);
        ((CardView) v.findViewById(R.id.card_ENTC)).setOnClickListener(this);
        ((CardView) v.findViewById(R.id.card_EE)).setOnClickListener(this);
        ((CardView) v.findViewById(R.id.card_ME)).setOnClickListener(this);
        ((CardView) v.findViewById(R.id.card_CSBS)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.books_viewall)).setOnClickListener(this);

        courseRV = v.findViewById(R.id.idRVCourses);

        VideoRV = v.findViewById(R.id.rv_videos);
        db = FirebaseFirestore.getInstance();

        modelArrayList = new ArrayList<>();
        videoArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));

        courseRVAdapter = new bookAdapter(modelArrayList, getActivity());

        courseRV.setAdapter(courseRVAdapter);





        VideoRV.setHasFixedSize(true);
        VideoRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));

        videoRVAdapter = new videoAdapter(videoArrayList, getActivity());

        VideoRV.setAdapter(videoRVAdapter);

        db.collection("Books_trending").get()
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


        db.collection("Videos").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Model c = d.toObject(Model.class);

                                videoArrayList.add(c);
                            }
                            videoRVAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {

        Bundle bundle = new Bundle();
        Fragment fragment = new Departments();
        switch(v.getId()){
            case R.id.card_CS:
                bundle.putString("dept","Computer Science");
                bundle.putString("dept_link","cs");
                break;
            case R.id.card_IT:
                bundle.putString("dept","Information Technology");
                bundle.putString("dept_link","it");
                break;
            case R.id.card_ENTC:
                bundle.putString("dept","Electronics & Telecommunication");
                bundle.putString("dept_link","entc");
                break;
            case R.id.card_EE:
                bundle.putString("dept","Electrical Engineering");
                bundle.putString("dept_link","ee");
                break;
            case R.id.card_ME:
                bundle.putString("dept","Mechanical Engineering");
                bundle.putString("dept_link","me");
                break;
            case R.id.card_CSBS:
                bundle.putString("dept","Computer Science with\nBusiness Studies");
                bundle.putString("dept_link","csbs");
                break;
            case R.id.books_viewall:
                fragment = new AllBooks();
                break;
        }

        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer1, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
