package com.novo.common;

public interface SocialInterface {
	//��ʼ��sdk
	public boolean initSDK();
	//�򵥵ķ�������
	public void share(String str);
	
	//�������ֺ�ͼƬ
	public void share(String str, String strPicPath);
	
	//�������֣�ͼƬ�����Ҹ���һ��url��ַ
	public void share(String str, String strPicPath, String strURL);
}
