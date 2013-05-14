package com.novo.socalintegration;

import java.text.SimpleDateFormat;

import com.guyou.socalize.config.SinaConfig;
import com.sina.weibo.sdk.api.BaseResponse;
import com.sina.weibo.sdk.api.IWeiboHandler;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, IWeiboHandler.Response{
	
	private static final String TAG = "main_activity";

	public static Oauth2AccessToken accessToken;
	
	private Weibo mWeibo;
	private PlatformControl mPlatformControl;
	
	private Button sinaButton;
	private Button tecentButton;
	private Button sinaAuthorizeButton;
	
	private SsoHandler mSsoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlatformControl = new PlatformControl(this);
		mPlatformControl.getWeiboAPI().responseListener(getIntent(), this);
		mWeibo = Weibo.getInstance(new SinaConfig().getAPP_KEY(), new SinaConfig().getREDIRECT_URL(), new SinaConfig().getSCOPE());
		if(null == mPlatformControl){
			Log.e(TAG, "平台控制器生成未成功");
		}
		sinaButton = (Button)findViewById(R.id.sina_share_button);
		tecentButton = (Button)findViewById(R.id.tecent_share_button);
		sinaAuthorizeButton = (Button)findViewById(R.id.sina_authorize);
		
		sinaButton.setOnClickListener(this);
		tecentButton.setOnClickListener(this);
		tecentButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sina_share_button:{
			mPlatformControl.shareControl(0);
			break;
		}
		case R.id.tecent_share_button:{
			mPlatformControl.shareControl(1);
			break;
		}
		case R.id.sina_authorize:{
			mSsoHandler = new SsoHandler(MainActivity.this, mWeibo);
			mSsoHandler.authorize(new AuthDialogListener(), getPackageName());
			break;
		}
		default:
			break;
		}
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		if(null != mPlatformControl){
			mPlatformControl.getWeiboAPI().responseListener(getIntent(), this);
		}
	}

	@Override
	public void onResponse(BaseResponse baseResponse) {
		// TODO Auto-generated method stub
		switch(baseResponse.errCode){
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_OK:{
			toastShow("分享成功");
			break;
		}
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_FAIL:{
			toastShow("分享失败");
			break;
		}
		case com.sina.weibo.sdk.constant.Constants.ErrorCode.ERR_CANCEL:{
			toastShow("用户取消分享");
			break;
		}
		default:
			break;
		}
	}
	
	public void toastShow(String text){
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	class AuthDialogListener implements WeiboAuthListener{

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onComplete(Bundle values) {
			// TODO Auto-generated method stub
			String code = values.getString("code");
        	if(code != null){
	        	Toast.makeText(MainActivity.this, "认证code成功", Toast.LENGTH_SHORT).show();
	        	return;
        	}
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            MainActivity.accessToken = new Oauth2AccessToken(token, expires_in);
            if (MainActivity.accessToken.isSessionValid()) {
//                AccessTokenKeeper.keepAccessToken(MainActivity.this,
//                        accessToken);
                Toast.makeText(MainActivity.this, "认证成功", Toast.LENGTH_SHORT)
                        .show();
            }
		}

		@Override
		public void onError(WeiboDialogError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
