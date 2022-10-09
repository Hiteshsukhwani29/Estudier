package com.scriptech.vstudy.ui.fragments.aboutUs;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.scriptech.vstudy.R;
import com.scriptech.vstudy.adapters.contributorsAdapter;
import com.scriptech.vstudy.model.contributorModel;

import java.util.ArrayList;


public class AboutUs extends Fragment {


    TextView founder1_n, founder2_n, cofounder1_n;
    ImageView founder1_i, founder2_i, cofounder1_i, founder1_linkedin, founder2_linkedin, cofounder1_linkedin, founder1_insta, founder2_insta, cofounder1_insta;

    CardView JoinTeam;

    private RecyclerView contributorsRV;
    private ArrayList<contributorModel> contributorsArrayList;
    private contributorsAdapter contributorsRVadapter;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_about_us, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        founder1_n = v.findViewById(R.id.card_founder_1).findViewById(R.id.team_mem_name);
        founder2_n = v.findViewById(R.id.card_founder_2).findViewById(R.id.team_mem_name);
      //  cofounder1_n = v.findViewById(R.id.card_cofounder_1).findViewById(R.id.team_mem_name);

        founder1_i = v.findViewById(R.id.card_founder_1).findViewById(R.id.team_mem_img);
        founder2_i = v.findViewById(R.id.card_founder_2).findViewById(R.id.team_mem_img);
      //  cofounder1_i = v.findViewById(R.id.card_cofounder_1).findViewById(R.id.team_mem_img);

        founder1_linkedin = v.findViewById(R.id.card_founder_1).findViewById(R.id.team_mem_img_linkedin);
        founder2_linkedin = v.findViewById(R.id.card_founder_2).findViewById(R.id.team_mem_img_linkedin);
      //  cofounder1_linkedin = v.findViewById(R.id.card_cofounder_1).findViewById(R.id.team_mem_img_linkedin);
        founder1_insta = v.findViewById(R.id.card_founder_1).findViewById(R.id.team_mem_img_insta);
        founder2_insta = v.findViewById(R.id.card_founder_2).findViewById(R.id.team_mem_img_insta);
     //   cofounder1_insta = v.findViewById(R.id.card_cofounder_1).findViewById(R.id.team_mem_img_insta);

        founder1_n.setText("Hitesh Sukhwani");
        founder2_n.setText("Saurav Singh");
      //  cofounder1_n.setText("Pratham Vaidya");

        founder1_i.setImageResource(R.drawable.hitesh);
        founder2_i.setImageResource(R.drawable.saurav);
     //   cofounder1_i.setImageResource(R.drawable.pratham);

      //  JoinTeam = v.findViewById(R.id.join_team);

        founder1_linkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://www.linkedin.com/in/hitesh-sukhwani-14b722135");
            }
        });
        founder2_linkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://www.linkedin.com/in/saurav-singh-6bb8a31b2");
            }
        });
      /*  cofounder1_linkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://www.linkedin.com/in/pratham-vaidya-a196a8183");
            }
        });
*/
        founder1_insta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://instagram.com/sukhwanihitesh_?utm_medium=copy_link");
            }
        });
        founder2_insta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://instagram.com/saurav_singh_rathore_?utm_medium=copy_link");
            }
        });
        /*cofounder1_insta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicked("https://instagram.com/prathamvaidya?utm_medium=copy_link");
            }
        });*/

        /*JoinTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new webview();
                Bundle bundle = new Bundle();
                bundle.putString("URL", "https://www.notion.so/Join-Our-Team-443ee360e03249daa9407fb70305340d");
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer1, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
*/

       // contributorsRV = v.findViewById(R.id.rv_contributors);
       /* contributorsArrayList = new ArrayList<>();
        contributorsRV.setHasFixedSize(true);
        contributorsRV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        contributorsRVadapter = new contributorsAdapter(contributorsArrayList, getActivity());
        contributorsRV.setAdapter(contributorsRVadapter);

        db = FirebaseFirestore.getInstance();

        db.collection("contributors").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                contributorModel c = d.toObject(contributorModel.class);

                                contributorsArrayList.add(c);
                            }
                            contributorsRVadapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });*/


        return v;
    }

    private void clicked(String s) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(s));
        startActivity(intent);
    }

}
