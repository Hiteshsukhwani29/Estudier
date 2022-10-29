package com.scriptech.vstudy.ui.fragments.webview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.scriptech.vstudy.R
import com.scriptech.vstudy.databinding.FragWebviewBinding

class webview: Fragment() {
    private var _binding: FragWebviewBinding? = null

    private val binding get() = _binding!!

    private var webview: WebView? = null

    private val args by navArgs<webviewArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragWebviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        webview = binding.wv
        webview?.settings?.loadsImagesAutomatically = true
        webview?.settings?.javaScriptEnabled = true
        webview?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview?.loadUrl(args.url)

        return root
    }
}