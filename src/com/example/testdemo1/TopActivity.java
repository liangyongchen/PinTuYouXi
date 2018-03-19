package com.example.testdemo1;

/**���а�*/
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
	/**�洢0-9������ͼƬ*/
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
		String [] groupNames  = {"��","һ��","����"};
		//���ּ�����
		LayoutInflater  inflater  =LayoutInflater.from(TopActivity.this);
		/**getGroupCount  ��ʾ�������*/
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
		 * �����ݲ�  ͬ����id�Ƿ��ʾͬ��������
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
				//setPadding  ��ʾ�ڱ߾�
				textView.setPadding(100, 0, 0, 0);//��������
			}
			textView.setText(groupNames[groupPosition]);
			return textView;
		}

		/**
		 * 
		 * @see  android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View, ViewGroup)
		 * ��ȡÿ������  ÿ�����������ʾ
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
		 * ��ʾһ�������Ƿ񱻲鿴
		 * ��ʾһ�����ܷ�չ��
		 * */
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		/**
		 * ͨ��һ����������һ�Ŷ�Ӧ��ͼƬ  ��canvas
		 * 1.�հ�ͼƬ
		 * 2.�ڿհ�ͼƬ�����ɻ���
		 * 3.��ͼ
		 * @param  num
		 * @return
		 * */
		public Bitmap  getBitmapByNum(int num){
			char  cs[]=(num+"").toCharArray();
			int width =cs.length*nums[0].getWidth();
			int height= nums[0].getHeight();
			//config  ����ͼƬ�ĸ�ʽ
			Bitmap  bitmap =Bitmap.createBitmap(width, height, Config.ARGB_8888);
			Canvas  canvas = new Canvas(bitmap);
			for (int i = 0; i < cs.length; i++) {
				int  index=Integer.parseInt(cs[i]+"");//���ַ���ת���ɶ�Ӧ����
			    canvas.drawBitmap(nums[index], nums[index].getWidth()*i, 0, null);
		
			}
			return bitmap;
		}
	}

}

