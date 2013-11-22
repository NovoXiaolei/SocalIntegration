package com.novo.tecent;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;

import com.novo.common.SocialInterface;
import com.novo.util.DebugUtil;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.AuthHelper;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.BaseVO;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;

public class TecentWeibo implements SocialInterface {
	

	private Context m_Context;
	private WeiboAPI m_WeiboAPI;
	private final String FORMAT = "json";
	
	private Location m_Location;
	private double m_longitude = 0d;
	private double m_latitude = 0d;
	
	private HttpCallback mCallBack;
	
	public TecentWeibo(Context context){
		this.m_Context = context;
		
		m_Location = Util.getLocation(context);
		if(m_Location !=null){
			m_longitude = m_Location.getLongitude();
			m_latitude = m_Location.getLatitude();
		}
		
		mCallBack = new HttpCallback() {
			
			@Override
			public void onResult(Object object) {
				// TODO Auto-generated method stub
				ModelResult result = (ModelResult)object;
				if(null!=result)
				{
					DebugUtil.ToastShow(m_Context, "发送成功");
				}
				else {
					{
						DebugUtil.ToastShow(m_Context, "发送成功");						
					}
				}
			}
		};
		
		if(initSDK()){
			DebugUtil.ToastShow(m_Context, "腾讯微博初始化成功");
		}
	}
	
	private boolean isWeiboApiAccess(){
		if(null!=m_WeiboAPI && false == m_WeiboAPI.isAuthorizeExpired(m_Context))
		{
			return true;
		}
		else {
				DebugUtil.ToastShow(m_Context, "请给程序授权");
				return false;
		}
	}
	
	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		AccountModel model = new AccountModel();
		String accessToken = Util.getSharePersistent(m_Context, "ACCESS_TOKEN");
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
			m_WeiboAPI.addWeibo(m_Context, str, FORMAT, m_longitude, m_latitude, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
		}
	}

	@Override
	public void share(String str, String strPicPathAndName) {
		// TODO Auto-generated method stub
		if(isWeiboApiAccess()){
			InputStream iStream = null;
			try {
				iStream = m_Context.getAssets().open(strPicPathAndName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap bm = new BitmapDrawable(iStream).getBitmap();
			m_WeiboAPI.addPic(m_Context, str, FORMAT, m_longitude, m_latitude, bm, 0, 0, mCallBack, null, BaseVO.TYPE_JSON);
		}
	}

	@Override
	public void share(String str, String strPicPath, String strURL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ssoAuthorize() {
		// TODO Auto-generated method stub
		long appid = Long.valueOf(Util.getConfig().getProperty("APP_KEY"));
		String app_secret = Util.getConfig().getProperty("APP_KEY_SEC");
		AuthHelper.register(m_Context, appid, app_secret, new OnAuthListener() {
			
			@Override
			public void onAuthFail(int result, String error) {
				// TODO Auto-generated method stub
				DebugUtil.ToastShow(m_Context, "onWeiboNotInstalled"+" "+"result: "+
							result+" "+"err: "+error);
			}

			@Override
			public void onAuthPassed(String name, WeiboToken token) {
				// TODO Auto-generated method stub
				Context context =m_Context;
				Util.saveSharePersistent(context, "ACCESS_TOKEN", token.accessToken);
				Util.saveSharePersistent(context, "EXPIRES_IN", String.valueOf(token.expiresIn));
				Util.saveSharePersistent(context, "OPEN_ID", token.openID);
				Util.saveSharePersistent(context, "REFRESH_TOKEN", "");
				Util.saveSharePersistent(context, "CLIENT_ID", Util.getConfig().getProperty("APP_KEY"));
				Util.saveSharePersistent(context, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
			}

			@Override
			public void onWeiBoNotInstalled() {
				// TODO Auto-generated method stub
				DebugUtil.ToastShow(m_Context, "onWeiboNotInstalled");
				
				Intent intent = new Intent(m_Context,Authorize.class);
				m_Context.startActivity(intent);
			}

			@Override
			public void onWeiboVersionMisMatch() {
				// TODO Auto-generated method stub
				DebugUtil.ToastShow(m_Context, "onWeiboVersionMisMatch");
				
				Intent intent = new Intent(m_Context,Authorize.class);
				m_Context.startActivity(intent);
			}
		});
		AuthHelper.auth(m_Context, "");
	}

}
