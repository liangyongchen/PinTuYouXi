package com.example.testdemo1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HelpActivity extends MainActivity implements  OnItemClickListener{

	ListView  lv_help;//按键对象
	MyListViewAdapter  adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		lv_help  =  (ListView) findViewById(R.id.lv_help);//按钮加载
		/*1.内容        2.如何显示      3.内容数量
		 * 4.适配器Adapater      5. ListAdapter接口    BaseAdapter接口
		 * 6.lv_help.setAdapter(adapter);
		 */
		//创建适配器
		 adapter  =  new  MyListViewAdapter(HelpActivity.this);
		//设置适配器
		lv_help.setAdapter(adapter);
		lv_help.setOnItemClickListener(this);
		music.addMusic(R.raw.coup_de_coeur);
	}
	/*第一个参数表示列表组件
	 * 第二和参数表示整个条目
	 * 第三个参数表示被点击的下标
	 * 第四个参数表示适配器中根据下标返回的id
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		adapter.setMessage(position);//可见性方法调用在此启动
		sound.play(R.raw.a2);
		
	}
}

/**接口BaseAdapter，有4个方法*/
class MyListViewAdapter extends BaseAdapter{
	//1.内容
	String[] titles  =  {"游戏操作","游戏系统"};
	String[] message  ={"一、基本操作" +
			"1.左上角为对照图片，游戏过程可以作为参考;	\n" +
			"2.右上角区域，控制游戏开始和暂停; 右上角区域，同时也能查看游戏状态;\n" +
			"3.游戏区域,点击图块,图块会向空白区域移动.\n" +
			" 二、难度及设定\n" +
			"1.游戏中按手机菜单键:弹出选项中选择游戏难度及控制;点击即可选择，其中分为三个等级：\n" +
			"初级难度：\n" +
			"3*3拼图界面;中级难度：\n" +
			"4*4拼图界面;高级难度：\n" +
			"5*5拼图界面.\n" +
			"2.游戏中按手机返回键:弹出菜单中选择退出游戏返回主菜单.\n",
	"点击【积分排名】弹出【追星榜】对话框，显示各个难度的第一名完成游戏所用的游戏时间，点【确定】返回主菜单，点【详细】进入【统计】页面【排名统计】页面显示各个难度的前十名玩家信息：包括游戏排名，游戏过关时间和玩家名称."};

	/** 用来控制详细内容的打开和关闭   */
	boolean []    bs=  new boolean  [titles.length];
	Context  mContext;
	LayoutInflater  inflater;
	//构造方法  需要传入上下文对象
	public MyListViewAdapter(Context  context){
		this.mContext  = context;
		inflater  = LayoutInflater.from(context);
	}

	//2.内容数量
	//获取数量方法    获取列表要显示的内容数量
	@Override
	public int getCount() {

		return 2;// 获取列表要显示的内容数量,一定要有数量，否则不显示
	}

	//获取条目方法      根据列表中的下表获取真实数据
	@Override
	public Object getItem(int position) {

		return null;
	}

	//获取条目id方法     根据列表中的下表获取真实数据对应的id
	@Override
	public long getItemId(int position) {

		return 0;
	}

	//获取布局方法    获取列表中每个条目具体如何显示 以及显示的内容
	/**getView(int position, View convertView, ViewGroup parent)
	 * position               表示要显示的第几条内容  第一个为0
	 * convertView     指在缓冲区域的布局
	 * parent                指的是设置该适配器的列表
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//1.将创建的布局加载
		//2.将对应的布局上的组建的文本修改
		//3.将设置好的布局返回
		View view = null;
		if(convertView!=null){
			view  =convertView;
		}else{
			view = (LinearLayout) inflater.inflate(R.layout.item_list_help, null);//1.将创建的布局加载	
		}
		//由于文本是在上面的布局内部
		TextView  tv_title  =  (TextView) view.findViewById(R.id.tv_title);
		TextView  tv_message  =  (TextView) view.findViewById(R.id.tv_message);
		
		int isVisble  ;
		/* @param visibility One of {@link #VISIBLE}, {@link #INVISIBLE}, or {@link #GONE}.
		 * @link #VISIBLE                可见
		 * @link #INVISIBLE            不隐藏不可见
		 * @link #GONE                      隐藏和不可见
		 */
		if(bs[position]){
			isVisble  = view.VISIBLE;//可见
		}else{
			isVisble  =  view.GONE;//不可见
		}
		tv_message.setVisibility(isVisble);//可见性
		tv_title.setText(titles[position]);
		tv_message.setText(message[position]);
		
	
		return view;//返回，将设置好的布局返回
	}
	
	//设置条目的可见性和不可见性
	public void setMessage(int position){
		bs[position]  =! bs[position];
		//刷新列表
		notifyDataSetChanged();
	}

}
