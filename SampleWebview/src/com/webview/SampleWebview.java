package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class SampleWebview extends Activity {
    private WebView webview;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl("http://google.com");
        
    }
}