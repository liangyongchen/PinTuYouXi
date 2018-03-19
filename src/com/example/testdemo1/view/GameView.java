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
//SurfaceView    ˫������ͼ
//SurfaceHolder    ��������ã�ͨ��SurfaceHolder���Ի�ȡSurfaceView��Ҫ��ͼ�Ļ���
//Callback    �ص�
//SurfaceHolder.Callback       �ṩ��SurfaceView�ڻ�ͼʱ�ڸ�����������
public class GameView extends  SurfaceView  implements  Runnable,SurfaceHolder.Callback{

	SurfaceHolder    holder;//���
	//�߳�ѭ���Ŀ���
	boolean  isRun;
	//��ͼ��ͼƬ
	Bitmap  bitmap;
	//SurfaceView  ͼƬ�Ŀ�͸�
	int Width,Height;
	//�Ѷ�
	int level;
	//��ϷͼƬ�������
	Bitmap[]  bitmaps;
	//��ֺ��СͼƬ�Ŀ�͸�
	int img_W,img_H;
	//����
	Paint  paint;
	//**����ͼƬ��ʾ*/
	public int [] nums;
	//**�����ʼ���������*/
	int [] buffNums;
	//**�طŹ����еĲ���*/
	int index;

	/**������*/
	GameActivity  gameActivity;
	SpriteGroup  group;

	/** ��Ϸ״̬  ����������Ϸ�ĸ����׶�*/
	public int state=2;  //stateҪ������ʼֵ�������Ļ�activity_game.xml�ļ����match_parent��ʾ��Ƶ
	public  static  final  int STATE_PLAY = 1;
	public  static  final  int STATE_READY = 2;
	public  static  final  int STATE_REPLAY = 0;

	/**��Ϸ��ʼʱ��*/
	public long startTime;//ʱ�����
	/**������֮ͣǰ��ʱ��*/
	public long bufftime;
	
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//���������������ӻص�
		holder  = getHolder();//��ʼ�����
		holder.addCallback(this);//����ʵ��Callback����������
		gameActivity  = (GameActivity) context;
		//bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.t0);//��ʼ��ͼƬ
		bitmap = BitmapFactory.decodeResource(getResources(), gameActivity.imgID);//��ʼ��ͼƬ
		level= gameActivity.Level;
		bitmaps= new Bitmap[level*level];

		paint  =  new Paint();
		paint.setColor(Color.WHITE);//���û�����ɫ
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
			 * 1.��ȡ�����еĻ���
			 *  2.�ڻ��廭���������ݸ���
			 *  3.�Ѹ��µ����ݽ�������ڻ��廭����
			 *  4.�����廭��������չʾ����Ļ��
			 */
			//1.��ȡ�����еĻ���
			Canvas  canvas  =  holder.lockCanvas();//lockCanvas() ��������
			if(canvas == null){//canvas�п���Ϊnull
				continue;
			}
//			//2.�ڻ��廭���������ݸ���
//			upDate();//��������
//			//3.�Ѹ��µ����ݽ�������ڻ��廭����
//			rendar(canvas);//��������
//			//4.��������,�����廭��������չʾ����Ļ��
//			holder.unlockCanvasAndPost(canvas);
			switch (state) {
			case STATE_READY:
				//������Ϸ׼���׶�
				//1.�׿�����ƶ�
				//2.���ƶ��Ľ������

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

					startTime=System.currentTimeMillis();//��ȡ��ǰʱ�䣬��λ����
					for (int i = 0; i < nums.length; i++) {
						buffNums[i] = nums[i];
					}
					group.rendar(canvas, bitmaps, buffNums, paint, Color.WHITE);
					holder.unlockCanvasAndPost(canvas);//����
				}
				break;
			case STATE_PLAY:
				//2.�����ݸ���
				upDate();
				//3.�����ݸ��µĽ���������廭������
				rendar(canvas);
				group.rendar(canvas, bitmaps, nums, paint, Color.WHITE);
				//4.�����廭��������չ�ֵ���Ļ��
				holder.unlockCanvasAndPost(canvas);//����
				break;
			case STATE_REPLAY:
				if(index < group.arrayList.size()){
					//�������ʼ�����BUFFNUMS����������һ��
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
					//Color.TRANSPARENT   ͸����ɫ
					group.rendar(canvas, bitmaps, buffNums, paint,Color.TRANSPARENT);
				}
				//4.�����廭��������չ�ֵ���Ļ��
				holder.unlockCanvasAndPost(canvas);//����
				break;
			}
		}
	}
	/**�ж�һ�������Ƿ��Ѿ�ƴ��*/
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
		//�������Ű�ͼƬ��ʾ�߿�λ�õ���
		this.Width  =  width;
		this.Height =  height;
		int  imgW = bitmap.getWidth();//��ȡͼƬ��
		int  imgH = bitmap.getHeight();//��ȡͼƬ��
		float  sx  = (float) Width/imgW;
		float  sy  = (float) Height/imgH;
		//Matrix  ����     ��׿�ṩ����ͼ�α任
		Matrix  matrix  =  new Matrix();
		matrix.setScale(sx, sy);//���������ŵģ�Scale(sx, sy)��ʾx�����y�������ű���

		//����
		/*�����ꡰBitmap.createBitmap(source, x, y, width, height, m, filter);��
		 *    ��bitmap
		 * source            �����ͼƬ
		 * x, y,             �ڸ�ͼƬ���ĸ�λ�ÿ�ʼ����
		 * width, height,    �ڸ�ͼƬ����������
		 * m,                ���ư����������Ǵ���
		 * filter            �����
		 */
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgW, imgH, matrix, true);
		//СͼƬ���
		img_W = Width/level;
		img_H = Height/level;

		/* ͼƬ��ȡ ��Bitmap.createBitmap(source, x, y, width, height);��
		 * source           ����ͼƬ��ȡ
		 * x, y,            ��ȡ����Ŀ�ʼλ��
		 * width, height    ��ȡͼƬ�Ŀ�͸ߴ�С
		 * ע�⣺   x  +  width  С�� <= bitmap.getWidth();��ȡͼƬ��
		 *      y  +  height С�� <= bitmap.getHeight();��ȡͼƬ��
		 */
		//��ͼƬѭ��
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < level; j++) {
				//bitmaps[i+j*level]��i+j*level��ʾ�ڼ���ͼƬ
				bitmaps[i+j*level] = 
						Bitmap.createBitmap(
								bitmap, i*img_W, j*img_H, img_W, img_H);	
			}
		}
		group  =  new SpriteGroup(bitmaps, nums);
		isRun  =true;
		Thread  thread  =  new Thread(this);//�����߳�
		thread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRun  =  false;
	}
	
	//2.�ڻ��廭���������ݸ���
	public  void upDate(){
		if(gameActivity.isStart){
			if(isWIN(nums)){
				state  =  STATE_REPLAY;
				//����һ��ʤ����ʱ��
				bufftime  =  getSocunds();
			}else{
				gameActivity.handler.sendEmptyMessage(0);//what
			}
		}
	}
	
	//3.��ͼ���Ѹ��µ����ݽ�������ڻ��廭����
	public void rendar(Canvas  canvas){
		//		/**canvas.drawBitmap(bitmap, left, top, paint);
		//		 *  left, top,    ���Ͻǵ������
		//		 */
		//		canvas.drawBitmap(bitmap, 0, 0, null);

		//		for (int i = 0; i < bitmaps.length; i++) {
		//			//�ҳ�ͼƬ�ڵڼ��еڼ���
		//			int raw =i/level;//�ڼ���
		//			int col =i%level;//�ڼ���
		//			//����ͼƬ���½�Ϊ��ɫ��
		//			if(nums[i]==bitmaps.length-1){//�������ͼƬ�����һ�飬����Ƴɰ�ɫ����
		//				//Rect  ����
		//				canvas.drawRect(col*img_W, raw*img_H,
		//						col*img_W+img_W, raw*img_H+img_H, paint);
		//			}else{//�������и�СͼƬ
		//				canvas.drawBitmap(bitmaps[nums[i]],col*img_W, raw*img_H, null);
		//			}
		//		}
		group.rendar(canvas, bitmaps, buffNums, paint, Color.WHITE);
	}
	//��ϷͼƬ���϶�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * event.getAction()  ��ȡ�ִ����Ķ���
		 * 
		 * event.getX();event.getY();��ȡ�ִ�����λ��
		 */
		if(gameActivity.isStart){//���if��
			if(state==STATE_PLAY){//��ƴͼ����ˣ����������ڴ�������¼�//2016.3.3

				if(event.getAction() == MotionEvent.ACTION_DOWN){
					int x = (int) event.getX();
					int y = (int) event.getY();
					gameActivity.sound.play(R.raw.a2);//�����Ч������Ϸ�����������
					group.event(x, y);		
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**��ʱ��ת���� 00:00:00��ʽ*/
	public String getCurrentTime(){

		int seconds = getSocunds();
		int second  = seconds%60;//��
		int min     = seconds / 60 % 60;//��
		int hour    = seconds /3600;//ʱ
		StringBuffer  time  =  new  StringBuffer();
		if(hour<10){//С��10��ʾ���Ǹ�λ��
			time.append("0").append(hour).append(":");//00:00:00��ʽ

		}else{
			time.append(hour).append(":");//00:00��ʽ
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

	/**�����Ϸ��ʼ�����ڵ�����*/
	public  int getSocunds(){
		return  (int) ((System.currentTimeMillis()-startTime)/1000+bufftime);
	}

}
