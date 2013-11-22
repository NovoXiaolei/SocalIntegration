package com.novo.socalintegration;

import com.guyou.socalize.config.PlatformConfig;
import com.novo.util.DebugUtil;

public class SocialFactory{

	private static final String TAG = "PlatformControl";
	private static SocialFactory m_Instance = null;
	
	public static SocialFactory getInstance()
	{
		if(null==m_Instance){
			m_Instance = new SocialFactory();
			return  m_Instance;
		}
		return m_Instance;
	}
	
	private SocialFactory(){
		
	}
	
	public Social getPlatform(int platformType)
	{
		Social social = null;
		switch (platformType) {
		case PlatformConfig.SINA_SHARE:
		{
			DebugUtil.LogDebug(TAG, "init sina weibo");
			break;
		}
		case PlatformConfig.TECENT_SHARE:{
			DebugUtil.LogDebug(TAG, "init tecent weibo");
			break;
		}
		default:
			break;
		}
		return social;
	}
}