package com.ajasuja.codepath.news.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ajasuja on 3/19/17.
 */

public class CustomBrowser extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
