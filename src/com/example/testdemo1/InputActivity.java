package com.example.testdemo1;

import java.util.Arrays;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends MainActivity implements OnClickListener{

	EditText  edt_input;
	Button    btn_true;
	Button    btn_cancel;
	int score;
	int level;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);
		edt_input  =  (EditText) findViewById(R.id.edt_input);
		btn_true   =  (Button) findViewById(R.id.btn_true);
		btn_cancel   =  (Button) findViewById(R.id.btn_cancel);
		Intent intent = getIntent();
		score =(int) intent.getLongExtra("score", 0);
		level = intent.getIntExtra("level", 0);
		btn_cancel.setOnClickListener(this);
		btn_true.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//点击确定  名字输入内容  点击取消名字为  无名子
		case R.id.btn_true:
			//edt_input.getEditableText().toString();  获取输入文本框
			name=edt_input.getEditableText().toString();
			//对输入进行判断  如果输入内容为空  则继续输入
			if(name==null||name.length()==0){
				Toast.makeText(InputActivity.this, "输入内容为空", 0).show();
			}else{
				//保存
				save();//保存
			}
			break;
		case R.id.btn_cancel:
			name = "无名氏";
			//保存
			save();//保存
			break;
		default:
			break;
		}
	}
	public void save(){
		//  对成绩和输入姓名进行保存
		scores[level-3][scores[level-3].length-1] =score;
		topNames[level-3][topNames[level-3].length-1] =name;
		int nums[]=scores[level-3];
		String names[]=topNames[level-3];
		for (int i = nums.length;i > 0; i--) {
			System.out.println(Arrays.toString(nums));
			for (int j = 0; j < i-1; j++) {
				if(nums[j]==0||(nums[j+1]!=0&&nums[j]>nums[j+1])){
					int tmp = nums[j]; 
					nums[j] =nums[j+1];
					nums[j+1]=tmp;
					String tmp1 = names[j]; 
					names[j] =names[j+1];
					names[j+1]=tmp1;
				}
			}
		}

		rank.upDate(scores, topNames);
		isBack = true;
		finish();		
	}
}
