package com.tcl.roselauncher.ui.webanimation;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private final String TAG = "MainActivity";
	private static int turn = 0;
	//private BridgeWebView webaimView;
	private WebView webaimView;
	private Button showBt ,ctrol_bt1, change_bt;
	private Button hidBt , ctrol_bt2;
	private int RESULT_CODE = 0;
	private TextView myTv;
	private SeekBar speed;
	ValueCallback<Uri> mUploadMessage;
	public Handler handler = new Handler(); 
	static class Location {
		String address;
	}
	static class User {
		String name;
		Location location;
		String testStr;
	}
	
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
        change_bt = (Button) findViewById(R.id.change_bt);
        myTv = (TextView) findViewById(R.id.tv);
        speed = (SeekBar) findViewById(R.id.speed_bt);
        showBt.setOnClickListener(this);
        hidBt.setOnClickListener(this);
        change_bt.setOnClickListener(this);
        ctrol_bt1.setOnClickListener(this);
        ctrol_bt2.setOnClickListener(this);
        speed.setProgress(5);
        speed.setOnSeekBarChangeListener(seekListener);
        
        webaimView = (WebView) findViewById(R.id.aimview);
        webaimView.getSettings().setDefaultTextEncodingName("utf-8");  
        webaimView.getSettings().setJavaScriptEnabled(true);
        webaimView.setBackgroundColor(0);
//        webaimView.setDefaultHandler(new DefaultHandler());
//        webaimView.setWebChromeClient(new WebChromeClient() {
//        	@SuppressWarnings("unused")
//			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
//				this.openFileChooser(uploadMsg);
//			}
//
//			@SuppressWarnings("unused")
//			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
//				this.openFileChooser(uploadMsg);
//			}
//
//			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//				mUploadMessage = uploadMsg;
//				pickFile();
//			}
//        });
       // webaimView.getBackground().setAlpha(0);
       // webaimView.loadUrl("https://www.baidu.com/");
        //webaimView.addJavascriptInterface(new MyObject(this,handler), "myObject");
        webaimView.setWebViewClient(new WebViewClient());
        webaimView.loadUrl("file:///android_asset/webanimate/indexjavajs.html");
        webaimView.addJavascriptInterface(this, "my");
        
        //webaimView.setWebViewClient(new AimWebViewClent());
        
        //webaimView.addJavascriptInterface(new JsObject(),"login");
        /*
        webaimView.registerHandler("submitFromWeb", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				// TODO Auto-generated method stub
				Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data from Java");
			}
        	
        });
        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "Bruce";
        webaimView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        webaimView.send("hello");
        */

    }
    
    private OnSeekBarChangeListener seekListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			//Log.d("MainActivity", "progress = " + progress);
			webaimView.loadUrl("javascript:setRotationSpeed('" + progress + "')");
			
		}
	};
    
    @JavascriptInterface
    public void startFunction() {
    	runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				myTv.setText(myTv.getText() + "\njs调用了java函数");
			}
    		
    	});
    }
    
    @JavascriptInterface
    public void startFunction(final String str) {
    	runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				myTv.setText(myTv.getText() + "\njs调用了java函数传递参数: " + str);
			}
    		
    	});
    }
    
    /**
	 * 
	 */
	protected void pickFile() {
		// TODO Auto-generated method stub
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
		chooserIntent.setType("image/*");
		startActivityForResult(chooserIntent, RESULT_CODE);
	}

	@Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
    	if ((KeyCode == KeyEvent.KEYCODE_BACK) && webaimView.canGoBack()) {
			webaimView.goBack();
			return true;
		}
		return false;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == RESULT_CODE) {
			if (null == mUploadMessage){
				return;
			}
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
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
			animator.setDuration(100);  
			animator.start(); 
			break;
		case R.id.hid_bt:
			curTranslationX = webaimView.getTranslationX();
			animator = ObjectAnimator.ofFloat(webaimView, "translationX", curTranslationX, 1680f);  
			animator.setDuration(100);  
			animator.start();
			break;
		case R.id.ctrol_bt1:
			webaimView.loadUrl("javascript:javacalljs()");
			webaimView.loadUrl("javascript:javacalljswithargs(" + " 'come from JS' " + ")");
//			Log.d("MainActivity","ctrol_bt1 OnClick");
//			webaimView.loadUrl("javascript:updateHtml()");	
//			webaimView.callHandler("functionInJs", "data from Java", new CallBackFunction() {
//
//				@Override
//				public void onCallBack(String data) {
//					// TODO Auto-generated method stub
//					Log.i(TAG, "reponse data from js " + data);
//				}
//
//			});
			break;
		case R.id.ctrol_bt2:
			Log.d("MainActivity","ctrol_bt2 OnClick");
			webaimView.loadUrl("file:///android_asset/webanimate/cartoon.html");
			break;
		case R.id.change_bt:
				if (turn > 3) {
					turn = 0;
				}
				switch (turn) {
				case 0:
					webaimView.loadUrl("file:///android_asset/webanimate/indexbutterfly.html");
					break;
				case 1:
					webaimView.loadUrl("file:///android_asset/webanimate/indexcircle.html");
					break;
				case 2:
					webaimView.loadUrl("file:///android_asset/webanimate/indexjavajs.html");
					break;
				case 3:
					webaimView.loadUrl("file:///android_asset/webanimate/indexlogo.html");
					break;
				default:
					break;
				}
				turn++;
			
		default:
			break;
		}
	}
} 
