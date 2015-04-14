package com.xuanhui;

import cn.buaa.myweixin.R;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends Activity{

	//ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		//pb = (ProgressBar) findViewById(R.id.pb);
		//pb.setMax(100);
		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);  
		webView.getSettings().setBuiltInZoomControls(true);
		//webView.setWebChromeClient(new WebViewClient() );
		webView.loadUrl("http://www.xuanhui.com.cn");
	}
//	private class WebViewClient extends WebChromeClient {
//		@Override
//		public void onProgressChanged(WebView view, int newProgress) {
//			pb.setProgress(newProgress);
//			if(newProgress==100){
//				pb.setVisibility(View.GONE);
//			}
//			super.onProgressChanged(view, newProgress);
//		}
//	}
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	Intent intent = new Intent(WebViewActivity.this, MainActivity.class);
    	WebViewActivity.this.startActivity(intent);
		return false;
    } 
}
