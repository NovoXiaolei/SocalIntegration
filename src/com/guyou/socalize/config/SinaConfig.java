package com.guyou.socalize.config;

public class SinaConfig{
	private final String APP_KEY = "4083126278";
	
	//�滻Ϊ������REDIRECT_URL
	private final String REDIRECT_URL = "http://www.sina.com";
	    
	//��֧��scope ֧�ִ�����scopeȨ�ޣ��ö��ŷָ�
	private final String SCOPE = "email,direct_messages_read,direct_messages_write," +
			"friendships_groups_read,friendships_groups_write,statuses_to_me_read," +
				"follow_app_official_microblog";


	public String getAPP_KEY() {
		return APP_KEY;
	}


	public String getREDIRECT_URL() {
		return REDIRECT_URL;
	}


	public String getSCOPE() {
		return SCOPE;
	}
}