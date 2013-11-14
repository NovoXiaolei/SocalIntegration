package com.novo.socalintegration;

import com.guyou.socalize.config.SinaConfig;
import com.novo.sina.AuthDialogListener;
import com.novo.socialintegration.R;
import com.sina.weibo.sdk.api.BaseResponse;
import com.sina.weibo.sdk.api.IWeiboHandler;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.sso.SsoHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, IWeiboHandler.Response{
	
	private static final String TAG = "main_activity";
	
	private Weibo mWeibo;
	private PlatformControl mPlatformControl;
	
	private Button sinaButton;
	private Button tecentButton;
	
	//sso auth button
	private Button sinaSSOAuthorizeButton;
	private Button sinaO2AuthorizeButton;
	
	private SsoHandler mSsoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPlatformControl = new PlatformControl(this);
		mPlatformControl.getWeiboAPI().responseListener(getIntent(), this);
		mWeibo = Weibo.getInstance(new SinaConfig().getAPP_KEY(), new SinaConfig().getREDIRECT_URL(), new SinaConfig().getSCOPE());
		if(null == mPlatformControl){
			toastShow("platformcontrol init failed");
		}
		else
		{
			if(null == mWeibo)
			{
				toastShow("weibo init failed");
			}
		}
		sinaButton = (Button)findViewById(R.id.sina_share_button);
		tecentButton = (Button)findViewById(R.id.tecent_share_button);
		sinaSSOAuthorizeButton = (Button)findViewById(R.id.sina_ssoauthorize);
		sinaO2AuthorizeButton = (Button)findViewById(R.id.sina_o2authorize);
		
		//bind the listener
		sinaButton.setOnClickListener(this);
		tecentButton.setOnClickListener(this);
		sinaSSOAuthorizeButton.setOnClickListener(this);
		sinaO2AuthorizeButton.setOnClickListener(this);
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
			mPlatformControl.shareControl(PlatformControl.SINA_SHARE);
			break;
		}
		case R.id.tecent_share_button:{
			mPlatformControl.shareControl(PlatformControl.TECENT_SHARE);
			break;
		}
		case R.id.sina_ssoauthorize:{
			Log.d(TAG, "sso authorize");
			mSsoHandler = new SsoHandler(MainActivity.this, mWeibo);
			mSsoHandler.authorize(new AuthDialogListener(this), this.getPackageName());
			break;
		}
		case R.id.sina_o2authorize:{
			Log.d(TAG, "oauth 2.0");
			mWeibo.anthorize(this, new AuthDialogListener(this));
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


}
