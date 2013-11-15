package com.novo.socalintegration;

import android.util.Log;
import android.widget.Toast;

import com.guyou.socalize.config.SinaConfig;
import com.sina.weibo.sdk.WeiboSDK;
import com.sina.weibo.sdk.api.IWeiboAPI;
import com.sina.weibo.sdk.api.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;

public class PlatformControl{

	private static final String TAG = "PlatformControl";

	private IWeiboAPI mSinaWeiboAPI;
	


	private MainActivity baseActivity;

	public static final int SINA_SHARE = 0;
	public static final int TECENT_SHARE = 1;

	public PlatformControl(MainActivity activity) {
		baseActivity = activity;
		initWeiboSDK();
	}
	
	public IWeiboAPI getWeiboAPI(){
		return mSinaWeiboAPI;
	}

	public void shareControl(int shareType) {
		switch (shareType) {
		case SINA_SHARE: {
			if (null == mSinaWeiboAPI) {
				Log.e(TAG, "init WeiboSDK failed");
				return;
			}

			if (reqWeibo()) {
				Log.e(TAG, "share with sina");
				reqTextMsg();
			}

			break;
		}
		case TECENT_SHARE: {
			Log.e(TAG, "share with weibo");
			Toast.makeText(baseActivity, "Share with tecent",
					Toast.LENGTH_SHORT).show();
			break;
		}
		default:
			break;
		}
	}

	private void initWeiboSDK() {
		mSinaWeiboAPI = WeiboSDK.createWeiboAPI(baseActivity,
				new SinaConfig().getAPP_KEY());
	}

	private boolean reqWeibo() {
		if (mSinaWeiboAPI != null) {
			boolean isDone = mSinaWeiboAPI.registerApp();
			if (isDone) {
				Log.e(TAG, "********微博注册成功");
			}
			return isDone;
		}
		return false;
	}
	
	/**
     * 三方到微博文本消息
     */
    private void reqTextMsg() {
        // 三方到微博
        // 初始化微博的分享消息
        WeiboMessage weiboMessage = new WeiboMessage();
        // 放文本消息
        weiboMessage.mediaObject = getTextObj();
        // 初始化从三方到微博的消息请求
        SendMessageToWeiboRequest req = new SendMessageToWeiboRequest();
        req.transaction = String.valueOf(System.currentTimeMillis());// 用transaction唯一标识一个请求
        req.message = weiboMessage;
        // 发送请求消息到微博
        mSinaWeiboAPI.sendRequest(baseActivity, req);
    }
    
    /**
     * 文本消息构造方法
     * 
     * @return
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text ="Sina Weibo Integration Test";
        return textObject;
    }

	
}