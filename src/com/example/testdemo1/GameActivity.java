package com.example.testdemo1;

import com.example.testdemo1.view.GameView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends MainActivity {

	public int  Level;
	public int  imgID;
	int position;
	
	Button  btn_title;
	ImageView iv_run;
	TextView  tv_time;
	GameView  gameView;
	
	//  是否开始
	public  boolean  isStart  =  true;
	
	//gemeView  中通过该信使发送消息到主线程更新界面
	public Handler handler = new Handler(){
		//接受信使发送过来的消息
		public void handleMessage(Message msg) {

			if(msg.what==0){
				if(isStart&&gameView.state==gameView.STATE_PLAY){
					tv_time.setText(gameView.getCurrentTime());
				}
			}
		};

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent   intent  =  getIntent();
		//获取从其他界面跳转到本界面的Intent  ，这里不能使用  new
		Level = intent.getIntExtra("level", 0);
		position = intent.getIntExtra("position", 0);
		//从图片数组中获取图片ID
		imgID  = imgs[position];
		setContentView(R.layout.activity_game);	
		
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_run = (ImageView) findViewById(R.id.iv_run);
		btn_title = (Button) findViewById(R.id.BT_title);	
		gameView  =(GameView) findViewById(R.id.gameView1);
		btn_title.setBackgroundResource(imgID);
		//背景音乐
		music.addMusic(R.raw.a3);
		
		/**接口*/ 
		iv_run.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(gameView.state==GameView.STATE_PLAY){
					if(isStart){

						iv_run.setImageResource(R.drawable.start);
						gameView.bufftime = gameView.getSocunds();

					}else{
						//重新开始之后  startTime  重新赋值为当前时间
						gameView.startTime  = System.currentTimeMillis();
						iv_run.setImageResource(R.drawable.up);
					}
					isStart  =! isStart;
				}
			}
		});
	}
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			/*menu.add(groupId, itemId, order, titleRes);
			 * 
			 * groupId   组id
			 * itemId    用来区分菜单上每个按钮  每个按钮都不一样
			 * order     排序  顺序
			 * title     内容
			 */
			menu.add(0, 0, 0, "返回游戏");
			menu.add(0, 1, 0, "游戏帮助");
			menu.add(0, 2, 0, "游戏设置");
			menu.add(0, 3, 0, "返回主菜单");

			return super.onCreateOptionsMenu(menu);
		}
		
		@Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			// TODO Auto-generated method stub

			switch (item.getItemId()) {
			case 0:

				break;
			case 1:
				Intent  intent  =  new  Intent(GameActivity.this,HelpActivity.class);
				isBack  =  true;
				if(isStart&&gameView.state==gameView.STATE_PLAY){
					isStart =  false;
					iv_run.setImageResource(R.drawable.start);//切换图片
					gameView.bufftime = gameView.getSocunds();//时间保存
				}
				startActivity(intent);
				break;
			case 2:
				Intent  intent1  =  new  Intent(GameActivity.this,SetActivity.class);
				isBack  =  true;
				if(isStart&&gameView.state==gameView.STATE_PLAY){
					isStart =  false;
					iv_run.setImageResource(R.drawable.start);//切换图片
					gameView.bufftime = gameView.getSocunds();//时间保存
				}
				startActivity(intent1);
				break;
			case 3:
				showGameClose();
				break;
			}
			return super.onMenuItemSelected(featureId, item);
		}

		/**游戏退出时，提示一个对话框*/
		public void showGameClose(){

			AlertDialog.Builder  builder  =  new AlertDialog.Builder(this);//对话框
			builder.setIcon(R.drawable.about).setTitle("是否退出？")
			.setPositiveButton("确认", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					isBack=  true;
					finish();
				}
			})
			.setNegativeButton("取消", null);
			builder.show();
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			//监听返回键按下
			//1.获取本次游戏分数
			//2.讲分数与排行榜做比较
			//3.如果能够进入排行榜，则进入信息输入界面，否则直接退出

			//gameView.bufftime  
			if(keyCode == KeyEvent.KEYCODE_BACK){

				if(gameView.isWIN(gameView.nums)){
					//暂定60分以内进入排行榜
					/*
					 * Intent  i  =  new(this,InputActivity.class);
					 * 
					 * i.putExtra("score",gameView.bufftime);
					 * 
					 * finish();
					 * 
					 * isBack  = true;       
					 * 
					 */
					//gameView.bufftime  是否能进入排行榜
					//  好于排行榜中对应的难度的最差成绩 或者 是最差成绩为0
					//最差成绩  即为对应难度的成绩的最后一名
					
//					loadScores();//加载成绩 
					rank.select(scores, topNames);
					
					int max = scores[Level-3][scores[Level-3].length-1];
					if(gameView.bufftime < max||max==0){
						Intent  intent  =  new Intent(GameActivity.this,InputActivity.class);
						//将成绩信息发送到输入界面
						intent.putExtra("score", gameView.bufftime);
						intent.putExtra("level", Level);
						startActivity(intent);
						finish();
						isBack = true;
					}else{
						finish();
						isBack = true;
					}
				}else{
					showGameClose();
				}
			}
			return super.onKeyDown(keyCode, event);
		}
}
