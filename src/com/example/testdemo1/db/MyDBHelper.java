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
	 * @param context   对象
	 * @param name      数据可名字   .db为数据库格式
	 * @param factory   游标工厂
	 * @param version   指数据库的版本
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
		/*创建数据库表
		 * create table user(  )
		 * id主键  字增的
		 * 姓名
		 * 分数
		 * 难度
		 * 名次
		 * 
		 */
		StringBuffer  sql = new StringBuffer();
		sql.append("create table ").append(table_name).append("(")
		.append("id integer primary key autoincrement,")
		.append("name text,").append("score integer,")
		.append("level integer,").append("top integer").append(")");
		db.execSQL(sql.toString());//执行创建表的语句
	}
	//数据库版本升级
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}