package com.novo.sina;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import com.guyou.socalize.config.SinaConfig;
import com.novo.common.SocialInterface;
import com.novo.socalintegration.Social;
import com.novo.util.DebugUtil;
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
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

public class SinaWeiboSocial extends Social implements SocialInterface, IWeiboHandler.Response{

	private final String TAG = "SinaWeiboSocial";
	
	private WeiboAuth m_Weibo = null;
	// ��΢���ͻ��˽�������Ҫʹ������ӿ���
	private IWeiboShareAPI m_iWeiboApi = null;
	private SsoHandler m_ssoHandler = null;
	
	private Context m_Context;
	
	public SinaWeiboSocial(Context context){
		this.m_Context = context;
		if(initSDK())
		{
			DebugUtil.ToastShow(context, "����΢����ʼ���ɹ�");
		}
	}
	
	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		m_Weibo = new WeiboAuth(m_Context, new SinaConfig().getAPP_KEY(),
				new SinaConfig().getREDIRECT_URL(), new SinaConfig().getSCOPE());
		if (null == m_Weibo) {
			DebugUtil.LogError(TAG, TAG + "m_weibo init failed");
			return false;
		}
		m_iWeiboApi = WeiboShareSDK.createWeiboAPI(m_Context,
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
									.ToastShow(m_Context, "�û�ȡ������");
						}

					});
		}
		Activity baseActivity = (Activity)m_Context;
		m_iWeiboApi.handleWeiboResponse(baseActivity.getIntent(),this);
		return true;
	}
	
	public void ssoAuthorize(){
		Activity baseActivity = (Activity)m_Context;
		m_ssoHandler = new SsoHandler(baseActivity, m_Weibo);
		m_ssoHandler.authorize(new WeiboAuthListener() {
			
			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
				DebugUtil.LogError(TAG, "onWeiboException");
			}
			
			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
				if(accessToken.isSessionValid())
				{
					AccessTokenKeeper.writeAccessToken(m_Context, accessToken);
				}
				DebugUtil.ToastShow(m_Context, "��Ȩ�ɹ�");
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				DebugUtil.LogError(TAG, "onCancel");
			}
		});
	}
	
	public void o2Authorize(){
		m_Weibo.anthorize(new WeiboAuthListener() {
			
			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
				DebugUtil.LogError(TAG, "onWeiboException");
			}
			
			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
				if(accessToken.isSessionValid())
				{
					AccessTokenKeeper.writeAccessToken(m_Context, accessToken);
				}
				DebugUtil.ToastShow(m_Context, "��Ȩ�ɹ�");
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				DebugUtil.LogError(TAG, "onCancel");
			}
		});
	}
	
	public boolean reqWeibo() {
		if (m_iWeiboApi.checkEnvironment(true)) {
			boolean isDone = m_iWeiboApi.registerApp();
			if (isDone) {
				DebugUtil.LogError(TAG, "********΢��ע��ɹ�");
			}
			return isDone;
		}
		return false;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		TextObject textObject = new TextObject();
		textObject.text = str;
		// ������΢��
		// ��ʼ��΢���ķ�����Ϣ
		WeiboMessage weiboMessage = new WeiboMessage();
		// ���ı���Ϣ
		weiboMessage.mediaObject = textObject;
		// ��ʼ����������΢������Ϣ����
		SendMessageToWeiboRequest req = new SendMessageToWeiboRequest();
		req.transaction = String.valueOf(System.currentTimeMillis());// ��transactionΨһ��ʶһ������
		req.message = weiboMessage;
		// ����������Ϣ��΢��
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
			inputStream =m_Context.getAssets().open(strPicPathAndName);
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

	@Override
	public void onResponse(BaseResponse response) {
		// TODO Auto-generated method stub
		switch (response.errCode) {
		case WBConstants.ErrorCode.ERR_OK: {
			DebugUtil.LogDebug(TAG, "����ɹ�");
			DebugUtil.ToastShow(m_Context, "���ͳɹ�");
			break;
		}
		case WBConstants.ErrorCode.ERR_FAIL: {
			DebugUtil.LogDebug(TAG, "����ʧ��");
			DebugUtil.ToastShow(m_Context, "����ʧ��");
			break;
		}
		case WBConstants.ErrorCode.ERR_CANCEL: {
			DebugUtil.LogDebug(TAG, "ȡ������");
			DebugUtil.ToastShow(m_Context, "ȡ������");
			break;
		}
		default:
			break;
		}
	}
	
	public void handleWeiboResponseIntent(Intent intent)
	{
		m_iWeiboApi.handleWeiboResponse(intent, this);
	}
	
	public void ssoAuthorizeCallBack(int requestCode, int resultCode, Intent data)
	{
		if(m_ssoHandler!=null){
			m_ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}
