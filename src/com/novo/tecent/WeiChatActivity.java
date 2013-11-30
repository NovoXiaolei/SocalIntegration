package com.novo.tecent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.guyou.socalize.config.WeiChatConfig;
import com.novo.common.SocialInterface;
import com.novo.socialintegration.R;
import com.novo.util.DebugUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class WeiChatActivity extends Activity implements OnClickListener, SocialInterface {
	private final String TAG = "WeiChatActivity";
	
	private IWXAPI m_WXApi;
	
	private Button m_WeiXinRegist;
	private Button m_shareTextButton;
	private Button m_shareTextAndImageButton;
	
	private void init(){
		m_WeiXinRegist = (Button)findViewById(R.id.weixin_regist);
		m_WeiXinRegist.setOnClickListener(this);
		
		m_shareTextButton = (Button)findViewById(R.id.weixin_text_share_button);
		m_shareTextButton.setOnClickListener(this);
		
		m_shareTextAndImageButton = (Button)findViewById(R.id.weixin_text_image_share_button);
		m_shareTextAndImageButton.setOnClickListener(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weixin_layout);
		//init button;
		init();
		if(initSDK()){
			DebugUtil.LogError(TAG, "Regist wei chat successfully");
		}
		else
		{
			DebugUtil.LogError(TAG, "Regist wei chat failed");
		}
	}

	@Override
	public boolean initSDK() {
		// TODO Auto-generated method stub
		String strAppId = new WeiChatConfig().getAppID();
		m_WXApi = WXAPIFactory.createWXAPI(this, strAppId);
		
		if(null!=m_WXApi)
		{
			
			return true;
		}
		return false;
	}

	@Override
	public void share(String str) {
		// TODO Auto-generated method stub
		m_WXApi.registerApp(new WeiChatConfig().getAppID());
		String text = str;
		if (text == null || text.length() == 0) {
			return;
		}
		
		// 初始化一个WXTextObject对象
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ignored";
		msg.description = text;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		
		// 调用api接口发送数据到微信
		if(m_WXApi.sendReq(req))
		{
			DebugUtil.LogError(TAG, "wei chat req send succefully");
		}
		//finish();
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
		switch(v.getId()){
		case R.id.weixin_regist:{
			break;
		}
		case R.id.weixin_text_share_button:{
			share("这只是测试 不要replay");
			break;
		}
		case R.id.weixin_text_image_share_button:{
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void ssoAuthorize() {
		// TODO Auto-generated method stub
		
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
