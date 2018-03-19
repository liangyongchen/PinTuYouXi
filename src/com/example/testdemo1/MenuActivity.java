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
	/*1.���ذ�ť
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
		/**findViewById   ͨ�����id�������
		 * 1.�������
		 * 2.����������
		 * 3.���ü�����
		 */

		//1.�������
		iv_start = (ImageView) findViewById(R.id.iv_start);
		iv_score = (ImageView) findViewById(R.id.iv_score);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		iv_help = (ImageView) findViewById(R.id.iv_help);
		iv_about = (ImageView) findViewById(R.id.iv_about);
		iv_exit = (ImageView) findViewById(R.id.iv_exit);

		/**ѡ�����android.view.View �ĵ��������
		 *�������������õ�� 
		 */
		//2.����������,������ӿڴ���
		//		OnClickListener listener = new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//			}
		//		};
		//3.���õ��������         this==MenuActivity��this
		iv_start.setOnClickListener(this);
		iv_score.setOnClickListener(this);
		iv_set.setOnClickListener(this);
		iv_help.setOnClickListener(this);
		iv_about.setOnClickListener(this);
		iv_exit.setOnClickListener(this);
		
		//��ӱ�������
		music.addMusic(R.raw.flower_dance);
	}
	/**�˳��Ի���*/
	public void showExit(){

		//�����Ի��������
		android.content.DialogInterface.OnClickListener  listener  =  new android.content.DialogInterface.OnClickListener(){
			/*
			 * (non-Javadoc)
			 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
			 * DialogInterface   ָ���Ƕ�Ӧ�ĶԻ���
			 * which���������
			 *          public static final int BUTTON_POSITIVE = -1;
			 *          public static final int BUTTON_NEGATIVE = -2;
			 *          public static final int BUTTON_NEUTRAL = -3;
			 */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//�ж�which
				if(which==DialogInterface.BUTTON_NEGATIVE){//which==Negativeʱ�˳���Ϸ
					finish();//����
					isBack = true;
				}
			}
		};
		AlertDialog.Builder  builder  =   new  AlertDialog.Builder(this);
		builder.setIcon(R.drawable.about)
		.setTitle(R.string.exit_title)
		.setMessage(R.string.exit_message)
		/*Negative      ����
		 * Positive         ����
		 * Neutral         ����
		 */
		.setPositiveButton("����", listener)
		//.setNeutralButton("ȡ��", null)
		.setNegativeButton("�˳�", listener);
		builder.show();
		//�Ի��򲼾�
		//builder.setView(view);
	}

	/**��ʾ���ڶԻ���������Ϸ���ڶԻ���*/
	public void showAbout(){
		//�Ի���    ͨ��Builder�ڲ��ഴ���Ի���
		AlertDialog.Builder  builder  =  new  AlertDialog.Builder(this);
		//����ͼƬ�����öԻ����ͼƬ
		//builder.setIcon(R.drawable.about);
		/*CharSequence title   �ַ�����
		 *������Ϸ���ڵ������ĶԻ���  �� �ַ�����
		 */ 
		builder.setTitle("����");
		//setMessage(message)��message�ܴ��ַ����к�int����
		String  message=" ����Ϸ��Ȩ�������������������⣬�벦����ϵ�绰13522290244����Email��wangtd@feicuiedu.com��";
		builder.setMessage(message);
		/*���ð�ť
		 * builder.setNegativeButton(text, listener);
		 */
		builder.setNegativeButton("����", null);
		//������������ʾ�Ի���
		builder.show();	
	}
	/**ҳ����ת*/
	public void toHelp(){

		Intent  intent  =  new Intent(MenuActivity.this,HelpActivity.class);
		startActivity(intent);
		isBack = true;
	}
	/**ҳ����ת*/
	public void  toSet(){
		Intent  intent  =  new Intent(MenuActivity.this,SetActivity.class);
		startActivity(intent);
		isBack = true;
	}
	/**ҳ����ת*/
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

	//�������ķ���
	@Override
	public void onClick(View v) {
		String  message = null;
		//��ȡ����������id
		int id = v.getId();//View v ������
		switch (id) {
		case R.id.iv_start:
			message = "��Ϸ��ʼ";
			toSelect();
			break;
		case R.id.iv_score:
			message = "��������";
			toTop();
			break;
		case R.id.iv_set:
			message = "��Ϸ����";
			toSet();
			break;
		case R.id.iv_help:
			message = "��Ϸ����";
			toHelp();
			break;
		case R.id.iv_about:
			message = "��Ϸ����";
			showAbout();//������ʾ�Ի���ķ���
			break;
		case R.id.iv_exit:
			message = "��Ϸ�˳�";
			showExit();//�����˳��Ի���ķ���
			break;
		}
		Toast toast = Toast.makeText(MenuActivity.this, message, Toast.LENGTH_SHORT);
		toast.show();//��������������
		sound.play(R.raw.a2);
	}

}
