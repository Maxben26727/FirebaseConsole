package com.max.firebaseconsole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.max.firebaseconsole.databinding.ActivityMainBinding;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity  implements AdvancedWebView.Listener {
    private ActivityMainBinding mainBinding;
    private  static final String url = "https://console.firebase.google.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainBinding.webview.setListener(this, this);
        mainBinding.webview.loadUrl(url);
        mainBinding.webview.setCookiesEnabled(true);
        WebChromeClient chromeClient = new WebChromeClient();
        mainBinding.webview.setWebChromeClient(chromeClient);
        mainBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainBinding.webview.reload();
            }
        });
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        final LinearLayout error=findViewById(R.id.errorlayout);
        Button refresh_btn=findViewById(R.id.refreshbtn);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected())
                {

                    error.setVisibility(View.GONE);
                    mainBinding.webview.reload();
                    mainBinding.webview.setVisibility(View.VISIBLE);
                }

            }
        });
        if(!isNetworkConnected())
        {
            mainBinding.webview.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        }
        else
        {
            mainBinding.webview.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        }



        mainBinding.webview.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        if (mainBinding.webview.canGoBack())
            mainBinding.webview.goBack();
        else
        super.onBackPressed();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
