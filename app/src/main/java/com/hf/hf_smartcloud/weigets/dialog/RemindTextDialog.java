package com.hf.hf_smartcloud.weigets.dialog;
 

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.hf.hf_smartcloud.R;
/**
 * 
 * Create custom Dialog windows for your application
 * Custom dialogs rely on custom layouts wich allow you to 
 * create and use your own look & feel.
 * 
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * <a href="http://my.oschina.net/arthor" target="_blank" rel="nofollow">@author</a> antoine vianey
 *
 */
public class RemindTextDialog extends Dialog {
 
    public RemindTextDialog(Context context, int theme) {
        super(context, theme);
    }
 
    public RemindTextDialog(Context context) {
        super(context);
    }
 
    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {
 
        private Context context;
        private String title;
        private String message;
        private OnCustomDialogListener customDialogListener;
        private int btn_num;
        private static TextView msgTextView;
        private String btnStr = "";
        public Builder(Context context, String title, String message, int btn_num, OnCustomDialogListener customListener) {
            this.context = context;
            this.title = title;
            this.message = message;
            this.customDialogListener = customListener;
            this.btn_num = btn_num;
        }
        public Builder(Context context, String title, String message, String buttnStr, int btn_num, OnCustomDialogListener customListener) {
            this.context = context;
            this.title = title;
            this.message = message;
            this.customDialogListener = customListener;
            this.btn_num = btn_num;
            this.btnStr = buttnStr;
        }
     // ����dialog�Ļص��¼�
    	public interface OnCustomDialogListener {
    		void back(String str);
    	}
        public void setMsgText(String text){
            Log.d("zcm===", "准备显示的信息为==="+text);
            msgTextView.setText(text);
        }
        /**
         * Create the custom dialog
         */
        public RemindTextDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final RemindTextDialog dialog = new RemindTextDialog(context, 
            		R.style.Dialog);
            dialog.setCanceledOnTouchOutside(true);
            View layout = inflater.inflate(R.layout.dialog_remind_text, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            TextView titleTxt = (TextView) layout.findViewById(R.id.title);
            Button okBtn = (Button) layout.findViewById(R.id.positiveButton);
            Button cancle = (Button) layout.findViewById(R.id.negativeButton);
            msgTextView = (TextView) layout.findViewById(R.id.message);
            ImageView line_img = (ImageView)layout.findViewById(R.id.dialog_line_img);
            if(btn_num == 1){
            	if(!btnStr.equals("")){
            		okBtn.setText(btnStr);
            	}
            	line_img.setVisibility(View.GONE);
            	cancle.setVisibility(View.GONE);
            }else{
            	line_img.setVisibility(View.VISIBLE);
            	cancle.setVisibility(View.VISIBLE);
            }
            msgTextView.setText(message);
            titleTxt.setText(title);
            okBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	customDialogListener.back("OK");
                	dialog.cancel();
                }
            });
            cancle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	dialog.cancel();
                }
            });
            dialog.setContentView(layout);
            return dialog;
        }
 
    }
 
}