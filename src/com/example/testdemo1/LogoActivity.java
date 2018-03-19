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
		/*setContentView(view);   通过自己创建的组件来显示内容  View
		 * View  表示视图
		 */
		setContentView(new LogoView(LogoActivity.this));//传参，调用LogoView的onDraw方法显示内容
	}
	/**页面跳转*/
	public void toMenu(){
		Intent  intent  =  new Intent(LogoActivity.this,MenuActivity.class);
		startActivity(intent);//启动跳转
		//结束
		finish();//跳转到MenuActivity界面后，就结束当前的LogoActivity进程
	}
}
