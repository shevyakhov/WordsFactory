package com.example.wordsfactory.ui.navigation_fragments.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.BuildConfig
import com.example.wordsfactory.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoViewModel: VideoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoViewModel = ViewModelProvider(this)[VideoViewModel::class.java]
        initWebView()
        binding.backBtn.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }
        }
        binding.forwardBtn.setOnClickListener {
            if (binding.webView.canGoForward()) {
                binding.webView.goForward()
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.loadUrl(BuildConfig.VIDEO_URL)
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        binding.webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    val webView = v as WebView
                    when (keyCode) {
                        KeyEvent.KEYCODE_BACK -> if (webView.canGoBack()) {
                            webView.goBack()
                            return true
                        }
                    }
                }
                return false
            }
        })
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return if (request.url.toString().contains(BuildConfig.VIDEO_URL)) {
                    view.loadUrl(request.url.toString())
                    videoViewModel.saveHistory(request.url.toString())
                    false
                } else {
                    Toast.makeText(
                        context,
                        videoViewModel.forbiddenLink(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    true
                }

            }

            @Deprecated("Deprecated in Java", ReplaceWith("false"))
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.contains(BuildConfig.VIDEO_URL)) {
                    view.loadUrl(url)
                    videoViewModel.saveHistory(url)

                    false
                } else {
                    Toast.makeText(
                        context,
                        videoViewModel.forbiddenLink(),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.webView.loadUrl(videoViewModel.currentUrl())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}