package com.example.testdemo1.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 主要用于操作数据库数据
 * @author Administrator
 */
public class Rank {

	MyDBHelper  dbHelper;
	Context mContext;
	SQLiteDatabase db;
	public Rank(Context mContext){
		this.mContext = mContext;
		dbHelper  =  new MyDBHelper(mContext, "user.db");
		//getWritableDatabase();//具有写的能力也有读的能力,但是读取数据速度不较慢
		//getReadableDatabase();//具有读的能力，没有写的能力
		db = dbHelper.getWritableDatabase();//具有写的能力也有读的能力	
	}
	public void insert(int [][] scores,String[][] toNames){//曾
		//健值  key  value
		//insert into user () values()
		//健值 方便数据库保存数据
		ContentValues  values  =  new ContentValues();
		for (int i = 0; i < toNames.length; i++) {
			for (int j = 0; j < toNames[i].length; j++) {
				values.put("name", toNames[i][j]);
				values.put("score", scores[i][j]);
				values.put("level", i);
				values.put("top", j);
				//table 表名
				db.insert(dbHelper.table_name, null, values);			
			}
		}	
	}
	public void delete(){//删
		/*  delete(table, whereClause, whereArgs);
		 *   table         表名
		 *   whereClause   条件
		 *   whereArgs     参数
		 *   delete user where id = 1
		 */
		db.delete(dbHelper.table_name, null, null);//没有条件的删除
		//db.delete(dbHelper.table_name, "id=?", new String[]{"1"});//有天骄的删除
	}
	public void upDate(int [][] scores,String[][] topNames){//改
		delete();
		insert(scores, topNames);	
	}
	@SuppressLint("NewApi")
	public void select(int [][] scores,String[][] topNames){//查
		//方法一
		//db.query(false, dbHelper.table_name, new String[]{"name","score","level","top"},
		//		                          null, null, null, null, null, null, null);
		//方法二
		//Cursor cursor = db.rawQuery("select * from "+dbHelper.table_name, null);//这里的select*from"+dbHelper.table_name是错误的,没有用空格空开
		Cursor cursor = db.rawQuery("select * from user", null);//正确的
		//将游标移动到下一行
		//返回值 boolean 类型  指下一行是否有数据
		//cursor.moveToNext();
		while(cursor.moveToNext()){
			//cursor.getInt(cursor.getColumnIndex("level"));  获取难度对应的值为表中的第几列
			int level=cursor.getInt(cursor.getColumnIndex("level"));
			int top = cursor.getInt(cursor.getColumnIndex("top"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int score = cursor.getInt(cursor.getColumnIndex("score"));	
			scores[level][top]  = score;
			topNames[level][top] = name;	
		}	
	}
}

