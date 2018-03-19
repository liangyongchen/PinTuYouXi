package com.example.testdemo1;

/**排行榜*/
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class TopActivity extends MainActivity {

	ExpandableListView  elv_top;
	MyExpandableListAdapter adapter;
	/**存储0-9的数组图片*/
	Bitmap[] nums;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_top);

		elv_top  =  (ExpandableListView) findViewById(R.id.elv_top);
		//		ExpandableListAdapter
		nums= new Bitmap[10];
		for (int i = 0; i < nums.length; i++) {
			nums[i]= BitmapFactory.decodeResource(getResources(), R.drawable.n0+i);
		}
		rank.select(scores, topNames);//
		adapter = new MyExpandableListAdapter();
		elv_top.setAdapter(adapter);
	}

	class MyExpandableListAdapter  extends  BaseExpandableListAdapter{
		String [] groupNames  = {"简单","一般","困难"};
		//布局加载器
		LayoutInflater  inflater  =LayoutInflater.from(TopActivity.this);
		/**getGroupCount  表示组的数量*/
		@Override
		public int getGroupCount() {
			return scores.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return scores[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return scores[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return scores[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return groupPosition*scores[groupPosition].length+childPosition;
		}
		/**
		 * 在数据层  同样的id是否表示同样的数据
		 * 
		 * */
		@Override
		public boolean hasStableIds() {
			return true;
		}
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView  textView;
			if(convertView!=null){
				textView  =  (TextView) convertView;
			}else{
				textView = new TextView(TopActivity.this);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(25);
				//setPadding  表示内边距
				textView.setPadding(100, 0, 0, 0);//左右上下
			}
			textView.setText(groupNames[groupPosition]);
			return textView;
		}

		/**
		 * 
		 * @see  android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View, ViewGroup)
		 * 获取每个数组  每个子项如何显示
		 * */
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View  view;
			if(convertView!=null){
				view  =convertView;
			}else{
				view  =  inflater.inflate(R.layout.item_elv_top_child, null);	
			}
			ImageView  tv_top  = (ImageView) view.findViewById(R.id.iv_top);
			ImageView  tv_score  = (ImageView) view.findViewById(R.id.iv_score);
			TextView  tv_name  = (TextView) view.findViewById(R.id.tv_name);

			tv_top.setImageBitmap(getBitmapByNum(childPosition+1));
			tv_score.setImageBitmap(getBitmapByNum(scores[groupPosition][childPosition]));
			//tv_top.setText(childPosition+1+"");
			//tv_score.setText(scores[groupPosition][childPosition]+"");
			tv_name.setText(topNames[groupPosition][childPosition]);
			return view;
		}
		/**
		 * isChildSelectable
		 * 表示一个子项是否被查看
		 * 表示一个组能否被展开
		 * */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		/**
		 * 通过一个数字生成一张对应的图片  画canvas
		 * 1.空白图片
		 * 2.在空白图片上生成画布
		 * 3.画图
		 * @param  num
		 * @return
		 * */
		public Bitmap  getBitmapByNum(int num){
			char  cs[]=(num+"").toCharArray();
			int width =cs.length*nums[0].getWidth();
			int height= nums[0].getHeight();
			//config  生成图片的格式
			Bitmap  bitmap =Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas  canvas = new Canvas(bitmap);
			for (int i = 0; i < cs.length; i++) {
				int  index=Integer.parseInt(cs[i]+"");//将字符串转换成对应数字
			    canvas.drawBitmap(nums[index], nums[index].getWidth()*i, 0, null);
		
			}
			return bitmap;
		}
	}

}

