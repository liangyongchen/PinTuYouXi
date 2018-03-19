package com.example.testdemo1;

import com.example.testdemo1.db.Rank;
import com.example.testdemo1.music.Music;
import com.example.testdemo1.music.Sound;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
/**Activity 是手机应用程序的一个界面*/
public class MainActivity extends Activity {
	//管理是否跳转到暂停界面
	public boolean isBack;//管理跳转变量
	/**调用播放音乐*/
	public  Music  music;
	/**音效*/
	public  Sound  sound;
	/**控制声音*/
	public static  boolean  isAudio;
	/** 存储分数 二维数组*/
	public static  int [][]  scores;
	/** 存储名字*/
	public static String [][]  topNames;
	/**存储 xml数据*/
	public static SharedPreferences  sp;
	/**数据库存工具*/
	public static Rank rank;
	
	//图片数组
	public  int [] imgs  ={ R.drawable.t0,R.drawable.t1,R.drawable.t2,
			R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,
			R.drawable.t7,R.drawable.t8,R.drawable.t9,};

	/**2016.3.9上午
	 * onCreate  相当于Java成粗的main方法
	 * 在java文件中，一个程序是有开始和结束，程序是有生命周期的 
	 */
	/**创建方法*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreate");
		music  =new Music(this);//初始化音乐类
		sound  = new Sound(this);//初始化音效
		
		if(scores==null){
			scores = new int[3][10];
		}
		if(topNames==null){
			topNames = new String[3][10];
			for(int i=0;i<topNames.length;i++){
				for (int j = 0; j < topNames[i].length; j++) {
					topNames[i][j] =null;
				}
			}		
		}

		if(sp==null){
			//初始化SharedPreferences
			/**getSharedPreferences(name, mode);
			 * name  xml文件名字
			 * mode  xml模式
			 */
			/*mode  xml模式
			 * @see #MODE_PRIVATE
			 *    MODE_PRIVATE   不允许别的程序访问 
			 * @see #MODE_WORLD_READABLE
			 *    MODE_WORLD_READABLE  允许别的程序读该文件
			 * @see #MODE_WORLD_WRITEABLE
			 *    MODE_WORLD_WRITEABLE  允许别的程序对该文件进行写入
			 * @see #MODE_MULTI_PROCESS
			 *    MODE_MULTI_PROCESS    该模式用于多线程操作数据时 线程安全
			 * 
			 */
			sp = getSharedPreferences("user", MODE_PRIVATE);	
		}
		if(rank==null){
			rank = new Rank(this);
		}
	}
	
	public void saveScores(){
		Editor editor = sp.edit();
		//editor.put(key, value);  向文件中存储值
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores[i].length; j++) {
				// 向 xml 文件中通过不同的key  存储不同飞分数
				editor.putInt(""+i+j+"scores", scores[i][j]);
				editor.putString(""+i+j+"name", topNames[i][j]);

			}
		}
		//  commit  提交
		editor.commit();

	}

	public void loadScores(){
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores[i].length; j++) {
				scores[i][j]= sp.getInt(""+i+j+"scores", 0);
				topNames[i][j]= sp.getString(""+i+j+"name", null);
			}
		}

	}
	
	/**开始方法*/
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("onStart");
	}
	/**继续方法*/
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
	}
	//跳转到暂停界面
	public void toPause(){
		//Intent    意图,界面跳转需要
		Intent intent = new Intent();
		/*setClass(packageContext, cls);
		 * packageContext   Context  上下文对象
		 * Class<?> cls     指跳转到那个界面
		 */
		//设置从MainActivity 跳转到PauseActivity界面
		intent.setClass(MainActivity.this, PauseActivity.class);
		//启动意图跳转
		startActivity(intent);
	}

	//但一个按键被按下的时候  执行
	// keyCode  表示按下的按键       返回键按下不进入暂停界面    要把 isBack设置为true
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*KeyEvent.KEYCODE_BACK   系统的返回键
		 * keyCode      新建的点击建
		 * 当点击建和系统的返回键相等时，直接跳转，不进入暂停界面
		 */
		if(keyCode == KeyEvent.KEYCODE_BACK){
			isBack = true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**暂停方法*/
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("onPause");
		//如果isBack为假的时候
		if(!isBack){
			toPause();
		}
		music.pauseMusic();


	}
	/**重新开始方法*/
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart");
		//重置暂停功能
		isBack = false;//开始的时候，暂停的界面执行
		music.playMusic();
	}
	/**停止方法*/
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("onStop");

	}
	/**销毁方法*/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy");
		music.stopMusic();
	}
}

