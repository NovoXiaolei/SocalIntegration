package com.novo.sina;


import java.text.SimpleDateFormat;

import com.novo.util.DebugUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;



public class AuthDialogListener implements WeiboAuthListener {
	private String TAG = "AuthDialogListener";
	
	private Oauth2AccessToken m_AccessToken;
	
	private Context m_Context;
	
	
	public AuthDialogListener(Context context)
	{
		this.m_Context = context;
	}


	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		DebugUtil.LogError(TAG, "onCancel");
	}


	@Override
	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		m_AccessToken = Oauth2AccessToken.parseAccessToken(values);
		if(m_AccessToken.isSessionValid())
		{
			AccessTokenKeeper.writeAccessToken(m_Context, m_AccessToken);
		}
	}


	@Override
	public void onWeiboException(WeiboException arg0) {
		// TODO Auto-generated method stub
		DebugUtil.LogError(TAG, "onWeiboException");
	}

	

}
