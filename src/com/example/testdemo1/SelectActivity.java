package com.example.testdemo1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectActivity extends MainActivity implements OnItemSelectedListener,OnItemClickListener{

	TextView  tv_select;
	Gallery     gallery;
	MyGalleryAdapter   adapter;//方法记载图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);
		tv_select  =  (TextView) findViewById(R.id.tv_select);
		tv_select.setText("1/"+imgs.length);
		gallery  =  (Gallery) findViewById(R.id.gallery1);
		adapter  =  new MyGalleryAdapter(this);
		gallery.setAdapter(adapter);
		gallery.setOnItemSelectedListener(this);//选关接口
		gallery.setOnItemClickListener(this);//对话框接口
		music.addMusic(R.raw.flower_dance);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		tv_select.setText(position+1+"/"+imgs.length);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		showSelect(position);


	}
	//页面跳转
	public void gotoGame(int level,int position){
		isBack = true;
		Intent   intent   =  new Intent(SelectActivity.this,GameActivity.class);
		//在intent跳转完成时，携带参数，携带参数的形式类似于  map  key  value
		//目标界面通过传入的name   ，获取参数
		intent.putExtra("level", level);
		intent.putExtra("position", position);
		startActivity(intent);

	}

	//显示对话框,难度选择制作
	public void showSelect(final  int position){
		AlertDialog.Builder  builder = new  AlertDialog.Builder(this);//创建对话框
		builder.setTitle("请选择难度");
		android.content.DialogInterface.OnClickListener   listener  =  new  android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Toast.makeText(SelectActivity.this, position+1+" 简单", Toast.LENGTH_SHORT).show();
					gotoGame(3,position);
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					Toast.makeText(SelectActivity.this, position+1+" 普通", Toast.LENGTH_SHORT).show();
					gotoGame(4,position);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					Toast.makeText(SelectActivity.this, position+1+" 困难", Toast.LENGTH_SHORT).show();
					gotoGame(5,position);
					break;
				}
			}
		};
		builder.setPositiveButton("简单", listener).
		setNeutralButton("普通", listener).
		setNegativeButton("困难", listener);
		builder.show();
	}
}

class  MyGalleryAdapter  extends  BaseAdapter{
	//上下文对象
	SelectActivity  mContext;
	//  布局加载器
	LayoutInflater     inflater;

	public MyGalleryAdapter(SelectActivity mContext) {
		this.mContext = mContext;
		inflater  =  LayoutInflater.from(mContext);

	}

	@Override
	public int getCount() {
		return mContext.imgs.length;
	}

	@Override
	public Object getItem(int position) {
		return mContext.imgs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View  view  = null;
		if(convertView != null){
			view  =  convertView;
		}else{
			view   =   inflater.inflate(R.layout.item_gallery_select, null);
		}
		ImageView    imageView    =    (ImageView) view.findViewById(R.id.iv_select);
		imageView.setImageResource(mContext.imgs[position]);
		return view;
	}
}