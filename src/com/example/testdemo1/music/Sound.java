package com.example.testdemo1.music;
import java.util.HashMap;
import com.example.testdemo1.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
/**������Ч��*/
public class Sound {
	Context  mContext;
	/**��Ч��*/
	SoundPool  pool;
	/**���ϴ洢��Ч*/
	HashMap<Integer, Integer>  map;
	public Sound(Context context) {
		this.mContext = context;
		//��ʼ����Ч
		pool   =  new  SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		//��ʼ��map������Ч
		map  = new   HashMap<Integer, Integer>();
		/*SoundPool(maxStreams, streamType, srcQuality);
		 * maxStreams      �ܴ������Ч����
		 * maxStreams      ������     AudioManager.STREAM_MUSIC ��ʾ������������
		 * srcQuality          ���ֱ�����        ûʲô�ô���һ��Ĭ��Ϊ0
		 */
		
		//�����Ч
		int key1 = pool.load(context, R.raw.a2, 1);
		/*load(context, resId, priority);
		 * context         �����Ķ���
		 * resId              ��Դid
		 * priority        ���ȼ�     Ҫ����1������
		 */
		
		//��Ч���ر���
		map.put( R.raw.a2, key1);
	}
	/**���ŷ���*/
	public  void play(int resId){

		int key = map.get(resId);
		pool.play(key, 1, 1, 1, 0, 1);
		/*
		 * soundID                     ��ЧID
		 * leftVolume               ����       ������
		 * rightVolume           ����       ������
		 * priority                     ���ȼ�
		 * loop                            ѭ��
		 * rate                            ����          0.5-2
		 */
	}
}
