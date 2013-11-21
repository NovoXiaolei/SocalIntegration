package com.novo.socalintegration;

import com.novo.util.DebugUtil;
import com.novo.sina.SinaWeiboActivity;
import com.novo.socialintegration.R;
import com.novo.tecent.TecentWeiboActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "main_activity";
	
	private Button sinaButton;
	private Button tecentButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DebugUtil.isDebugOpen = true;
		sinaButton = (Button)findViewById(R.id.sina);
		tecentButton = (Button)findViewById(R.id.tecent);
		//bind the listener
		sinaButton.setOnClickListener(this);
		tecentButton.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.sina:
		{
			Intent intent = new Intent(this, SinaWeiboActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.tecent:
		{
			Intent intent = new Intent(this, TecentWeiboActivity.class);
			startActivity(intent);
			break;
		}
		default:
			break;
		}
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
	}

	
	public void toastShow(String text){
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

}
