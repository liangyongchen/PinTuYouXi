package com.example.testdemo1;

import android.os.Bundle;
import android.app.Activity;

public class PauseActivity extends Activity {

	/*��ͣ����
	 * ������
	 * 1.�̳�Activity
	 * 2.��дonCreate
	 * 3.ע�ᣬ������׿�����֪������
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//���ظ�ҳ������ݻ��߶���        
		//view  ��ͼ      ���а�׿�ռ�� ����   
//		setContentView(R.layout.activity_main);
        //com.example.testdemo1  һ��ѡ���Լ���R�ļ�
		setContentView(R.layout.activity_pause);
	}

}
