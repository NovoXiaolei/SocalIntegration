package com.novo.sina;

import java.io.IOException;
import java.io.InputStream;

import com.guyou.socalize.config.SinaConfig;
import com.novo.common.SocialInterface;
import com.novo.debug.util.DebugUtil;
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
		IWeiboHandler.Response, SocialInterface, OnClickListener {
	private final String TAG = "SinaWeiboActivity";

	private WeiboAuth m_Weibo = null;
	// 与微博客户端交互则需要使用这个接口类
	private IWeiboShareAPI m_iWeiboApi = null;
	private SsoHandler m_ssoHandler = null;

	// Button
	private Button m_SSOAuthorize;
	private Button m_O2Authorize;
	private Button m_shareTextButton;
	private Button m_shareTextAndImageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sina_layout);
		if (initSDK()) {
			initButton();
		}
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
		m_iWeiboApi.handleWeiboResponse(intent, this);
	}

	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		m_Weibo = new WeiboAuth(this, new SinaConfig().getAPP_KEY(),
				new SinaConfig().getREDIRECT_URL(), new SinaConfig().getSCOPE());
		if (null == m_Weibo) {
			DebugUtil.LogError(TAG, TAG + "m_weibo init failed");
			return false;
		}
		m_iWeiboApi = WeiboShareSDK.createWeiboAPI(this,
				new SinaConfig().getAPP_KEY());
		if (null == m_iWeiboApi) {
			DebugUtil.LogError(TAG, TAG + "m_iWeiboApi init failed");
			return false;
		}
		if (!m_iWeiboApi.isWeiboAppInstalled()) {
			m_iWeiboApi
					.registerWeiboDownloadListener(new IWeiboDownloadListener() {

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							DebugUtil
									.TostShow(SinaWeiboActivity.this, "用户取消下载");
						}

					});
		}
		m_iWeiboApi.handleWeiboResponse(getIntent(), this);
		return true;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		TextObject textObject = new TextObject();
		textObject.text = str;
		// 三方到微博
		// 初始化微博的分享消息
		WeiboMessage weiboMessage = new WeiboMessage();
		// 放文本消息
		weiboMessage.mediaObject = textObject;
		// 初始化从三方到微博的消息请求
		SendMessageToWeiboRequest req = new SendMessageToWeiboRequest();
		req.transaction = String.valueOf(System.currentTimeMillis());// 用transaction唯一标识一个请求
		req.message = weiboMessage;
		// 发送请求消息到微博
		m_iWeiboApi.sendRequest(req);
	}

	@Override
	public void share(String str, String strPicPathAndName) {
		// TODO Auto-generated method stub
		TextObject textObject1 = new TextObject();
		textObject1.text =str;
		
		ImageObject imageObject1 = new ImageObject();
		InputStream inputStream = null;
		try {
			inputStream =getAssets().open(strPicPathAndName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BitmapDrawable drawable = new BitmapDrawable(inputStream);
		Bitmap image =drawable.getBitmap();
		imageObject1.setImageObject(image);
		WeiboMultiMessage multiMessage1 = new WeiboMultiMessage();
		multiMessage1.textObject = textObject1;
		multiMessage1.imageObject = imageObject1;
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = multiMessage1;
		if(m_iWeiboApi.getWeiboAppSupportAPI()>10351)
		{
			m_iWeiboApi.sendRequest(request);
		}
		else {
			DebugUtil.LogError(TAG, "sina api is low");
		}
		
	}

	@Override
	public void share(String str, String strPicPath, String strURL) {
		// TODO Auto-generated method stub

	}

	private boolean reqWeibo() {
		if (m_iWeiboApi.checkEnvironment(true)) {
			boolean isDone = m_iWeiboApi.registerApp();
			if (isDone) {
				DebugUtil.LogError(TAG, "********微博注册成功");
			}
			return isDone;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		case R.id.sina_ssoauthorize: {
			DebugUtil.LogDebug(TAG, "ssoauthorize");
			m_ssoHandler = new SsoHandler(this, m_Weibo);
			DebugUtil.LogError(TAG, getPackageName());
			m_ssoHandler.authorize(new AuthDialogListener(this));
			break;
		}
		case R.id.sina_o2authorize: {
			DebugUtil.LogDebug(TAG, "o2authorize");
			m_Weibo.anthorize(new AuthDialogListener(this));
			break;
		}
		case R.id.sina_text_share_button: {
			if (reqWeibo()) {
				share("sina refacotr test");
			}
			break;
		}
		
		case R.id.sina_text_image_share_button:{
			if(reqWeibo())
			{
				share("分享文字与图片测试","logo.png");
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
		if (m_ssoHandler != null) {
			m_ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResponse(BaseResponse response) {
		// TODO Auto-generated method stub
		switch (response.errCode) {
		case WBConstants.ErrorCode.ERR_OK: {
			DebugUtil.TostShow(this, "分享成功");
			break;
		}
		case WBConstants.ErrorCode.ERR_FAIL: {
			DebugUtil.TostShow(this, "分享失败");
			String str = "Error Code:" + response.errCode;
			Log.e(TAG, str);
			break;
		}
		case WBConstants.ErrorCode.ERR_CANCEL: {
			DebugUtil.TostShow(this, "您已取消分享");
			break;
		}
		default:
			break;
		}
	}

}
