package com.scriptech.vstudy.ui.fragments.pdf

import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.github.barteksc.pdfviewer.PDFView
import com.scriptech.vstudy.databinding.FragPdfBinding
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class Pdf: Fragment() {
    private var _binding: FragPdfBinding? = null

    private val binding get() = _binding!!

    private val args by navArgs<PdfArgs>()

    private val pdfurl = args.link

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragPdfBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.parseColor("#20111111")
                requireActivity().window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }
        RetrievePDFFromURL(binding.idPDFView).execute(pdfurl)
        return root
    }

    class RetrievePDFFromURL(private val pdfView: PDFView) :
        AsyncTask<String, Void, InputStream>() {

        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(params[0])
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return inputStream
        }
        override fun onPostExecute(result: InputStream?) {
            pdfView.fromStream(result).load()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Main2Activity?)!!.hideBottomNav()
    }
    override fun onStop() {
        super.onStop()
        (activity as Main2Activity?)!!.showBottomNav()
    }
}

