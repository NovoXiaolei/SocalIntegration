package com.novo.common;

public interface SocialInterface {
	//初始化sdk
	public boolean initSDK();
	//简单的分享文字
	public void share(String str);
	
	//分享文字和图片
	public void share(String str, String strPicPathAndName);
	
	//分享文字，图片，并且附加一个url地址
	public void share(String str, String strPicPath, String strURL);
}
