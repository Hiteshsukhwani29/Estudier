package com.scriptech.vstudy;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.scriptech.vstudy.model.booksModel;
import com.scriptech.vstudy.model.sliderModel;
import com.scriptech.vstudy.model.videoModel;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment implements View.OnClickListener {

    private RecyclerView courseRV;
    private ArrayList<booksModel> booksArrayList;
    private bookAdapter courseRVAdapter;

    private RecyclerView VideoRV;
    private videoAdapter videoRVAdapter;
    private ArrayList<videoModel> videoArrayList;

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();


    List<sliderModel> sliderItems = new ArrayList<>();

    TextView CS_T, IT_T, ENTC_T, EE_T, ME_T, CSBS_T;
    ImageView CS_I, IT_I, ENTC_I, EE_I, ME_I, CSBS_I;

    Bundle bundle = new Bundle();


    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.frag_home, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#20111111"));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
        }

        v.findViewById(R.id.card_CS).setOnClickListener(this);
        CS_T = v.findViewById(R.id.card_CS).findViewById(R.id.dept_name);
        CS_T.setText("CS");
        CS_I = v.findViewById(R.id.card_CS).findViewById(R.id.dept_img);
        CS_I.setImageResource(R.drawable.comp);

        v.findViewById(R.id.card_IT).setOnClickListener(this);
        IT_T = v.findViewById(R.id.card_IT).findViewById(R.id.dept_name);
        IT_T.setText("IT");
        IT_I = v.findViewById(R.id.card_IT).findViewById(R.id.dept_img);
        IT_I.setImageResource(R.drawable.it);

        v.findViewById(R.id.card_ENTC).setOnClickListener(this);
        ENTC_T = v.findViewById(R.id.card_ENTC).findViewById(R.id.dept_name);
        ENTC_T.setText("ENTC");
        ENTC_I = v.findViewById(R.id.card_ENTC).findViewById(R.id.dept_img);
        ENTC_I.setImageResource(R.drawable.entc);


        v.findViewById(R.id.card_EE).setOnClickListener(this);
        EE_T = v.findViewById(R.id.card_EE).findViewById(R.id.dept_name);
        EE_T.setText("EE");
        EE_I = v.findViewById(R.id.card_EE).findViewById(R.id.dept_img);
        EE_I.setImageResource(R.drawable.elec);

        v.findViewById(R.id.card_ME).setOnClickListener(this);
        ME_T = v.findViewById(R.id.card_ME).findViewById(R.id.dept_name);
        ME_T.setText("ME");
        ME_I = v.findViewById(R.id.card_ME).findViewById(R.id.dept_img);
        ME_I.setImageResource(R.drawable.mech);

        v.findViewById(R.id.card_CSBS).setOnClickListener(this);
        CSBS_T = v.findViewById(R.id.card_CSBS).findViewById(R.id.dept_name);
        CSBS_T.setText("CSBS");
        CSBS_I = v.findViewById(R.id.card_CSBS).findViewById(R.id.dept_img);
        CSBS_I.setImageResource(R.drawable.csbs);


        courseRV = v.findViewById(R.id.idRVCourses);

        VideoRV = v.findViewById(R.id.rv_videos);
        db = FirebaseFirestore.getInstance();

        booksArrayList = new ArrayList<>();
        videoArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);
        courseRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        courseRVAdapter = new bookAdapter(booksArrayList, getActivity());

        courseRV.setAdapter(courseRVAdapter);


        VideoRV.setHasFixedSize(true);
        VideoRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        videoRVAdapter = new videoAdapter(videoArrayList, getActivity());

        VideoRV.setAdapter(videoRVAdapter);

        ((TextView) v.findViewById(R.id.books_viewall)).setOnClickListener(this);
        ((TextView) v.findViewById(R.id.videos_viewall)).setOnClickListener(this);

        db.collection("Books_trending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                booksModel c = d.toObject(booksModel.class);

                                booksArrayList.add(c);
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


        db.collection("Videos_trending").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                videoModel c = d.toObject(videoModel.class);

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

        viewPager2 = v.findViewById(R.id.viewPagerImageSlider);


        sliderItems.add(new sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture1.jpg?alt=media"));
        sliderItems.add(new sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture2.jpg?alt=media"));
        sliderItems.add(new sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture3.jpg?alt=media"));
        sliderItems.add(new sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture4.jpg?alt=media"));


        viewPager2.setAdapter(new sliderAdapter(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.55f + r * 0.45f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000); // slide duration 5 seconds
            }
        });

        return v;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onClick(View v) {


        Fragment fragment = new Departments();
        switch (v.getId()) {
            case R.id.card_CS:
                bundle.putString("dept", "Computer Science");
                bundle.putString("dept_link", "cs");
                break;
            case R.id.card_IT:
                bundle.putString("dept", "Information Technology");
                bundle.putString("dept_link", "it");
                break;
            case R.id.card_ENTC:
                bundle.putString("dept", "Electronics & Telecommunication");
                bundle.putString("dept_link", "entc");
                break;
            case R.id.card_EE:
                bundle.putString("dept", "Electrical Engineering");
                bundle.putString("dept_link", "ee");
                break;
            case R.id.card_ME:
                bundle.putString("dept", "Mechanical Engineering");
                bundle.putString("dept_link", "me");
                break;
            case R.id.card_CSBS:
                bundle.putString("dept", "Computer Science with\nBusiness Studies");
                bundle.putString("dept_link", "csbs");
                break;
            case R.id.books_viewall:
                fragment = new AllBooks();
                break;
            case R.id.videos_viewall:
                fragment = new AllVideos();
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
