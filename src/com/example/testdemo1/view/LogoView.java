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
	/**����*/
	Paint  paint;
	/**�������飬��������ͼƬ*/
	Bitmap  []  bitmaps;
	/**ȷ�ϻ�������һ��ͼƬ*/
	int index;
	//����ƴͼ��͸�
	int screenW=480,screenH=800;
	/**����͸���ȱ�����ʹ�տ�ʼ����ͼƬ��������
	 * 0xff  ��ʾ16����
	 */
	int a = 0xff;
	
	LogoActivity  logoActivity;

	/**��XML�ļ���ʹ��
	 * @param context    ��Context context  �������Ľ���LogoActivity
	 *             ��setContentView(new LogoView(LogoActivity.this))����ʾ����
	 * @param attrs
	 */
	public LogoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//�½����ʣ���XML�ļ���ʹ��
		paint = new Paint();
		init();//��ʼ����������
		logoActivity  =  (LogoActivity) context;
	}
	
	/**��Java�ļ���ʹ��
	 * 
	 * @param context  ��Context context  �������Ľ���LogoActivity
	 *             ��setContentView(new LogoView(LogoActivity.this))����ʾ����
	 */
	public LogoView(Context context) {
		super(context);
		//�½����ʣ���Java�ļ���ʹ��
		paint = new Paint();
		init();//��ʼ����������
		logoActivity = (LogoActivity) context;
	}
	
	/**��ɳ�ʼ��*/
	public  void  init(){
		bitmaps  =  new Bitmap[3];//��ʼ������������ͼƬ
		bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mmlogo);
		bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.and1);
		bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		//�����߳�
		new Thread(this).start();//�߳̿�ʼ
	}

	@Override
	public void run() {
		while(index < bitmaps.length){//С������ĳ���

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			/*����͸����ѭ����ʹ�տ�ʼ����ͼƬ��������*/
			while(a>5){
				try {
					Thread.sleep(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
				a-=5;
				//���ø÷���֪ͨϵͳˢ�»��Ƶ�ͼ��
				postInvalidate();
			}
			index++;
			//���ø÷���֪ͨϵͳˢ�»��Ƶ�ͼ��
			postInvalidate();
			a=0xff;//*����͸���ȱ�������  a-=5 �Ļ���a=0xff
		}
		logoActivity.toMenu();//����LogoActivityʵ����ת��MenuActivity����
	}
	
	/**�����õģ���ͼ
	 * Canvas  ����
	 *ͼƬ����ʾ���������onDraw������ʾ������
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//���ñ�����ɫ
		canvas.drawColor(Color.WHITE);
		/*����͸���ȣ�ʹ�տ�ʼ����ͼƬ��������*/
		paint.setAlpha(a);
		if(index < bitmaps.length){
			//����ͼƬ����Ŀ�͸�
			int bx = screenW/2 - bitmaps[index].getWidth()/2;
			int by = screenH/2 - bitmaps[index].getHeight()/2;
			//��ͼƬ
			canvas.drawBitmap(bitmaps[index], bx, by, paint);
		}

		//		//���ñ�����ɫ
		//		canvas.drawColor(Color.WHITE);
		//		//������ɫ����
		//		paint.setColor(Color.BLACK);
		//		//���û��ʵĴ�ϸ
		//		paint.setStrokeWidth(5);
		//		/*canvas.drawLine(startX, startY, stopX, stopY, paint);
		//		 * startX ,  startY     ���
		//		 * stopX  ,  stopY      �յ�
		//		 * paint                ����
		//		 * ��һ����
		//		 */
		//		canvas.drawLine(0, 0, 100, 100, paint);//��ֱ
		//		//��һ��ʵ��԰
		//		paint.setStyle(Style.STROKE);//FILL  ��ʾʵ��
		//		paint.setColor(Color.BLUE);//��Բ����ɫ
		//		/*canvas.drawCircle(cx, cy, radius, paint);//��Բ
		//		 * cx, cy,      ��ʾԲ��
		//		 * radius      ��ʾ�뾶
		//		 * paint        ����
		//		 */
		//		canvas.drawCircle(240, 400, 50, paint);//��Բ
		//		/* ������
		//		 * Rect rect = new Rect(left, top, right, bottom);//���β�������
		//		 * left, top            ָ���ϽǶ��������
		//		 * right, bottom    ָ���½Ƕ��������
		//		 */
		//		Rect rect = new Rect(150, 100, 350, 200);//���β�������
		//		paint.setStyle(Style.FILL);//��������Ϊʵ��
		//		canvas.drawRect(rect, paint);//������
		//
		//		/*  ��ʾͼƬ������
		//		 * Resources res  = getResources();    ����� res  ��ʾ·�����洢ͼƬ��·��
		//		 *R.drawable.logo��ʾ��Դ·�����������·��
		//		 */
		//		Resources res  = getResources();
		//		//����ͼƬ�����ͼƬBitmap bitmap 
		//		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.logo); //  ����logoͼƬ
		//		/**��ͼƬ  canvas.drawBitmap(bitmap, left, top, paint);
		//		 *          bitmap        Ҫ����ͼƬ
		//		 *          left, top       ָ���ϽǶ��������
		//		 */
		//		canvas.drawBitmap(bitmap, 10, 10, paint);

	}

}
