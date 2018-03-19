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
	//公用集合
	public ArrayList<Point> arrayList = new ArrayList<Point>();
	public SpriteGroup(Bitmap[] bitmaps, int[] nums) {
		this.bitmaps = bitmaps;
		this.nums = nums;

		sprites  =  new Sprite[bitmaps.length];
		for (int i = 0; i < nums.length; i++) {
			//循环为每一个Sprite类赋值,获取第几行，第几列
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

	//处理点击（拖动）事件
	public void event(int touchX,int touchY){
		Point  point = getIndex(touchX,touchY);
		Sprite  sprite1 = sprites[point.x];//点击的格子
		Sprite  sprite2 = sprites[point.y];//目标格子
		//勾股定理
		int lx = sprite1.getCenterX()-sprite2.getCenterX();
		int ly = sprite1.getCenterY()-sprite2.getCenterY();
		int len = (int) Math.sqrt(lx*lx+ly*ly);
		//判断如果len 等于小图片宽或高  证明两个各自相邻  则交换
		if(len == bitmaps[0].getWidth()|| len == bitmaps[0].getHeight()){
			//交换
			nums[point.y] = nums[point.x];
			nums[point.x] = nums.length-1;
			arrayList.add(point);
		}

	}

	/**获取点击位置对应的格子以及白块所在的格子
	 * point.x;    表示点击位置所在格子的下标
	 * point.y;    表示白色格子所在位置的下标 
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
		//获取对应走的第几步
		Point point = arrayList.get(index);
		int i1 = point.x;
		int i2 = point.y;

		//按照已走的步骤交互两个格子
		buffNums[i2] = buffNums[i1];
		buffNums[i1] = buffNums.length-1;	
	}

	//存储能与百块交互的格子
	ArrayList<Point>  points = new ArrayList<Point>();
	Random  random  =  new  Random();
	public void ready(){
		points.clear();//清空上一步数据
		getList();
		//从集合随机一步出来
		int index = random.nextInt(points.size());
		Point point = points.get(index);
		//交互两个格子
		nums[point.y] = nums[point.x];
		nums[point.x] = nums.length-1;

	}
	//用来获取能够与百块交换的所有格子
	public void getList() {
		//1.先获取白块的位置		
		int white = 0;
		for (int i = 0; i < nums.length; i++) {
			if(nums[i]==nums.length-1){
				white = i;
			}
		}
		//获取与白块挨着的所有格子
		for (int i = 0; i < sprites.length; i++) {
			//拿到点击的格子
			Sprite  sprite1  = sprites[i];
			//拿到目标格子
			Sprite  sprite2  = sprites[white];
			//勾股定理
			int lx  = sprite1.getCenterX()-sprite2.getCenterX();
			int ly  = sprite1.getCenterY()-sprite2.getCenterY();	
			int  len = (int) Math.sqrt(lx*lx+ly*ly);	
			//如果len 等于小图片宽或高  证明两个各自相邻  则交换	
			if(len == bitmaps[0].getWidth()||len == bitmaps[0].getHeight()){
				Point point = new Point(i,white);
				
				points.add(point);
			}		
		}

	}









}
