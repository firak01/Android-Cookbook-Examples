package com.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**Mit dem eigenene WebViewClient sicherstellen:
 * - dass auch Links innerhalb der WebView geöffnet werden und nicht im Standardbrowser.
 * @author Fritz Lindhauer
 *
 */
public class MyWebViewClient extends WebViewClient{

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String sUrl) {
                view.loadUrl(sUrl);
                return true;
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
}
