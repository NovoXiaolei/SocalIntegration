package com.novo.tecent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.guyou.socalize.config.WeiChatConfig;
import com.novo.common.SocialInterface;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeiChatActivity extends Activity implements OnClickListener, SocialInterface {
	private final String TAG = "WeiChatActivity";
	
	private IWXAPI m_WXApi;
	
	private void init(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//init button;
		init();
	}

	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		String strAppId = new WeiChatConfig().getAppID();
		m_WXApi = WXAPIFactory.createWXAPI(this, strAppId, true);
		m_WXApi.registerApp(strAppId);
		
		if(null!=m_WXApi)
		{
			return true;
		}
		return false;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void share(String str, String strPicPathAndName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void share(String str, String strPicPath, String strURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
