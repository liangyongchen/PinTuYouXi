package com.example.testdemo1.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class SpriteGroup {

	Sprite [] sprites;
	Bitmap [] bitmaps;
	int  []   nums;
	//���ü���
	public ArrayList<Point> arrayList = new ArrayList<Point>();
	public SpriteGroup(Bitmap[] bitmaps, int[] nums) {
		this.bitmaps = bitmaps;
		this.nums = nums;

		sprites  =  new Sprite[bitmaps.length];
		for (int i = 0; i < nums.length; i++) {
			//ѭ��Ϊÿһ��Sprite�ำֵ,��ȡ�ڼ��У��ڼ���
			int raw = (int) (i/Math.sqrt(nums.length));
			int col = (int) (i%Math.sqrt(nums.length));

			int x = col*bitmaps[i].getWidth();
			int y = raw*bitmaps[i].getHeight();
			sprites[i] = new Sprite(x, y, i);
		}
	}

	public void rendar(Canvas canvas,Bitmap [] bitmaps,int [] nums,Paint paint,int color){
		for (int i = 0; i < nums.length; i++) {
			sprites[i].rendar(canvas, bitmaps, nums, paint, color);
		}	
	}

	//���������϶����¼�
	public void event(int touchX,int touchY){
		Point  point = getIndex(touchX,touchY);
		Sprite  sprite1 = sprites[point.x];//����ĸ���
		Sprite  sprite2 = sprites[point.y];//Ŀ�����
		//���ɶ���
		int lx = sprite1.getCenterX()-sprite2.getCenterX();
		int ly = sprite1.getCenterY()-sprite2.getCenterY();
		int len = (int) Math.sqrt(lx*lx+ly*ly);
		//�ж����len ����СͼƬ����  ֤��������������  �򽻻�
		if(len == bitmaps[0].getWidth()|| len == bitmaps[0].getHeight()){
			//����
			nums[point.y] = nums[point.x];
			nums[point.x] = nums.length-1;
			arrayList.add(point);
		}

	}

	/**��ȡ���λ�ö�Ӧ�ĸ����Լ��׿����ڵĸ���
	 * point.x;    ��ʾ���λ�����ڸ��ӵ��±�
	 * point.y;    ��ʾ��ɫ��������λ�õ��±� 
	 * */
	public Point getIndex(int touchX, int touchY) {
		Point  point  =  new Point();
		for (int i = 0; i < sprites.length; i++) {
			if(sprites[i].rect.contains(touchX, touchY)){
				point.x = i ;
				break;
			}
		}

		for (int i = 0; i < sprites.length; i++) {
			if(nums[i]==nums.length-1){
				point.y = i;
				break;
			}
		}
		return point;
	}

	public void upDate(int [] buffNums,int index){
		//��ȡ��Ӧ�ߵĵڼ���
		Point point = arrayList.get(index);
		int i1 = point.x;
		int i2 = point.y;

		//�������ߵĲ��轻����������
		buffNums[i2] = buffNums[i1];
		buffNums[i1] = buffNums.length-1;	
	}

	//�洢����ٿ齻���ĸ���
	ArrayList<Point>  points = new ArrayList<Point>();
	Random  random  =  new  Random();
	public void ready(){
		points.clear();//�����һ������
		getList();
		//�Ӽ������һ������
		int index = random.nextInt(points.size());
		Point point = points.get(index);
		//������������
		nums[point.y] = nums[point.x];
		nums[point.x] = nums.length-1;

	}
	//������ȡ�ܹ���ٿ齻�������и���
	public void getList() {
		//1.�Ȼ�ȡ�׿��λ��		
		int white = 0;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i]==nums.length-1){
				white = i;
			}
		}
		//��ȡ��׿鰤�ŵ����и���
		for (int i = 0; i < sprites.length; i++) {
			//�õ�����ĸ���
			Sprite  sprite1  = sprites[i];
			//�õ�Ŀ�����
			Sprite  sprite2  = sprites[white];
			//���ɶ���
			int lx  = sprite1.getCenterX()-sprite2.getCenterX();
			int ly  = sprite1.getCenterY()-sprite2.getCenterY();	
			int  len = (int) Math.sqrt(lx*lx+ly*ly);	
			//���len ����СͼƬ����  ֤��������������  �򽻻�	
			if(len == bitmaps[0].getWidth()||len == bitmaps[0].getHeight()){
				Point point = new Point(i,white);
				
				points.add(point);
			}		
		}

	}









}
