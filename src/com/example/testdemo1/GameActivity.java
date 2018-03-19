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
	
	//  �Ƿ�ʼ
	public  boolean  isStart  =  true;
	
	//gemeView  ��ͨ������ʹ������Ϣ�����̸߳��½���
	public Handler handler = new Handler(){
		//������ʹ���͹�������Ϣ
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
		//��ȡ������������ת���������Intent  �����ﲻ��ʹ��  new
		Level = intent.getIntExtra("level", 0);
		position = intent.getIntExtra("position", 0);
		//��ͼƬ�����л�ȡͼƬID
		imgID  = imgs[position];
		setContentView(R.layout.activity_game);	
		
		tv_time = (TextView) findViewById(R.id.tv_time);
		iv_run = (ImageView) findViewById(R.id.iv_run);
		btn_title = (Button) findViewById(R.id.BT_title);	
		gameView  =(GameView) findViewById(R.id.gameView1);
		btn_title.setBackgroundResource(imgID);
		//��������
		music.addMusic(R.raw.a3);
		
		/**�ӿ�*/ 
		iv_run.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(gameView.state==GameView.STATE_PLAY){
					if(isStart){

						iv_run.setImageResource(R.drawable.start);
						gameView.bufftime = gameView.getSocunds();

					}else{
						//���¿�ʼ֮��  startTime  ���¸�ֵΪ��ǰʱ��
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
			 * groupId   ��id
			 * itemId    �������ֲ˵���ÿ����ť  ÿ����ť����һ��
			 * order     ����  ˳��
			 * title     ����
			 */
			menu.add(0, 0, 0, "������Ϸ");
			menu.add(0, 1, 0, "��Ϸ����");
			menu.add(0, 2, 0, "��Ϸ����");
			menu.add(0, 3, 0, "�������˵�");

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
					iv_run.setImageResource(R.drawable.start);//�л�ͼƬ
					gameView.bufftime = gameView.getSocunds();//ʱ�䱣��
				}
				startActivity(intent);
				break;
			case 2:
				Intent  intent1  =  new  Intent(GameActivity.this,SetActivity.class);
				isBack  =  true;
				if(isStart&&gameView.state==gameView.STATE_PLAY){
					isStart =  false;
					iv_run.setImageResource(R.drawable.start);//�л�ͼƬ
					gameView.bufftime = gameView.getSocunds();//ʱ�䱣��
				}
				startActivity(intent1);
				break;
			case 3:
				showGameClose();
				break;
			}
			return super.onMenuItemSelected(featureId, item);
		}

		/**��Ϸ�˳�ʱ����ʾһ���Ի���*/
		public void showGameClose(){

			AlertDialog.Builder  builder  =  new AlertDialog.Builder(this);//�Ի���
			builder.setIcon(R.drawable.about).setTitle("�Ƿ��˳���")
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					isBack=  true;
					finish();
				}
			})
			.setNegativeButton("ȡ��", null);
			builder.show();
		}

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			//�������ؼ�����
			//1.��ȡ������Ϸ����
			//2.�����������а����Ƚ�
			//3.����ܹ��������а��������Ϣ������棬����ֱ���˳�

			//gameView.bufftime  
			if(keyCode == KeyEvent.KEYCODE_BACK){

				if(gameView.isWIN(gameView.nums)){
					//�ݶ�60�����ڽ������а�
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
					//gameView.bufftime  �Ƿ��ܽ������а�
					//  �������а��ж�Ӧ���Ѷȵ����ɼ� ���� �����ɼ�Ϊ0
					//���ɼ�  ��Ϊ��Ӧ�Ѷȵĳɼ������һ��
					
//					loadScores();//���سɼ� 
					rank.select(scores, topNames);
					
					int max = scores[Level-3][scores[Level-3].length-1];
					if(gameView.bufftime < max||max==0){
						Intent  intent  =  new Intent(GameActivity.this,InputActivity.class);
						//���ɼ���Ϣ���͵��������
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
