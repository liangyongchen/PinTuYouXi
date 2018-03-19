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

	ListView  lv_help;//��������
	MyListViewAdapter  adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		lv_help  =  (ListView) findViewById(R.id.lv_help);//��ť����
		/*1.����        2.�����ʾ      3.��������
		 * 4.������Adapater      5. ListAdapter�ӿ�    BaseAdapter�ӿ�
		 * 6.lv_help.setAdapter(adapter);
		 */
		//����������
		 adapter  =  new  MyListViewAdapter(HelpActivity.this);
		//����������
		lv_help.setAdapter(adapter);
		lv_help.setOnItemClickListener(this);
		music.addMusic(R.raw.coup_de_coeur);
	}
	/*��һ��������ʾ�б����
	 * �ڶ��Ͳ�����ʾ������Ŀ
	 * ������������ʾ��������±�
	 * ���ĸ�������ʾ�������и����±귵�ص�id
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		adapter.setMessage(position);//�ɼ��Է��������ڴ�����
		sound.play(R.raw.a2);
		
	}
}

/**�ӿ�BaseAdapter����4������*/
class MyListViewAdapter extends BaseAdapter{
	//1.����
	String[] titles  =  {"��Ϸ����","��Ϸϵͳ"};
	String[] message  ={"һ����������" +
			"1.���Ͻ�Ϊ����ͼƬ����Ϸ���̿�����Ϊ�ο�;	\n" +
			"2.���Ͻ����򣬿�����Ϸ��ʼ����ͣ; ���Ͻ�����ͬʱҲ�ܲ鿴��Ϸ״̬;\n" +
			"3.��Ϸ����,���ͼ��,ͼ�����հ������ƶ�.\n" +
			" �����Ѷȼ��趨\n" +
			"1.��Ϸ�а��ֻ��˵���:����ѡ����ѡ����Ϸ�Ѷȼ�����;�������ѡ�����з�Ϊ�����ȼ���\n" +
			"�����Ѷȣ�\n" +
			"3*3ƴͼ����;�м��Ѷȣ�\n" +
			"4*4ƴͼ����;�߼��Ѷȣ�\n" +
			"5*5ƴͼ����.\n" +
			"2.��Ϸ�а��ֻ����ؼ�:�����˵���ѡ���˳���Ϸ�������˵�.\n",
	"���������������������׷�ǰ񡿶Ի�����ʾ�����Ѷȵĵ�һ�������Ϸ���õ���Ϸʱ�䣬�㡾ȷ�����������˵����㡾��ϸ�����롾ͳ�ơ�ҳ�桾����ͳ�ơ�ҳ����ʾ�����Ѷȵ�ǰʮ�������Ϣ��������Ϸ��������Ϸ����ʱ����������."};

	/** ����������ϸ���ݵĴ򿪺͹ر�   */
	boolean []    bs=  new boolean  [titles.length];
	Context  mContext;
	LayoutInflater  inflater;
	//���췽��  ��Ҫ���������Ķ���
	public MyListViewAdapter(Context  context){
		this.mContext  = context;
		inflater  = LayoutInflater.from(context);
	}

	//2.��������
	//��ȡ��������    ��ȡ�б�Ҫ��ʾ����������
	@Override
	public int getCount() {

		return 2;// ��ȡ�б�Ҫ��ʾ����������,һ��Ҫ��������������ʾ
	}

	//��ȡ��Ŀ����      �����б��е��±��ȡ��ʵ����
	@Override
	public Object getItem(int position) {

		return null;
	}

	//��ȡ��Ŀid����     �����б��е��±��ȡ��ʵ���ݶ�Ӧ��id
	@Override
	public long getItemId(int position) {

		return 0;
	}

	//��ȡ���ַ���    ��ȡ�б���ÿ����Ŀ���������ʾ �Լ���ʾ������
	/**getView(int position, View convertView, ViewGroup parent)
	 * position               ��ʾҪ��ʾ�ĵڼ�������  ��һ��Ϊ0
	 * convertView     ָ�ڻ�������Ĳ���
	 * parent                ָ�������ø����������б�
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//1.�������Ĳ��ּ���
		//2.����Ӧ�Ĳ����ϵ��齨���ı��޸�
		//3.�����úõĲ��ַ���
		View view = null;
		if(convertView!=null){
			view  =convertView;
		}else{
			view = (LinearLayout) inflater.inflate(R.layout.item_list_help, null);//1.�������Ĳ��ּ���	
		}
		//�����ı���������Ĳ����ڲ�
		TextView  tv_title  =  (TextView) view.findViewById(R.id.tv_title);
		TextView  tv_message  =  (TextView) view.findViewById(R.id.tv_message);
		
		int isVisble  ;
		/* @param visibility One of {@link #VISIBLE}, {@link #INVISIBLE}, or {@link #GONE}.
		 * @link #VISIBLE                �ɼ�
		 * @link #INVISIBLE            �����ز��ɼ�
		 * @link #GONE                      ���غͲ��ɼ�
		 */
		if(bs[position]){
			isVisble  = view.VISIBLE;//�ɼ�
		}else{
			isVisble  =  view.GONE;//���ɼ�
		}
		tv_message.setVisibility(isVisble);//�ɼ���
		tv_title.setText(titles[position]);
		tv_message.setText(message[position]);
		
	
		return view;//���أ������úõĲ��ַ���
	}
	
	//������Ŀ�Ŀɼ��ԺͲ��ɼ���
	public void setMessage(int position){
		bs[position]  =! bs[position];
		//ˢ���б�
		notifyDataSetChanged();
	}

}
