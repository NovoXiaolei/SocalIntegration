package com.guyou.sina;

import android.os.Bundle;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;

public class AuthListener implements WeiboAuthListener {

	private Oauth2AccessToken accessToken;

	private static AuthListener mInstance;

	private AuthListener() {
	};

	public synchronized static AuthListener getInstance() {
		if (null == mInstance) {
			mInstance = new AuthListener();
		}
		return mInstance;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete(Bundle bundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(WeiboDialogError weiboDialogError) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWeiboException(WeiboException weiboException) {
		// TODO Auto-generated method stub

	}

	public Oauth2AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Oauth2AccessToken accessToken) {
		this.accessToken = accessToken;
	}

}