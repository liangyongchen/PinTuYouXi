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
	/**音量管理类*/
	AudioManager  am;
	/**音量大小*/
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
		//在监听器中创建按钮
		ivSoundOff.setOnClickListener(this);
		ivSoundOn.setOnClickListener(this);
		ivSoundDown.setOnClickListener(this);
		ivSoundUp.setOnClickListener(this);
		btClear.setOnClickListener(this);
		btFinish.setOnClickListener(this);

		//添加音乐
		music.addMusic(R.raw.ban_xiao_ye_qu);
		//初始化音量管理类
		am   =  (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		//获取当前音量
		vol  =  am.getStreamVolume(AudioManager.STREAM_MUSIC);
		//获取音量最大值
		int maxVol  =  am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		sbSoundShow.setMax(maxVol);//把音量最大值给seekBar1按钮
		sbSoundShow.setProgress(vol);//seekBar1按钮获取当前音量

		/**当进入设置页面时，修改对应的声音图片*/
		if(vol==0){
			if(!isAudio){
				ivSound.setBackgroundResource(R.drawable.audio_off);
			}
		}
		//给seekBar1按钮设置监听器
		OnSeekBarChangeListener  l = new  OnSeekBarChangeListener(){
			/*第一参数表示当前seekBar
			 * 第二参数表示seekBar在变化的值
			 * 第三个参数表示是否由人操作产生的变化
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				vol = progress;//当前音量，第二参数表示seekBar在变化的值
				if(progress==0){//调静音
					isAudio = false;
					ivSound.setBackgroundResource(R.drawable.audio_off);
				}else{
					isAudio = true;
					music.playMusic();
					ivSound.setBackgroundResource(R.drawable.audio_on);
				}
				/*第一参数表示音量类型
				 * 第二参数表示要设置的值
				 * 第三个参数表示标记，调节音量的附带动作
				 */
				am.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);//设置系统音量
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {//开始触摸时方法
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {//触摸结束后方法

			}
		};
		sbSoundShow.setOnSeekBarChangeListener(l);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			//清空数据
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
		case  R.id.imageView2://音量打开
			isAudio  =  true;
			ivSound.setBackgroundResource(R.drawable.audio_on);
			music.playMusic();
			break;
		case R.id.imageView3://音量关闭
			isAudio = false;
			ivSound.setBackgroundResource(R.drawable.audio_off);
			music.pauseMusic();
			break;
		case R.id.imageView4://音量减少
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
		case R.id.imageView5://音量增加
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
	//监听按钮，音量的实体按钮
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//音量+
		if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
			vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			sbSoundShow.setProgress(vol);
			isAudio = true;
			ivSound.setBackgroundResource(R.drawable.audio_on);
		}
		//音量 -
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
