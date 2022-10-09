package com.scriptech.vstudy.ui.fragments.notifications;


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
import com.scriptech.vstudy.R;
import com.scriptech.vstudy.model.notificationModel;
import com.scriptech.vstudy.adapters.notificationAdapter;

import java.util.ArrayList;
import java.util.List;


public class Notifications extends Fragment implements View.OnClickListener {


    private RecyclerView notificationsRV;
    private ArrayList<notificationModel> notificationsArrayList;
    private notificationAdapter notificationsRVadapter;
    private FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_notifications, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        notificationsRV = v.findViewById(R.id.rv_notification);
        notificationsArrayList = new ArrayList<>();
        notificationsRV.setHasFixedSize(true);
        notificationsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsRVadapter = new notificationAdapter(notificationsArrayList, getActivity());
        notificationsRV.setAdapter(notificationsRVadapter);

        db = FirebaseFirestore.getInstance();

        db.collection("notifications").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                notificationModel c = d.toObject(notificationModel.class);

                                notificationsArrayList.add(c);
                            }
                            notificationsRVadapter.notifyDataSetChanged();
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

    }
}



