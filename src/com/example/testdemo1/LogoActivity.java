package com.example.testdemo1;

import com.example.testdemo1.view.LogoView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_logo);
		/*setContentView(view);   ͨ���Լ��������������ʾ����  View
		 * View  ��ʾ��ͼ
		 */
		setContentView(new LogoView(LogoActivity.this));//���Σ�����LogoView��onDraw������ʾ����
	}
	/**ҳ����ת*/
	public void toMenu(){
		Intent  intent  =  new Intent(LogoActivity.this,MenuActivity.class);
		startActivity(intent);//������ת
		//����
		finish();//��ת��MenuActivity����󣬾ͽ�����ǰ��LogoActivity����
	}
}
