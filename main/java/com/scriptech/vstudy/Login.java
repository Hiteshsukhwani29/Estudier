package com.scriptech.vstudy;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Fragment implements View.OnClickListener {

    EditText mobile,name;

    String no,Name;

    public Login() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_login, container, false);

        mobile = (EditText) v.findViewById(R.id.et_MobileNos);
        name = (EditText) v.findViewById(R.id.et_Name);

        ((Button) v.findViewById(R.id.btn_getOTP)).setOnClickListener(this);
        return v;
  }

    @Override
    public void onClick(View v) {

            no = mobile.getText().toString();
            Name = name.getText().toString();
            if (no.length() != 10) {
            mobile.setError("Enter a valid mobile");
            mobile.requestFocus();
            }
            if (Name.isEmpty()) {
                name.setError("Enter a valid Name");
                name.requestFocus();
            }
            else {

                    Fragment fragment = new Otp();
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile", no);
                    bundle.putString("name", Name);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

    }

