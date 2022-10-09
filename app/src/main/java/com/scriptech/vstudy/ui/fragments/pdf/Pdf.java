package com.scriptech.vstudy.ui.fragments.pdf;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.LottieAnimationView;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.scriptech.vstudy.R;
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Pdf extends Fragment {


    PDFView pdfView;

    String pdfurl;
    LottieAnimationView pb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_pdf, container, false);
        pb = v.findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        pdfurl = getArguments().getString("Link");
        pdfView = v.findViewById(R.id.idPDFView);
        new RetrivePDFfromUrl().execute(pdfurl);

        return v;
    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {

            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    pb.setVisibility(View.GONE);
                }
            }).load();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((Main2Activity) getActivity()).hideBottomNav();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((Main2Activity) getActivity()).showBottomNav();
    }
}
