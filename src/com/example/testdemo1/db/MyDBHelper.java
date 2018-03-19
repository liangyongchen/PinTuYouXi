package com.example.testdemo1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** 
 *SQLiteOpenHelper
 */
public class MyDBHelper extends SQLiteOpenHelper{
	public static final int VERSION  =1;
	public String table_name = "user";
	/** 
	 * SQLite Explorer
	 * @param context   ����
	 * @param name      ���ݿ�����   .dbΪ���ݿ��ʽ
	 * @param factory   �α깤��
	 * @param version   ָ���ݿ�İ汾
	 */
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	public MyDBHelper(Context context, String name) {
		this(context, name,null,VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		/*�������ݿ��
		 * create table user(  )
		 * id����  ������
		 * ����
		 * ����
		 * �Ѷ�
		 * ����
		 * 
		 */
		StringBuffer  sql = new StringBuffer();
		sql.append("create table ").append(table_name).append("(")
		.append("id integer primary key autoincrement,")
		.append("name text,").append("score integer,")
		.append("level integer,").append("top integer").append(")");
		db.execSQL(sql.toString());//ִ�д���������
	}
	//���ݿ�汾����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}