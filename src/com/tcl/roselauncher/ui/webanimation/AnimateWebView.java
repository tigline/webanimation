/**
 * 
 */
package com.tcl.roselauncher.ui.webanimation;

import android.content.Context;
import android.webkit.WebView;

/**
 * @Project WebAnimation	
 * @author houxb
 * @Date 2015-11-3
 */
public class AnimateWebView extends WebView {

	/**
	 * @param context
	 */
	public AnimateWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}
	
	public void LoadWebFile(String URL) {
		this.loadUrl(URL);
	}
	
}
