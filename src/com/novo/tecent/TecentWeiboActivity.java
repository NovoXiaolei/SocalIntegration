package com.novo.tecent;

import com.novo.common.SocialInterface;
import com.novo.debug.util.DebugUtil;
import com.novo.socialintegration.R;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

import android.app.Activity;

import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.BaseTypes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TecentWeiboActivity extends Activity implements OnClickListener,SocialInterface  {

	private final String TAG = "TecentWeiboActivity";
	private final String FORMAT = "json";
	
	private Button m_authorizeButton;
	private Button m_shareTextButton;
	private Button m_shareTextAndImageButton;
	private WeiboAPI m_WeiboAPI;
	
	private Location m_Location;
	private double m_longitude = 0d;
	private double m_latitude = 0d;
	
	private HttpCallback mCallBack;
	private void  init(){
		initButton();
		
		m_Location = Util.getLocation(getApplicationContext());
		if(m_Location !=null){
			m_longitude = m_Location.getLongitude();
			m_latitude = m_Location.getLatitude();
		}
	}
	
	private boolean isWeiboApiAccess(){
		if(null!=m_WeiboAPI && false == m_WeiboAPI.isAuthorizeExpired(getApplicationContext()))
		{
			return true;
		}
		else {
			{
				return false;
			}
		}
	}
	
	private void initButton(){
		m_authorizeButton = (Button)findViewById(R.id.tecent_ssoauthorize);
		m_authorizeButton.setOnClickListener(this);
		
		m_shareTextButton = (Button)findViewById(R.id.tecent_text_share_button);
		m_shareTextButton.setOnClickListener(this);
		
		m_shareTextAndImageButton = (Button)findViewById(R.id.tecent_text_image_share_button);
		m_shareTextAndImageButton.setOnClickListener(this);
		
		mCallBack = new HttpCallback() {
			
			@Override
			public void onResult(Object object) {
				// TODO Auto-generated method stub
				ModelResult result = (ModelResult)object;
				if(null!=result)
				{
					DebugUtil.TostShow(getApplicationContext(), "发送成功");
				}
				else {
					{
						DebugUtil.TostShow(getApplicationContext(), "发送成功");						
					}
				}
			}
		};
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tecent_layout);
		
		init();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tecent_ssoauthorize:{
			long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
			String app_secret = Util.getConfig().getProperty("APP_KEY_SEC");
			AuthHelper.register(this.getApplicationContext(), appid, app_secret, new AuthListener(this));
			AuthHelper.auth(this, "");
			
			if(initSDK()){
				DebugUtil.LogError(TAG, "m_Weibo init successful");
			}
			break;
		}
		
		case R.id.tecent_text_share_button:{
			share("Tecent share text test!");
			break;
		}
		
		case R.id.tecent_text_image_share_button:{
			break;
		}

		default:
			break;
		}
	}

	@Override
	public boolean initSDK() {
		AccountModel model = new AccountModel();
		String accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
		model.setAccessToken(accessToken);
		m_WeiboAPI = new WeiboAPI(model);
		if(null != m_WeiboAPI)
		{
			return true;
		}
		return false;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		if(isWeiboApiAccess()){
			m_WeiboAPI.addWeibo(getApplicationContext(), str, FORMAT, m_longitude, m_latitude, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
		}
		else {
			DebugUtil.TostShow(getApplicationContext(), "请为程序授权");
		}
	}

	@Override
	public void share(String str, String strPicPathAndName) {
		// TODO Auto-generated method stub
		if(isWeiboApiAccess()){
			
		}
		else {
			DebugUtil.TostShow(getApplicationContext(), "请为程序授权");
		}
	}

	@Override
	public void share(String str, String strPicPath, String strURL) {
		// TODO Auto-generated method stub
		
	}


}
