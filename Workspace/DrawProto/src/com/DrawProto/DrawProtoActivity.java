package com.DrawProto;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class DrawProtoActivity extends Activity {
    PathView pv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	 super.onCreate(savedInstanceState);
         
    	 WebView wv = (WebView)findViewById(R.id.wv);
  
    	// wv.loadUrl("file://android_asset/floor1.png");
        wv.loadUrl("https://github.com/majesticblue02/MGHWayfinder/raw/master/Other%20Files/Images/floor1.jpg");
         
    }
}