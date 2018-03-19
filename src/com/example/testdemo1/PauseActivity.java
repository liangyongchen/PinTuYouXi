package com.example.testdemo1;

import android.os.Bundle;
import android.app.Activity;

public class PauseActivity extends Activity {

	/*暂停界面
	 * 三步走
	 * 1.继承Activity
	 * 2.重写onCreate
	 * 3.注册，这样安卓程序才知道，在
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//加载该页面的内容或者对象        
		//view  视图      搜有安卓空间的 父类   
//		setContentView(R.layout.activity_main);
        //com.example.testdemo1  一定选择自己的R文件
		setContentView(R.layout.activity_pause);
	}

}
