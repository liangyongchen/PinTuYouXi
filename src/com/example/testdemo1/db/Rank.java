package com.example.testdemo1.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * ��Ҫ���ڲ������ݿ�����
 * @author Administrator
 */
public class Rank {

	MyDBHelper  dbHelper;
	Context mContext;
	SQLiteDatabase db;
	public Rank(Context mContext){
		this.mContext = mContext;
		dbHelper  =  new MyDBHelper(mContext, "user.db");
		//getWritableDatabase();//����д������Ҳ�ж�������,���Ƕ�ȡ�����ٶȲ�����
		//getReadableDatabase();//���ж���������û��д������
		db = dbHelper.getWritableDatabase();//����д������Ҳ�ж�������	
	}
	public void insert(int [][] scores,String[][] toNames){//��
		//��ֵ  key  value
		//insert into user () values()
		//��ֵ �������ݿⱣ������
		ContentValues  values  =  new ContentValues();
		for (int i = 0; i < toNames.length; i++) {
			for (int j = 0; j < toNames[i].length; j++) {
				values.put("name", toNames[i][j]);
				values.put("score", scores[i][j]);
				values.put("level", i);
				values.put("top", j);
				//table ����
				db.insert(dbHelper.table_name, null, values);			
			}
		}	
	}
	public void delete(){//ɾ
		/*  delete(table, whereClause, whereArgs);
		 *   table         ����
		 *   whereClause   ����
		 *   whereArgs     ����
		 *   delete user where id = 1
		 */
		db.delete(dbHelper.table_name, null, null);//û��������ɾ��
		//db.delete(dbHelper.table_name, "id=?", new String[]{"1"});//���콾��ɾ��
	}
	public void upDate(int [][] scores,String[][] topNames){//��
		delete();
		insert(scores, topNames);	
	}
	@SuppressLint("NewApi")
	public void select(int [][] scores,String[][] topNames){//��
		//����һ
		//db.query(false, dbHelper.table_name, new String[]{"name","score","level","top"},
		//		                          null, null, null, null, null, null, null);
		//������
		//Cursor cursor = db.rawQuery("select * from "+dbHelper.table_name, null);//�����select*from"+dbHelper.table_name�Ǵ����,û���ÿո�տ�
		Cursor cursor = db.rawQuery("select * from user", null);//��ȷ��
		//���α��ƶ�����һ��
		//����ֵ boolean ����  ָ��һ���Ƿ�������
		//cursor.moveToNext();
		while(cursor.moveToNext()){
			//cursor.getInt(cursor.getColumnIndex("level"));  ��ȡ�Ѷȶ�Ӧ��ֵΪ���еĵڼ���
			int level=cursor.getInt(cursor.getColumnIndex("level"));
			int top = cursor.getInt(cursor.getColumnIndex("top"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int score = cursor.getInt(cursor.getColumnIndex("score"));	
			scores[level][top]  = score;
			topNames[level][top] = name;	
		}	
	}
}

