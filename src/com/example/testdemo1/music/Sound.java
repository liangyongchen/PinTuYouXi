package com.example.testdemo1.music;
import java.util.HashMap;
import com.example.testdemo1.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
/**播放音效类*/
public class Sound {
	Context  mContext;
	/**音效池*/
	SoundPool  pool;
	/**集合存储音效*/
	HashMap<Integer, Integer>  map;
	public Sound(Context context) {
		this.mContext = context;
		//初始化音效
		pool   =  new  SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		//初始化map集合音效
		map  = new   HashMap<Integer, Integer>();
		/*SoundPool(maxStreams, streamType, srcQuality);
		 * maxStreams      能存最多音效数量
		 * maxStreams      流类型     AudioManager.STREAM_MUSIC 表示播放音乐类型
		 * srcQuality          音乐比特率        没什么用处，一般默认为0
		 */
		
		//添加音效
		int key1 = pool.load(context, R.raw.a2, 1);
		/*load(context, resId, priority);
		 * context         上下文对象
		 * resId              资源id
		 * priority        优先级     要大于1的整数
		 */
		
		//音效加载保存
		map.put( R.raw.a2, key1);
	}
	/**播放方法*/
	public  void play(int resId){

		int key = map.get(resId);
		pool.play(key, 1, 1, 1, 0, 1);
		/*
		 * soundID                     音效ID
		 * leftVolume               音量       左声道
		 * rightVolume           音量       右声道
		 * priority                     优先级
		 * loop                            循环
		 * rate                            音量          0.5-2
		 */
	}
}
