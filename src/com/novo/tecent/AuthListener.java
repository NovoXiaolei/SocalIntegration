package com.novo.tecent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.novo.debug.util.DebugUtil;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.component.Authorize;
import com.tencent.weibo.sdk.android.component.sso.OnAuthListener;
import com.tencent.weibo.sdk.android.component.sso.WeiboToken;

public class AuthListener implements OnAuthListener {
	private final String TAG = "AuthListener";
	
	private Activity baseActivity = null;
	
	public AuthListener(Activity baseActivity){
		this.baseActivity = baseActivity;
	}
	
	@Override
	public void onAuthFail(int result, String error) {
		// TODO Auto-generated method stub
		DebugUtil.TostShow(baseActivity, "onWeiboNotInstalled"+" "+"result: "+
					result+" "+"err: "+error);
	}

	@Override
	public void onAuthPassed(String name, WeiboToken token) {
		// TODO Auto-generated method stub
		Context context = baseActivity.getApplicationContext();
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
		DebugUtil.TostShow(baseActivity, "onWeiboNotInstalled");
		
		Intent intent = new Intent(baseActivity,Authorize.class);
		baseActivity.startActivity(intent);
	}

	@Override
	public void onWeiboVersionMisMatch() {
		// TODO Auto-generated method stub
		DebugUtil.TostShow(baseActivity, "onWeiboVersionMisMatch");
		
		Intent intent = new Intent(baseActivity,Authorize.class);
		baseActivity.startActivity(intent);
	}

}
