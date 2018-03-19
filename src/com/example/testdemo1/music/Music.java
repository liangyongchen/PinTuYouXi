package com.example.testdemo1.music;
/**背景音乐类*/
import com.example.testdemo1.MainActivity;
import android.content.Context;
import android.media.MediaPlayer;
public class Music {
	/**用于音乐播放*/
	MediaPlayer   player;
	Context  mContext ;//用于记载资源，获取整个程序的配置信息
	/**构造方法*/
	public Music(Context context) {
		this.mContext = context;
	}
	
	
	/**添加音乐*/
	public  void addMusic(int musicResId){
		/*创建一月播放器
		 *         player = MediaPlayer.create(context, );
		 *                          context    上下文对象
		 *                          resid         资源id
		 */
		player = MediaPlayer.create(mContext, musicResId);
		//添加音乐播放器判断
		try {
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//设置音乐循环
		player.setLooping(true);
		playMusic();
	}
	/**播放音乐*/
	public  void  playMusic(){
		if(MainActivity.isAudio)
		if(player!=null)//因为其他类继承了MainActivity类，如果其他类里面没有音乐就会报错
		if(!player.isPlaying()){
			player.start();
		}	
	}
	/**暂停音乐*/
	public void pauseMusic(){
		if(player!=null)//因为其他类继承了MainActivity类，如果其他类里面没有音乐就会报错
		if(player.isPlaying()){
			player.pause();
		}	
	}
	/**停止音乐*/
	public void stopMusic(){
		if(player!=null)//因为其他类继承了MainActivity类，如果其他类里面没有音乐就会报错
		if(player.isPlaying()){
			player.stop();
			//释放资源
			player.release();
		}	
	}
}
