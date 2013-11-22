package com.novo.sina;

import java.io.IOException;
import java.io.InputStream;

import com.guyou.socalize.config.SinaConfig;
import com.novo.common.SocialInterface;
import com.novo.util.DebugUtil;
import com.novo.socialintegration.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SinaWeiboActivity extends Activity implements
		OnClickListener {
	private final String TAG = "SinaWeiboActivity";

	// Button
	private Button m_SSOAuthorize;
	private Button m_O2Authorize;
	private Button m_shareTextButton;
	private Button m_shareTextAndImageButton;
	
	private SinaWeiboSocial m_SinaWeiboSocial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_layout);
		m_SinaWeiboSocial = new SinaWeiboSocial(this);
		initButton();
	}

	private void initButton() {
		m_SSOAuthorize = (Button) findViewById(R.id.sina_ssoauthorize);
		m_O2Authorize = (Button) findViewById(R.id.sina_o2authorize);
		m_shareTextButton = (Button) findViewById(R.id.sina_text_share_button);
		m_shareTextAndImageButton= (Button)findViewById(R.id.sina_text_image_share_button);

		if (null != m_SSOAuthorize) {
			m_SSOAuthorize.setOnClickListener(this);

		}
		if (null != m_O2Authorize) {
			m_O2Authorize.setOnClickListener(this);
		}
		if (null != m_shareTextButton) {
			m_shareTextButton.setOnClickListener(this);
		}
		if(null!=m_shareTextAndImageButton)
		{
			m_shareTextAndImageButton.setOnClickListener(this);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		m_SinaWeiboSocial.handleWeiboResponseIntent(intent);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		case R.id.sina_ssoauthorize: {
			m_SinaWeiboSocial.ssoAuthorize();
			break;
		}
		case R.id.sina_o2authorize: {
			DebugUtil.LogDebug(TAG, "o2authorize");
			m_SinaWeiboSocial.o2Authorize();
			break;
		}
		case R.id.sina_text_share_button: {
			if (m_SinaWeiboSocial.reqWeibo()) {
				m_SinaWeiboSocial.share("sina refacotr test");
			}
			break;
		}
		
		case R.id.sina_text_image_share_button:{
			if(m_SinaWeiboSocial.reqWeibo())
			{
				m_SinaWeiboSocial.share("∑÷œÌŒƒ◊÷”ÎÕº∆¨≤‚ ‘","logo.png");
			}
			break;
		}
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		m_SinaWeiboSocial.ssoAuthorizeCallBack(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	
}
