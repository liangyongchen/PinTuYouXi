package com.example.testdemo1.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class Sprite {

	int x,y,w,h;//x轴，一轴，w宽，h高
	int id;
	//矩形
	Rect rect;
	
	public Sprite(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		rect=new Rect();
	}
	public void rendar(Canvas canvas,Bitmap [] bitmaps,int [] nums,Paint paint,int color){
		//绘制图片
		w= bitmaps[nums[id]].getWidth();
		h= bitmaps[nums[id]].getHeight();
		canvas.drawBitmap(bitmaps[nums[id]], x, y, paint);
		//判断右下角图片,并绘图
		if(nums[id]==nums.length-1){
			paint.setColor(color);
			paint.setStyle(Style.FILL);
			canvas.drawRect(x, y, x+w, y+h, paint);
		}
		//画黑框
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);//填充边框
		paint.setStrokeWidth(2);//设置画笔粗细
		
		//矩形长宽高变量
		rect.left=x;
		rect.top=y;
		rect.right=x+w;
		rect.bottom=y+h;
		canvas.drawRect(rect, paint);	
	}
	//获取中心点x坐标,可以进行图片交互
	public int getCenterX(){
		return x+w/2;
		
	}
	//获取中心点y坐标,可以进行图片交互
	public int getCenterY(){
		return y+h/2;
		
	}
	
	
	
	
}
