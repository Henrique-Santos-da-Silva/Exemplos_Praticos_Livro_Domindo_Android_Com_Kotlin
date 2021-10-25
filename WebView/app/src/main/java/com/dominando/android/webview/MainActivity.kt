package com.dominando.android.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        webView.loadUrl("https://www.noticiasautomotivas.com.br/")
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                view?.loadUrl(request?.url.toString())
//                return false
//            }
//        }

        val settings = webView.settings
        settings.javaScriptEnabled = true
        webView.addJavascriptInterface(this, "dominando")
        webView.loadUrl("file:///android_asset/app_page.html")
    }

    @JavascriptInterface
    fun showToast(s: String, t: String) {
        Toast.makeText(this, "Nome $s Idade: $t", Toast.LENGTH_SHORT).show()
    }


//
//    override fun onBackPressed() {
//        if (webView.canGoBack())
//            webView.goBack()
//        else
//            super.onBackPressed()
//    }
}