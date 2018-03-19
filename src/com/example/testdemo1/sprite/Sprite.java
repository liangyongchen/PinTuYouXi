package com.example.testdemo1.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

public class Sprite {

	int x,y,w,h;//x�ᣬһ�ᣬw��h��
	int id;
	//����
	Rect rect;
	
	public Sprite(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		rect=new Rect();
	}
	public void rendar(Canvas canvas,Bitmap [] bitmaps,int [] nums,Paint paint,int color){
		//����ͼƬ
		w= bitmaps[nums[id]].getWidth();
		h= bitmaps[nums[id]].getHeight();
		canvas.drawBitmap(bitmaps[nums[id]], x, y, paint);
		//�ж����½�ͼƬ,����ͼ
		if(nums[id]==nums.length-1){
			paint.setColor(color);
			paint.setStyle(Style.FILL);
			canvas.drawRect(x, y, x+w, y+h, paint);
		}
		//���ڿ�
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);//���߿�
		paint.setStrokeWidth(2);//���û��ʴ�ϸ
		
		//���γ���߱���
		rect.left=x;
		rect.top=y;
		rect.right=x+w;
		rect.bottom=y+h;
		canvas.drawRect(rect, paint);	
	}
	//��ȡ���ĵ�x����,���Խ���ͼƬ����
	public int getCenterX(){
		return x+w/2;
		
	}
	//��ȡ���ĵ�y����,���Խ���ͼƬ����
	public int getCenterY(){
		return y+h/2;
		
	}
	
	
	
	
}
