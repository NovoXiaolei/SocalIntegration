package com.novo.socalintegration;

import android.app.Activity;
import android.util.Log;

import com.guyou.sina.SinaConfig;
import com.sina.weibo.sdk.WeiboSDK;
import com.sina.weibo.sdk.api.IWeiboAPI;

public class PlatformControl {
	
	private static final String TAG = "PlatformControl";
	
	private IWeiboAPI mSinaWeiboAPI;

	private Activity baseActivity;

	public PlatformControl(Activity activity) {
		baseActivity = activity;
	}

	private void initWeiboSDK() {
		mSinaWeiboAPI = WeiboSDK.createWeiboAPI(baseActivity,
				new SinaConfig().getAPP_KEY());
	}

	private void reqWeibo() {
		if (mSinaWeiboAPI != null) {
			boolean isDone = mSinaWeiboAPI.registerApp();
			if(isDone){
				Log.e(TAG, "********Î¢²©×¢²á³É¹¦");
			}
		}
	}	
}