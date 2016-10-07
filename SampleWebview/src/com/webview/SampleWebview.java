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
        //webview.loadUrl("http://google.com");
        webview.loadUrl("http://192.168.3.104");
        //webview.loadUrl("http://192.168.3.104/db/fgl/Project/VideoArchive/via_Application.nsf?Opendatabase");
        //http://192.168.3.104/db/fgl/VIA/via_Application.nsf?Opendatabase
        
    }
}