package com.example.testdemo1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuActivity extends MainActivity implements OnClickListener{
	/*1.加载按钮
	 * 
	 * 
	 */
	ImageView iv_start;
	ImageView iv_score;
	ImageView iv_set;
	ImageView iv_help;
	ImageView iv_about;
	ImageView iv_exit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		/**findViewById   通过组件id加载组件
		 * 1.加载组件
		 * 2.创建监听器
		 * 3.设置监听器
		 */

		//1.加载组件
		iv_start = (ImageView) findViewById(R.id.iv_start);
		iv_score = (ImageView) findViewById(R.id.iv_score);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		iv_help = (ImageView) findViewById(R.id.iv_help);
		iv_about = (ImageView) findViewById(R.id.iv_about);
		iv_exit = (ImageView) findViewById(R.id.iv_exit);

		/**选择包是android.view.View 的点击监听器
		 *监听器用于设置点击 
		 */
		//2.创建监听器,在上面接口创建
		//		OnClickListener listener = new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//			}
		//		};
		//3.设置点击监听器         this==MenuActivity。this
		iv_start.setOnClickListener(this);
		iv_score.setOnClickListener(this);
		iv_set.setOnClickListener(this);
		iv_help.setOnClickListener(this);
		iv_about.setOnClickListener(this);
		iv_exit.setOnClickListener(this);
		
		//添加背景音乐
		music.addMusic(R.raw.flower_dance);
	}
	/**退出对话框*/
	public void showExit(){

		//创建对话框监听器
		android.content.DialogInterface.OnClickListener  listener  =  new android.content.DialogInterface.OnClickListener(){
			/*
			 * (non-Javadoc)
			 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
			 * DialogInterface   指的是对应的对话框
			 * which的三大变量
			 *          public static final int BUTTON_POSITIVE = -1;
			 *          public static final int BUTTON_NEGATIVE = -2;
			 *          public static final int BUTTON_NEUTRAL = -3;
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//判断which
				if(which==DialogInterface.BUTTON_NEGATIVE){//which==Negative时退出游戏
					finish();//结束
					isBack = true;
				}
			}
		};
		AlertDialog.Builder  builder  =   new  AlertDialog.Builder(this);
		builder.setIcon(R.drawable.about)
		.setTitle(R.string.exit_title)
		.setMessage(R.string.exit_message)
		/*Negative      负极
		 * Positive         正极
		 * Neutral         中立
		 */
		.setPositiveButton("返回", listener)
		//.setNeutralButton("取消", null)
		.setNegativeButton("退出", listener);
		builder.show();
		//对话框布局
		//builder.setView(view);
	}

	/**显示关于对话框，设置游戏关于对话框*/
	public void showAbout(){
		//对话框    通过Builder内部类创建对话框
		AlertDialog.Builder  builder  =  new  AlertDialog.Builder(this);
		//设置图片，设置对话框的图片
		//builder.setIcon(R.drawable.about);
		/*CharSequence title   字符序列
		 *设置游戏关于弹出来的对话框  的 字符序列
		 */ 
		builder.setTitle("关于");
		//setMessage(message)的message能传字符序列和int序列
		String  message=" 本游戏版权属于翡翠教育！若有问题，请拨打联系电话13522290244或发送Email至wangtd@feicuiedu.com！";
		builder.setMessage(message);
		/*设置按钮
		 * builder.setNegativeButton(text, listener);
		 */
		builder.setNegativeButton("返回", null);
		//方法启动，显示对话框
		builder.show();	
	}
	/**页面跳转*/
	public void toHelp(){

		Intent  intent  =  new Intent(MenuActivity.this,HelpActivity.class);
		startActivity(intent);
		isBack = true;
	}
	/**页面跳转*/
	public void  toSet(){
		Intent  intent  =  new Intent(MenuActivity.this,SetActivity.class);
		startActivity(intent);
		isBack = true;
	}
	/**页面跳转*/
	public void  toSelect(){
		Intent  intent  =  new Intent(MenuActivity.this,SelectActivity.class);
		startActivity(intent);
		isBack = true;
	}
	public void toTop(){
		Intent   intent  =  new  Intent(MenuActivity.this,TopActivity.class);
		startActivity(intent);
		isBack  =  true;
	}

	//监听器的方法
	@Override
	public void onClick(View v) {
		String  message = null;
		//获取被点击组件的id
		int id = v.getId();//View v 的作用
		switch (id) {
		case R.id.iv_start:
			message = "游戏开始";
			toSelect();
			break;
		case R.id.iv_score:
			message = "积分排名";
			toTop();
			break;
		case R.id.iv_set:
			message = "游戏设置";
			toSet();
			break;
		case R.id.iv_help:
			message = "游戏帮助";
			toHelp();
			break;
		case R.id.iv_about:
			message = "游戏关于";
			showAbout();//调用显示对话框的方法
			break;
		case R.id.iv_exit:
			message = "游戏退出";
			showExit();//调用退出对话框的方法
			break;
		}
		Toast toast = Toast.makeText(MenuActivity.this, message, Toast.LENGTH_SHORT);
		toast.show();//监听器方法调用
		sound.play(R.raw.a2);
	}

}
