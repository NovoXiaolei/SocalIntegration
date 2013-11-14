package com.novo.sina;


import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.novo.socalintegration.MainActivity;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.util.AccessTokenKeeper;

public class AuthDialogListener implements WeiboAuthListener {
	private String TAG = "AuthDialogListener";
	
	private MainActivity m_MainActivity;
	private Oauth2AccessToken m_AccessToken;
	
	
	public AuthDialogListener(MainActivity activity)
	{
		m_MainActivity = activity;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		m_MainActivity.toastShow("Auth cancel");
	}

	@Override
	public void onComplete(Bundle values) {
		// TODO Auto-generated method stub
		String token = values.getString("access_token");
		String expires_in = values.getString("expires_in");
		m_AccessToken = new Oauth2AccessToken(token, expires_in);
		if(m_AccessToken.isSessionValid())
		{
//			String date = new SimpleDateFormat("yyyt/MM/dd HH:mm:ss")
//							.format(new java.util.Date(m_AccessToken.getExpiresTime()));
			AccessTokenKeeper.keepAccessToken(m_MainActivity, m_AccessToken);
			m_MainActivity.toastShow("auth success");
		}
	}

	@Override
	public void onError(WeiboDialogError error) {
		// TODO Auto-generated method stub
		m_MainActivity.toastShow("Auth error"+error.getMessage());
	}

	@Override
	public void onWeiboException(WeiboException exception) {
		// TODO Auto-generated method stub
		m_MainActivity.toastShow("Auth exception"+exception.getMessage());
	}

	public Oauth2AccessToken getAccessToken() {
		if(null!=m_AccessToken)
		{
			return m_AccessToken;
		}
		return null;
	}

	public void setAccessToken(Oauth2AccessToken accessToken) {
		this.m_AccessToken = accessToken;
	}

}
