package com.novo.tecent;

import java.io.IOException;
import java.io.InputStream;

import com.novo.common.SocialInterface;
import com.novo.util.DebugUtil;
import com.novo.socialintegration.R;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TecentWeiboActivity extends Activity implements OnClickListener{

	private final String TAG = "TecentWeiboActivity";
	
	private Button m_authorizeButton;
	private Button m_shareTextButton;
	private Button m_shareTextAndImageButton;
	
	private TecentWeibo m_tencTecentWeibo;
	

	private void  init(){
		initButton();
	}
	
	private void initButton(){
		m_authorizeButton = (Button)findViewById(R.id.tecent_ssoauthorize);
		m_authorizeButton.setOnClickListener(this);
		
		m_shareTextButton = (Button)findViewById(R.id.tecent_text_share_button);
		m_shareTextButton.setOnClickListener(this);
		
		m_shareTextAndImageButton = (Button)findViewById(R.id.tecent_text_image_share_button);
		m_shareTextAndImageButton.setOnClickListener(this);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tecent_layout);
		
		init();
		m_tencTecentWeibo = new TecentWeibo(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tecent_ssoauthorize:{
			m_tencTecentWeibo.ssoAuthorize();
			break;
		}
		
		case R.id.tecent_text_share_button:{
			m_tencTecentWeibo.share("Tecent share text test!");
			break;
		}
		
		case R.id.tecent_text_image_share_button:{
			m_tencTecentWeibo.share("Tecent share text and image!", "images/logo.png");
			break;
		}

		default:
			break;
		}
	}


}
