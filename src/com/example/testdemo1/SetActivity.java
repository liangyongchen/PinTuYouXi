package com.example.testdemo1;
import android.media.AudioManager;
import android.os.Bundle;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
public class SetActivity extends MainActivity implements OnClickListener{
	ImageView   ivSound;
	ImageView   ivSoundOff;
	ImageView   ivSoundOn;
	ImageView   ivSoundDown;
	ImageView   ivSoundUp;
	SeekBar         sbSoundShow;
	Button           btClear;
	Button           btFinish;
	/**����������*/
	AudioManager  am;
	/**������С*/
	int   vol;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		ivSound              =   (ImageView) findViewById(R.id.imageView1);
		ivSoundOff         =   (ImageView) findViewById(R.id.imageView2);
		ivSoundOn         =   (ImageView) findViewById(R.id.imageView3);
		ivSoundDown      =   (ImageView) findViewById(R.id.imageView4);
		ivSoundUp          =   (ImageView) findViewById(R.id.imageView5);
		sbSoundShow     = (SeekBar) findViewById(R.id.seekBar1);
		btClear                 =  (Button) findViewById(R.id.button1);
		btFinish               =  (Button) findViewById(R.id.button2);
		//�ڼ������д�����ť
		ivSoundOff.setOnClickListener(this);
		ivSoundOn.setOnClickListener(this);
		ivSoundDown.setOnClickListener(this);
		ivSoundUp.setOnClickListener(this);
		btClear.setOnClickListener(this);
		btFinish.setOnClickListener(this);

		//�������
		music.addMusic(R.raw.ban_xiao_ye_qu);
		//��ʼ������������
		am   =  (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//��ȡ��ǰ����
		vol  =  am.getStreamVolume(AudioManager.STREAM_MUSIC);
		//��ȡ�������ֵ
		int maxVol  =  am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		sbSoundShow.setMax(maxVol);//���������ֵ��seekBar1��ť
		sbSoundShow.setProgress(vol);//seekBar1��ť��ȡ��ǰ����

		/**����������ҳ��ʱ���޸Ķ�Ӧ������ͼƬ*/
		if(vol==0){
			if(!isAudio){
				ivSound.setBackgroundResource(R.drawable.audio_off);
			}
		}
		//��seekBar1��ť���ü�����
		OnSeekBarChangeListener  l = new  OnSeekBarChangeListener(){
			/*��һ������ʾ��ǰseekBar
			 * �ڶ�������ʾseekBar�ڱ仯��ֵ
			 * ������������ʾ�Ƿ����˲��������ı仯
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				vol = progress;//��ǰ�������ڶ�������ʾseekBar�ڱ仯��ֵ
				if(progress==0){//������
					isAudio = false;
					ivSound.setBackgroundResource(R.drawable.audio_off);
				}else{
					isAudio = true;
					music.playMusic();
					ivSound.setBackgroundResource(R.drawable.audio_on);
				}
				/*��һ������ʾ��������
				 * �ڶ�������ʾҪ���õ�ֵ
				 * ������������ʾ��ǣ����������ĸ�������
				 */
				am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);//����ϵͳ����
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {//��ʼ����ʱ����
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {//���������󷽷�

			}
		};
		sbSoundShow.setOnSeekBarChangeListener(l);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			//�������
			rank.delete();
			for (int i = 0; i < scores.length; i++) {
				for (int j = 0; j < scores[i].length; j++) {
					scores[i][j]=0;
					topNames[i][j]="";
				}
			}
			break;
		case  R.id.button2:
			finish();
			isBack = true;
			break;
		case  R.id.imageView2://������
			isAudio  =  true;
			ivSound.setBackgroundResource(R.drawable.audio_on);
			music.playMusic();
			break;
		case R.id.imageView3://�����ر�
			isAudio = false;
			ivSound.setBackgroundResource(R.drawable.audio_off);
			music.pauseMusic();
			break;
		case R.id.imageView4://��������
			if(vol>0){
				vol--;
			}
			am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
			sbSoundShow.setProgress(vol);
			if(vol<=0){
				isAudio  = false;
				ivSound.setBackgroundResource(R.drawable.audio_off);
			}
			break;
		case R.id.imageView5://��������
			if(vol<sbSoundShow.getMax()){
				vol++;
				isAudio  = true;
				ivSound.setBackgroundResource(R.drawable.audio_on);
			}
			am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
			sbSoundShow.setProgress(vol);
			break;
		}
		sound.play(R.raw.a2);
	}
	//������ť��������ʵ�尴ť
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//����+
		if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
			vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			sbSoundShow.setProgress(vol);
			isAudio = true;
			ivSound.setBackgroundResource(R.drawable.audio_on);
		}
		//���� -
		if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
			vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			sbSoundShow.setProgress(vol);
			if(vol == 0){
				isAudio = false;
				ivSound.setBackgroundResource(R.drawable.audio_off);
			}
		}	
		return super.onKeyUp(keyCode, event);
	}
}
