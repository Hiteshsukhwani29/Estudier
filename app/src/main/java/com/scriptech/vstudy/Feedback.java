package com.scriptech.vstudy;


import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.R.layout.simple_spinner_item;
import static android.app.Activity.RESULT_OK;


public class Feedback extends Fragment {


    String[] category = {"Add_Notes", "Add Book", "Add Video", "Report Content", "Feedback"};
    String[] department = {"Computer Engineering", "Information Technology", "Mechanical Engineering", "Civil Engineering", "Chemical Engineering", "CSBS", "Electrical Engineering", "Electronics Engineering", "ENTC", "None"};

    private FirebaseFirestore db;
    EditText name, email, add_notes_name, add_notes_author, add_books_name, add_books_author, add_videos_name, add_videos_author, report_desc, feedback_desc;
    String Name, Num, Email, dept_str;
    int pos, feedback_type = 0, validated = 0;
    String currentuser, docURL = "";
    Button submit, upload;
    Uri imageuri = null;
    Spinner spin_dept;
    ProgressDialog dialog;

    LottieAnimationView FeedbackSubmitted;

    RelativeLayout Add_Notes, Add_Books, Add_Videos, Feedback, Report, feedback_main;

    Map<String, Object> info = new HashMap<>();

    Intent galleryIntent = new Intent();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_feedback, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        Spinner spin = v.findViewById(R.id.spinner);
        spin_dept = v.findViewById(R.id.department_spin);

        name = v.findViewById(R.id.personal_info_name);
        email = v.findViewById(R.id.personal_info_email);

        add_notes_name = v.findViewById(R.id.add_notes_notesname);
        add_notes_author = v.findViewById(R.id.add_notes_notesauthor);

        add_books_name = v.findViewById(R.id.add_books_bookname);
        add_books_author = v.findViewById(R.id.add_books_bookauthor);

        add_videos_name = v.findViewById(R.id.add_video_videoname);
        add_videos_author = v.findViewById(R.id.add_video_videoauthor);

        report_desc = v.findViewById(R.id.report_desc);

        feedback_desc = v.findViewById(R.id.feedback_desc);

        upload = v.findViewById(R.id.upload_file);

        FeedbackSubmitted = v.findViewById(R.id.feedback_submitted);

        feedback_main = v.findViewById(R.id.feeback_main);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);

            }
        });

        FeedbackSubmitted.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e("Animation:", "start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Animation:", "end");
                Fragment fragment = new Feedback();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer1, fragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("Animation:", "cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e("Animation:", "repeat");
            }
        });

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        submit = v.findViewById(R.id.submit_form);


        Add_Notes = v.findViewById(R.id.Add_Notes);
        Add_Books = v.findViewById(R.id.Add_Book);
        Add_Videos = v.findViewById(R.id.Add_Videos);
        Report = v.findViewById(R.id.Report);
        Feedback = v.findViewById(R.id.Feedback);

        hideall();
        ArrayAdapter aa = new ArrayAdapter(getActivity(), simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        ArrayAdapter dept = new ArrayAdapter(getActivity(), simple_spinner_item, department);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_dept.setAdapter(dept);


        if (currentuser.length() > 5) {
            DocumentReference docRef = db.collection("Users").document(currentuser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            Name = document.getString("name");
                            Num = document.getString("number");
                            Email = document.getString("email");
                            name.setText(Name);
                            email.setText(Email);
                        } else {
                            Log.d("LOGGER", "Error");
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });
        }


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pos = position;
                feedback_type = pos;
                switch (position) {
                    case 0: {
                        hideall();
                        upload.setVisibility(View.VISIBLE);
                        Add_Notes.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        hideall();
                        upload.setVisibility(View.VISIBLE);
                        Add_Books.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 2: {
                        hideall();
                        upload.setVisibility(View.VISIBLE);
                        Add_Videos.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 3: {
                        hideall();
                        upload.setVisibility(View.GONE);
                        Report.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 4: {
                        hideall();
                        upload.setVisibility(View.GONE);
                        Feedback.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                hideall();
                upload.setVisibility(View.VISIBLE);
                Add_Notes.setVisibility(View.VISIBLE);
            }
        });


        spin_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                dept_str = department[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = Calendar.getInstance().getTime();

                switch (feedback_type) {
                    case 0:
                        validated = notesValidation();
                        break;
                    case 1:
                        validated = booksValidation();
                        break;
                    case 2:
                        validated = videosValidation();
                        break;
                    case 3:
                        validated = reportValidation();
                        break;
                    case 4:
                        validated = feedbackValidation();
                        break;
                }

                if (validated == 1) {
                    info.put("1_Date", date);
                    info.put("2_Name", Name);
                    info.put("2.1_Number", Num);
                    info.put("2.2_Email", Email);
                    info.put("3_Department", dept_str);
                    info.put("4_Notes_Name", add_notes_name.getText().toString());
                    info.put("4.1_Notes_Author", add_notes_author.getText().toString());
                    info.put("5_Book_Name", add_books_name.getText().toString());
                    info.put("5.1_Book_Author", add_books_author.getText().toString());
                    info.put("6_Video_Name", add_notes_name.getText().toString());
                    info.put("6.1_Video_Author", add_notes_author.getText().toString());
                    info.put("7_Report_Description", add_notes_name.getText().toString());
                    info.put("8_Feedback_Description", feedback_desc.getText().toString());

                    addDataToFirestore();
                }
            }
        });

        return v;
    }

    private void addDataToFirestore() {

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Reports");
        collectionReference.add(info)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        reset();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void hideall() {

        FeedbackSubmitted.setVisibility(View.GONE);
        feedback_main.setVisibility(View.VISIBLE);
        Add_Notes.setVisibility(View.GONE);
        Add_Books.setVisibility(View.GONE);
        Add_Videos.setVisibility(View.GONE);
        Report.setVisibility(View.GONE);
        Feedback.setVisibility(View.GONE);

    }

    public int notesValidation() {

        if (add_notes_name.getText().toString().length() < 5) {
            add_notes_name.setError("Enter Valid Notes Topic");
            add_notes_name.requestFocus();
        }
        if (add_notes_author.getText().toString().length() < 5) {
            add_notes_author.setError("Enter Valid Author Name");
            add_notes_author.requestFocus();
        }
        if (docURL.length() == 0)
            Toast.makeText(getActivity(), "Please Upload Document", Toast.LENGTH_SHORT).show();
        else
            return 1;

        return 0;
    }


    public int booksValidation() {

        if (add_books_name.getText().toString().length() < 5) {
            add_books_name.setError("Enter Valid Book Name");
            add_books_name.requestFocus();
        }
        if (add_books_author.getText().toString().length() < 5) {
            add_books_author.setError("Enter Valid Author Name");
            add_books_author.requestFocus();
        }
        if (docURL.length() == 0)
            Toast.makeText(getActivity(), "Please Upload Document", Toast.LENGTH_SHORT).show();

        else
            return 1;

        return 0;
    }

    public int videosValidation() {

        if (add_videos_name.getText().toString().length() < 5) {
            add_videos_name.setError("Enter Valid Video Name");
            add_videos_name.requestFocus();
        }
        if (add_videos_author.getText().toString().length() < 5) {
            add_videos_author.setError("Enter Valid Author Name");
            add_videos_author.requestFocus();
        }
        if (docURL.length() == 0)
            Toast.makeText(getActivity(), "Please Upload Document", Toast.LENGTH_SHORT).show();

        else
            return 1;

        return 0;

    }

    public int reportValidation() {

        if (report_desc.getText().toString().length() < 5) {
            report_desc.setError("Enter Valid Book Name");
            report_desc.requestFocus();
        } else
            return 1;

        return 0;
    }


    public int feedbackValidation() {

        if (feedback_desc.getText().toString().length() < 5) {
            feedback_desc.setError("Enter Valid Description");
            feedback_desc.requestFocus();
        } else
            return 1;

        return 0;

    }

    public void reset() {

        feedback_main.setVisibility(View.GONE);
        FeedbackSubmitted.setVisibility(View.VISIBLE);
        FeedbackSubmitted.playAnimation();
        add_notes_name.setText(null);
        add_notes_author.setText(null);
        add_books_name.setText(null);
        add_books_author.setText(null);
        add_videos_name.setText(null);
        add_videos_author.setText(null);
        feedback_desc.setText(null);
        report_desc.setText(null);
        docURL = null;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait...");

            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://edu-project-2423e.appspot.com/uploads");
            final String messagePushID = timestamp;

            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        docURL = uri.toString();
                        Toast.makeText(getActivity(), "Click On Submit", Toast.LENGTH_SHORT).show();
                        info.put("9_Document", docURL);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
}

