package com.novo.common;

public interface SocialInterface {
	//��ʼ��sdk
	public boolean initSDK();
	
	public void ssoAuthorize();
	//�򵥵ķ�������
	public void share(String str);
	
	//�������ֺ�ͼƬ
	public void share(String str, String strPicPathAndName);
	
	//�������֣�ͼƬ�����Ҹ���һ��url��ַ
	public void share(String str, String strPicPath, String strURL);
}
