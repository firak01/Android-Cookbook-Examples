package com.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SampleWebview extends Activity {
	private MyWebViewClient objWebViewClient=new MyWebViewClient(); 
	private String sUrl=new String("http://google.com");
    private WebView webview;

    /*Getter Setter*/
    public String getUrl() {
		return sUrl;
	}

	public void setUrl(String sUrl) {
		this.sUrl = sUrl;
	}

    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
	    //FGL: 20161011 Hiermit wird die WebView andere Links nicht im StandardBrowser öffnen, 
	    //             sondern in der WebView selbst.
        //"http://192.168.3.104/db/fgl/Project/VideoArchive/via_Application.nsf?Opendatabase"
        //"http://192.168.3.104/db/fgl/VIA/via_Application.nsf?Opendatabase"
        setUrl("http://google.com");
        //setUrl("http://192.168.3.104");
        
        webview = (WebView)findViewById(R.id.webview);
        if(webview!=null){
        	if(webview.getSettings()!=null){
        		webview.getSettings().setJavaScriptEnabled(true);
        	}
	        if(objWebViewClient!=null){
	        	webview.setWebViewClient(this.objWebViewClient);
		    }else{
		    	
		    };
		    
	        //Das eigentliche Laden findet nun im WebViewClient statt.
	        webview.loadUrl(sUrl);
        }else{
        	        	
        };
    }
}