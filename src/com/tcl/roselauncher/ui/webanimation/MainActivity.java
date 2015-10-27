package com.tcl.roselauncher.ui.webanimation;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	
	private WebView webaimView;
	private Button showBt ,ctrol_bt1;
	private Button hidBt , ctrol_bt2;
	
	ObjectAnimator animator;
    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" }) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        showBt = (Button) findViewById(R.id.show_bt);
        hidBt = (Button) findViewById(R.id.hid_bt);
        ctrol_bt1 = (Button) findViewById(R.id.ctrol_bt1);
        ctrol_bt2 = (Button) findViewById(R.id.ctrol_bt2);
        showBt.setOnClickListener(this);
        hidBt.setOnClickListener(this);
        ctrol_bt1.setOnClickListener(this);
        ctrol_bt2.setOnClickListener(this);
        
        webaimView = (WebView) findViewById(R.id.aimview);
        webaimView.getSettings().setDefaultTextEncodingName("utf-8") ;  
        webaimView.getSettings().setJavaScriptEnabled(true);
        webaimView.setBackgroundColor(0);
       // webaimView.getBackground().setAlpha(0);
       // webaimView.loadUrl("https://www.baidu.com/");
        webaimView.loadUrl("file:///android_asset/demo.html");
        webaimView.setWebViewClient(new AimWebViewClent());
        
        webaimView.addJavascriptInterface(new JsObject(),"login");
        
        
        
    }
    
    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
    	if ((KeyCode == KeyEvent.KEYCODE_BACK) && webaimView.canGoBack()) {
			webaimView.goBack();
			return true;
		}
		return false;
    	
    }
    
    
    
    private class AimWebViewClent extends WebViewClient {
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		view.loadUrl(url);
			return true;
    	}
    }

    public class JsObject {  
        @JavascriptInterface  
        //public String toString() { return "injectedObject"; } 
        public void startFunction() {
        	AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
    		ab.setTitle("提示");
    		ab.setMessage("通过js 调用了 java 中的方法");
    		ab.setPositiveButton("确定", new DialogInterface.OnClickListener()
    		{
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				dialog.dismiss();
    			}
    		});
    		ab.create().show();
        }
     }  
    

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		float curTranslationX;
		switch (v.getId()) {

		case R.id.show_bt:
			Log.d("MainActivity", "show_webView");
			curTranslationX = webaimView.getTranslationX();
			animator = ObjectAnimator.ofFloat(webaimView, "translationX", curTranslationX, 0f);  
			animator.setDuration(500);  
			animator.start(); 
			break;
		case R.id.hid_bt:
			curTranslationX = webaimView.getTranslationX();
			animator = ObjectAnimator.ofFloat(webaimView, "translationX", curTranslationX, 960f);  
			animator.setDuration(500);  
			animator.start();
			break;
		case R.id.ctrol_bt1:
			Log.d("MainActivity","ctrol_bt1 OnClick");
			webaimView.loadUrl("javascript:updateHtml()");
			
			break;
		case R.id.ctrol_bt2:
			Log.d("MainActivity","ctrol_bt2 OnClick");
			webaimView.loadUrl("file:///android_asset/HelloWorld.html");
			break;

		default:
			break;
		}
	}
} 
