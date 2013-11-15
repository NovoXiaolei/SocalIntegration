package com.novo.sina;

import com.guyou.socalize.config.SinaConfig;
import com.novo.common.SocialInterface;
import com.novo.debug.util.DebugUtil;
import com.novo.socialintegration.R;
import com.sina.weibo.sdk.WeiboSDK;
import com.sina.weibo.sdk.api.BaseResponse;
import com.sina.weibo.sdk.api.IWeiboAPI;
import com.sina.weibo.sdk.api.IWeiboHandler;
import com.weibo.sdk.android.Weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SinaWeiboActivity extends Activity implements IWeiboHandler.Response, SocialInterface{	
	private String Tag = "SinaWeiboActivity";
	
	private Weibo m_Weibo;
	private IWeiboAPI m_iWeiboApi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_layout);
		if(initSDK()){
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	public void onResponse(BaseResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		m_Weibo = Weibo.getInstance(new SinaConfig().getAPP_KEY(), new SinaConfig().getREDIRECT_URL(), new SinaConfig().getSCOPE());
		if(null == m_Weibo)
		{
			DebugUtil.LogError(Tag, Tag+"m_weibo init failed");
			return false;
		}
		m_iWeiboApi = WeiboSDK.createWeiboAPI(this,
				new SinaConfig().getAPP_KEY());
		if(null == m_iWeiboApi)
		{
			DebugUtil.LogError(Tag, Tag+"m_iWeiboApi init failed");
			return false;
		}
		return true;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void share(String str, String strPicPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void share(String str, String strPicPath, String strURL) {
		// TODO Auto-generated method stub
		
	}

}
	