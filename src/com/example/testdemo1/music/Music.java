package com.example.testdemo1.music;
/**����������*/
import com.example.testdemo1.MainActivity;
import android.content.Context;
import android.media.MediaPlayer;
public class Music {
	/**�������ֲ���*/
	MediaPlayer   player;
	Context  mContext ;//���ڼ�����Դ����ȡ���������������Ϣ
	/**���췽��*/
	public Music(Context context) {
		this.mContext = context;
	}
	
	
	/**�������*/
	public  void addMusic(int musicResId){
		/*����һ�²�����
		 *         player = MediaPlayer.create(context, );
		 *                          context    �����Ķ���
		 *                          resid         ��Դid
		 */
		player = MediaPlayer.create(mContext, musicResId);
		//������ֲ������ж�
		try {
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//��������ѭ��
		player.setLooping(true);
		playMusic();
	}
	/**��������*/
	public  void  playMusic(){
		if(MainActivity.isAudio)
		if(player!=null)//��Ϊ������̳���MainActivity�࣬�������������û�����־ͻᱨ��
		if(!player.isPlaying()){
			player.start();
		}	
	}
	/**��ͣ����*/
	public void pauseMusic(){
		if(player!=null)//��Ϊ������̳���MainActivity�࣬�������������û�����־ͻᱨ��
		if(player.isPlaying()){
			player.pause();
		}	
	}
	/**ֹͣ����*/
	public void stopMusic(){
		if(player!=null)//��Ϊ������̳���MainActivity�࣬�������������û�����־ͻᱨ��
		if(player.isPlaying()){
			player.stop();
			//�ͷ���Դ
			player.release();
		}	
	}
}
