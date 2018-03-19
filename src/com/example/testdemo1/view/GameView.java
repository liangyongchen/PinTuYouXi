package com.example.testdemo1.view;

import java.security.acl.Group;
import java.util.Random;

import com.example.testdemo1.GameActivity;
import com.example.testdemo1.R;
import com.example.testdemo1.sprite.Sprite;
import com.example.testdemo1.sprite.SpriteGroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//SurfaceView    双缓冲视图
//SurfaceHolder    句柄，作用：通过SurfaceHolder可以获取SurfaceView需要绘图的画布
//Callback    回调
//SurfaceHolder.Callback       提供了SurfaceView在绘图时期各个生命周期
public class GameView extends  SurfaceView  implements  Runnable,SurfaceHolder.Callback{

	SurfaceHolder    holder;//句柄
	//线程循环的开关
	boolean  isRun;
	//绘图的图片
	Bitmap  bitmap;
	//SurfaceView  图片的宽和高
	int Width,Height;
	//难度
	int level;
	//游戏图片拆分数组
	Bitmap[]  bitmaps;
	//拆分后的小图片的宽和高
	int img_W,img_H;
	//画笔
	Paint  paint;
	//**控制图片显示*/
	public int [] nums;
	//**缓存最开始的随机数组*/
	int [] buffNums;
	//**回放过程中的步数*/
	int index;

	/**调用类*/
	GameActivity  gameActivity;
	SpriteGroup  group;

	/** 游戏状态  用来区分游戏的各个阶段*/
	public int state=2;  //state要给个初始值，不给的话activity_game.xml文件里的match_parent显示黑频
	public  static  final  int STATE_PLAY = 1;
	public  static  final  int STATE_READY = 2;
	public  static  final  int STATE_REPLAY = 0;

	/**游戏开始时间*/
	public long startTime;//时间变量
	/**缓存暂停之前的时间*/
	public long bufftime;
	
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//创建监听器，增加回调
		holder  = getHolder();//初始化句柄
		holder.addCallback(this);//用来实现Callback的三个方法
		gameActivity  = (GameActivity) context;
		//bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t0);//初始化图片
		bitmap = BitmapFactory.decodeResource(getResources(), gameActivity.imgID);//初始化图片
		level= gameActivity.Level;
		bitmaps= new Bitmap[level*level];

		paint  =  new Paint();
		paint.setColor(Color.WHITE);//设置画笔颜色
		nums  =  new int[level*level];
		buffNums=new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i]  = i;
		}
//		Random  random  =new Random();
//		for (int i = nums.length-1;i>0; i--) {
//			int ins = random.nextInt(i);
//			int  ty = nums[i];
//			nums[i]  =  nums[ins];
//			nums[ins] = ty;
//		}
	}


	@Override
	public void run() {
		while(isRun){
			/**
			 * 1.获取缓冲中的画布
			 *  2.在缓冲画布上做数据更新
			 *  3.把更新的数据结果储存在缓冲画布上
			 *  4.将缓冲画布的内容展示到屏幕上
			 */
			//1.获取缓冲中的画布
			Canvas  canvas  =  holder.lockCanvas();//lockCanvas() 画布加锁
			if(canvas == null){//canvas有可能为null
				continue;
			}
//			//2.在缓冲画布上做数据更新
//			upDate();//方法调用
//			//3.把更新的数据结果储存在缓冲画布上
//			rendar(canvas);//方法调用
//			//4.画布解锁,将缓冲画布的内容展示到屏幕上
//			holder.unlockCanvasAndPost(canvas);
			switch (state) {
			case STATE_READY:
				//处理游戏准备阶段
				//1.白块随机移动
				//2.将移动的结果绘制

				if(index<20){
					group.ready();
					group.rendar(canvas, bitmaps, nums, paint, Color.WHITE);
					holder.unlockCanvasAndPost(canvas);
					index++;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					index = 0;
					state = STATE_PLAY;

					startTime=System.currentTimeMillis();//获取当前时间，单位毫秒
					for (int i = 0; i < nums.length; i++) {
						buffNums[i] = nums[i];
					}
					group.rendar(canvas, bitmaps, buffNums, paint, Color.WHITE);
					holder.unlockCanvasAndPost(canvas);//解锁
				}
				break;
			case STATE_PLAY:
				//2.做数据更新
				upDate();
				//3.讲数据更新的结果画到缓冲画布上面
				rendar(canvas);
				group.rendar(canvas, bitmaps, nums, paint, Color.WHITE);
				//4.讲缓冲画布的内容展现到屏幕上
				holder.unlockCanvasAndPost(canvas);//解锁
				break;
			case STATE_REPLAY:
				if(index < group.arrayList.size()){
					//缓存了最开始数组的BUFFNUMS上面重新走一遍
					group.upDate(buffNums, index);
					group.rendar(canvas, bitmaps, buffNums, paint,Color.WHITE);
					index++;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				if(isWIN(buffNums)){
					//Color.TRANSPARENT   透明颜色
					group.rendar(canvas, bitmaps, buffNums, paint,Color.TRANSPARENT);
				}
				//4.讲缓冲画布的内容展现到屏幕上
				holder.unlockCanvasAndPost(canvas);//解锁
				break;
			}
		}
	}
	/**判断一个数组是否已经拼好*/
	public boolean isWIN(int[] nums){
		for (int i = 0; i < nums.length; i++) {
			if(nums[i]!=i){
				return  false;
			} 
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {


	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//做个缩放把图片显示边框位置调好
		this.Width  =  width;
		this.Height =  height;
		int  imgW = bitmap.getWidth();//获取图片宽
		int  imgH = bitmap.getHeight();//获取图片高
		float  sx  = (float) Width/imgW;
		float  sy  = (float) Height/imgH;
		//Matrix  矩形     安卓提供用于图形变换
		Matrix  matrix  =  new Matrix();
		matrix.setScale(sx, sy);//用于做缩放的，Scale(sx, sy)表示x方向和y方向缩放比例

		//缩放
		/*定义完“Bitmap.createBitmap(source, x, y, width, height, m, filter);”
		 *    给bitmap
		 * source            处理的图片
		 * x, y,             在该图片的哪个位置开始处理
		 * width, height,    在该图片处理多大的面积
		 * m,                对推案进行怎样是处理
		 * filter            抗锯齿
		 */
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgW, imgH, matrix, true);
		//小图片宽高
		img_W = Width/level;
		img_H = Height/level;

		/* 图片截取 “Bitmap.createBitmap(source, x, y, width, height);”
		 * source           那张图片截取
		 * x, y,            截取坐标的开始位置
		 * width, height    截取图片的宽和高大小
		 * 注意：   x  +  width  小于 <= bitmap.getWidth();获取图片宽
		 *      y  +  height 小于 <= bitmap.getHeight();获取图片高
		 */
		//拆图片循环
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < level; j++) {
				//bitmaps[i+j*level]的i+j*level表示第几张图片
				bitmaps[i+j*level] = 
						Bitmap.createBitmap(
								bitmap, i*img_W, j*img_H, img_W, img_H);	
			}
		}
		group  =  new SpriteGroup(bitmaps, nums);
		isRun  =true;
		Thread  thread  =  new Thread(this);//创建线程
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun  =  false;
	}
	
	//2.在缓冲画布上做数据更新
	public  void upDate(){
		if(gameActivity.isStart){
			if(isWIN(nums)){
				state  =  STATE_REPLAY;
				//保存一下胜利的时间
				bufftime  =  getSocunds();
			}else{
				gameActivity.handler.sendEmptyMessage(0);//what
			}
		}
	}
	
	//3.绘图，把更新的数据结果储存在缓冲画布上
	public void rendar(Canvas  canvas){
		//		/**canvas.drawBitmap(bitmap, left, top, paint);
		//		 *  left, top,    左上角的坐标点
		//		 */
		//		canvas.drawBitmap(bitmap, 0, 0, null);

		//		for (int i = 0; i < bitmaps.length; i++) {
		//			//找出图片在第几行第几列
		//			int raw =i/level;//第几列
		//			int col =i%level;//第几行
		//			//设置图片右下角为白色框
		//			if(nums[i]==bitmaps.length-1){//如果属于图片的最后一块，则绘制成白色矩形
		//				//Rect  矩形
		//				canvas.drawRect(col*img_W, raw*img_H,
		//						col*img_W+img_W, raw*img_H+img_H, paint);
		//			}else{//则正常切割小图片
		//				canvas.drawBitmap(bitmaps[nums[i]],col*img_W, raw*img_H, null);
		//			}
		//		}
		group.rendar(canvas, bitmaps, buffNums, paint, Color.WHITE);
	}
	//游戏图片的拖动
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * event.getAction()  获取手触摸的动作
		 * 
		 * event.getX();event.getY();获取手触摸的位置
		 */
		if(gameActivity.isStart){//这个if是
			if(state==STATE_PLAY){//当拼图完成了，不在让其在触发点击事件//2016.3.3

				if(event.getAction() == MotionEvent.ACTION_DOWN){
					int x = (int) event.getX();
					int y = (int) event.getY();
					gameActivity.sound.play(R.raw.a2);//添加音效，玩游戏点击出现声音
					group.event(x, y);		
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**将时间转换成 00:00:00格式*/
	public String getCurrentTime(){

		int seconds = getSocunds();
		int second  = seconds%60;//秒
		int min     = seconds / 60 % 60;//分
		int hour    = seconds /3600;//时
		StringBuffer  time  =  new  StringBuffer();
		if(hour<10){//小于10表示的是个位数
			time.append("0").append(hour).append(":");//00:00:00格式

		}else{
			time.append(hour).append(":");//00:00格式
		}
		if(min<10){
			time.append("0").append(min).append(":");

		}else{
			time.append(min).append(":");
		}
		if(second<10){
			time.append("0").append(second);

		}else{
			time.append(second);
		}
		return  time.toString();
	}

	/**获从游戏开始到现在的秒数*/
	public  int getSocunds(){
		return  (int) ((System.currentTimeMillis()-startTime)/1000+bufftime);
	}

}
