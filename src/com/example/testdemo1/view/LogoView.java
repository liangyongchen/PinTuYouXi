package com.example.testdemo1.view;

import com.example.testdemo1.LogoActivity;
import com.example.testdemo1.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class LogoView extends View implements Runnable{
	/**画笔*/
	Paint  paint;
	/**定义数组，用来保存图片*/
	Bitmap  []  bitmaps;
	/**确认画的是那一张图片*/
	int index;
	//设置拼图宽和高
	int screenW=480,screenH=800;
	/**设置透明度变量，使刚开始加载图片交换生动
	 * 0xff  表示16进制
	 */
	int a = 0xff;
	
	LogoActivity  logoActivity;

	/**在XML文件中使用
	 * @param context    的Context context  传进来的近视LogoActivity
	 *             在setContentView(new LogoView(LogoActivity.this))；显示出来
	 * @param attrs
	 */
	public LogoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//新建画笔，在XML文件中使用
		paint = new Paint();
		init();//初始化方法调用
		logoActivity  =  (LogoActivity) context;
	}
	
	/**在Java文件中使用
	 * 
	 * @param context  的Context context  传进来的近视LogoActivity
	 *             在setContentView(new LogoView(LogoActivity.this))；显示出来
	 */
	public LogoView(Context context) {
		super(context);
		//新建画笔，在Java文件中使用
		paint = new Paint();
		init();//初始化方法调用
		logoActivity = (LogoActivity) context;
	}
	
	/**完成初始化*/
	public  void  init(){
		bitmaps  =  new Bitmap[3];//初始化，定义三张图片
		bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mmlogo);
		bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.and1);
		bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		//启动线程
		new Thread(this).start();//线程开始
	}

	@Override
	public void run() {
		while(index < bitmaps.length){//小于数组的长度

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*设置透明度循环，使刚开始加载图片交换生动*/
			while(a>5){
				try {
					Thread.sleep(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
				a-=5;
				//利用该方法通知系统刷新绘制的图形
				postInvalidate();
			}
			index++;
			//利用该方法通知系统刷新绘制的图形
			postInvalidate();
			a=0xff;//*设置透明度变量，把  a-=5 改回来a=0xff
		}
		logoActivity.toMenu();//调用LogoActivity实现跳转到MenuActivity界面
	}
	
	/**绘制用的，绘图
	 * Canvas  画布
	 *图片的显示就是在这个onDraw方法显示出来的
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//设置背景颜色
		canvas.drawColor(Color.WHITE);
		/*设置透明度，使刚开始加载图片交换生动*/
		paint.setAlpha(a);
		if(index < bitmaps.length){
			//计算图片本身的宽和高
			int bx = screenW/2 - bitmaps[index].getWidth()/2;
			int by = screenH/2 - bitmaps[index].getHeight()/2;
			//画图片
			canvas.drawBitmap(bitmaps[index], bx, by, paint);
		}

		//		//设置背景颜色
		//		canvas.drawColor(Color.WHITE);
		//		//画笔颜色设置
		//		paint.setColor(Color.BLACK);
		//		//设置画笔的粗细
		//		paint.setStrokeWidth(5);
		//		/*canvas.drawLine(startX, startY, stopX, stopY, paint);
		//		 * startX ,  startY     起点
		//		 * stopX  ,  stopY      终点
		//		 * paint                画笔
		//		 * 画一条线
		//		 */
		//		canvas.drawLine(0, 0, 100, 100, paint);//画直
		//		//画一个实心园
		//		paint.setStyle(Style.STROKE);//FILL  表示实心
		//		paint.setColor(Color.BLUE);//画圆的颜色
		//		/*canvas.drawCircle(cx, cy, radius, paint);//画圆
		//		 * cx, cy,      表示圆心
		//		 * radius      表示半径
		//		 * paint        画笔
		//		 */
		//		canvas.drawCircle(240, 400, 50, paint);//画圆
		//		/* 画矩形
		//		 * Rect rect = new Rect(left, top, right, bottom);//矩形参数设置
		//		 * left, top            指左上角定点的坐标
		//		 * right, bottom    指右下角定点的坐标
		//		 */
		//		Rect rect = new Rect(150, 100, 350, 200);//矩形参数设置
		//		paint.setStyle(Style.FILL);//居心设置为实心
		//		canvas.drawRect(rect, paint);//画矩形
		//
		//		/*  表示图片工程类
		//		 * Resources res  = getResources();    里面的 res  表示路劲，存储图片的路劲
		//		 *R.drawable.logo表示资源路径里面的资料路径
		//		 */
		//		Resources res  = getResources();
		//		//创建图片类加载图片Bitmap bitmap 
		//		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.logo); //  加载logo图片
		//		/**画图片  canvas.drawBitmap(bitmap, left, top, paint);
		//		 *          bitmap        要画的图片
		//		 *          left, top       指左上角定点的坐标
		//		 */
		//		canvas.drawBitmap(bitmap, 10, 10, paint);

	}

}
