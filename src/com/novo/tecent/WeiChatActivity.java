package com.novo.tecent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.guyou.socalize.config.WeiChatConfig;
import com.novo.common.SocialInterface;
import com.novo.socialintegration.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

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
		switch(v.getId()){
		case R.id.weixin_regist:{
			break;
		}
		case R.id.weixin_text_share_button:{
			break;
		}
		case R.id.weixin_text_image_share_button:{
			break;
		}
		default:
			break;
		}
	}

}
