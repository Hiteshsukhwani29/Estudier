//package com.scriptech.vstudy.ui.fragments.departments;
//
//
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.scriptech.vstudy.R;
//import com.scriptech.vstudy.adapters.deptAdapter;
//import com.scriptech.vstudy.model.subjectsModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Departments extends Fragment {
//
//
//    private RecyclerView courseRV;
//    private ArrayList<subjectsModel> modelArrayList;
//    private deptAdapter courseRVAdapter;
//    private FirebaseFirestore db;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View v = inflater.inflate(R.layout.frag_departments, container, false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                getActivity().getWindow().setStatusBarColor(Color.parseColor("#20111111"));
//                getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            }
//        }
//
//
//        String dept = getArguments().getString("dept");
//        String dept_link = getArguments().getString("dept_link");
//
//        TextView tv = (TextView) v.findViewById(R.id.dept_name);
//        tv.setText(dept);
//
//
//        courseRV = v.findViewById(R.id.recyclerProfile);
//        db = FirebaseFirestore.getInstance();
//
//        modelArrayList = new ArrayList<>();
//        courseRV.setHasFixedSize(true);
//        courseRV.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        courseRVAdapter = new deptAdapter(modelArrayList, getActivity());
//
//        courseRV.setAdapter(courseRVAdapter);
//
//        db.collection(dept_link).get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if (!queryDocumentSnapshots.isEmpty()) {
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//                                subjectsModel c = d.toObject(subjectsModel.class);
//
//                                modelArrayList.add(c);
//                            }
//                            courseRVAdapter.notifyDataSetChanged();
//                        } else {
//
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        return v;
//    }
//
//}
