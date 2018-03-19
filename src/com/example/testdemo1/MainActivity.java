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
/**Activity ���ֻ�Ӧ�ó����һ������*/
public class MainActivity extends Activity {
	//�����Ƿ���ת����ͣ����
	public boolean isBack;//������ת����
	/**���ò�������*/
	public  Music  music;
	/**��Ч*/
	public  Sound  sound;
	/**��������*/
	public static  boolean  isAudio;
	/** �洢���� ��ά����*/
	public static  int [][]  scores;
	/** �洢����*/
	public static String [][]  topNames;
	/**�洢 xml����*/
	public static SharedPreferences  sp;
	/**���ݿ�湤��*/
	public static Rank rank;
	
	//ͼƬ����
	public  int [] imgs  ={ R.drawable.t0,R.drawable.t1,R.drawable.t2,
			R.drawable.t3,R.drawable.t4,R.drawable.t5,R.drawable.t6,
			R.drawable.t7,R.drawable.t8,R.drawable.t9,};

	/**2016.3.9����
	 * onCreate  �൱��Java�ɴֵ�main����
	 * ��java�ļ��У�һ���������п�ʼ�ͽ��������������������ڵ� 
	 */
	/**��������*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreate");
		music  =new Music(this);//��ʼ��������
		sound  = new Sound(this);//��ʼ����Ч
		
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
			//��ʼ��SharedPreferences
			/**getSharedPreferences(name, mode);
			 * name  xml�ļ�����
			 * mode  xmlģʽ
			 */
			/*mode  xmlģʽ
			 * @see #MODE_PRIVATE
			 *    MODE_PRIVATE   �������ĳ������ 
			 * @see #MODE_WORLD_READABLE
			 *    MODE_WORLD_READABLE  �����ĳ�������ļ�
			 * @see #MODE_WORLD_WRITEABLE
			 *    MODE_WORLD_WRITEABLE  �����ĳ���Ը��ļ�����д��
			 * @see #MODE_MULTI_PROCESS
			 *    MODE_MULTI_PROCESS    ��ģʽ���ڶ��̲߳�������ʱ �̰߳�ȫ
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
		//editor.put(key, value);  ���ļ��д洢ֵ
		for (int i = 0; i < scores.length; i++) {
			for (int j = 0; j < scores[i].length; j++) {
				// �� xml �ļ���ͨ����ͬ��key  �洢��ͬ�ɷ���
				editor.putInt(""+i+j+"scores", scores[i][j]);
				editor.putString(""+i+j+"name", topNames[i][j]);

			}
		}
		//  commit  �ύ
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
	
	/**��ʼ����*/
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("onStart");
	}
	/**��������*/
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
	}
	//��ת����ͣ����
	public void toPause(){
		//Intent    ��ͼ,������ת��Ҫ
		Intent intent = new Intent();
		/*setClass(packageContext, cls);
		 * packageContext   Context  �����Ķ���
		 * Class<?> cls     ָ��ת���Ǹ�����
		 */
		//���ô�MainActivity ��ת��PauseActivity����
		intent.setClass(MainActivity.this, PauseActivity.class);
		//������ͼ��ת
		startActivity(intent);
	}

	//��һ�����������µ�ʱ��  ִ��
	// keyCode  ��ʾ���µİ���       ���ؼ����²�������ͣ����    Ҫ�� isBack����Ϊtrue
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*KeyEvent.KEYCODE_BACK   ϵͳ�ķ��ؼ�
		 * keyCode      �½��ĵ����
		 * ���������ϵͳ�ķ��ؼ����ʱ��ֱ����ת����������ͣ����
		 */
		if(keyCode == KeyEvent.KEYCODE_BACK){
			isBack = true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**��ͣ����*/
	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("onPause");
		//���isBackΪ�ٵ�ʱ��
		if(!isBack){
			toPause();
		}
		music.pauseMusic();


	}
	/**���¿�ʼ����*/
	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart");
		//������ͣ����
		isBack = false;//��ʼ��ʱ����ͣ�Ľ���ִ��
		music.playMusic();
	}
	/**ֹͣ����*/
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("onStop");

	}
	/**���ٷ���*/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy");
		music.stopMusic();
	}
}

