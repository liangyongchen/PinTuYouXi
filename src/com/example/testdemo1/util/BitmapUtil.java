package com.example.testdemo1.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	
	public static Bitmap scaleBitmap(Context context,int resId,int width,int height){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;
		
		
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2;
		bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
		return bitmap;
	}
	
}
