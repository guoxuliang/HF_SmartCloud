package com.hf.hf_smartcloud.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/** 
 * @ClassName: DimenUtils 
 * @Description: TODO(����ת��������) 
 * @author hjd 
 * @date 2015-10-22 ����9:10:15 
 */

public class DimenUtils {

	 public static int sp2px(Context context, float spValue) {
	        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	        return (int) (spValue * fontScale + 0.5f);
	    }

	    public static int px2sp(Context context, float pxValue) {
	        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	        return (int) (pxValue / fontScale + 0.5f);
	    }

	    public static int dip2px(Context context, int dipValue) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (dipValue * scale + 0.5f);
	    }

	    public static int px2dip(Context context, float pxValue) {
	        final float scale = context.getResources().getDisplayMetrics().density;
	        return (int) (pxValue / scale + 0.5f);
	    }

	    /** ��ȡ��Ļ�Ŀ�� */
		public final static int getWindowsWidth(Context context) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			return dm.widthPixels;
		}
		
		/** ��ȡ��Ļ�ĸ߶� */
		public final static int getWindowsHight(Context context) {
			WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			return dm.heightPixels;
		}
}
