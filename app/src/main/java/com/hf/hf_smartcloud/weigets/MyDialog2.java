package com.hf.hf_smartcloud.weigets;


import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;

public class MyDialog2 {

    Button button;
    TextView textView;
    Button button2;
    AlertDialog alertDialog;

    private DialogButtonClick mClick;

    public interface DialogButtonClick {
        void cilckComfirmButton(View view);
        void cilckCancleButton(View view);
    }

    public void buttonClickEvent(DialogButtonClick bc){
        if(bc != null){
            mClick = bc;
            cilckEvent();
        }
    }



    public void initDialog(Activity activity, String titleString) {
        alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.activity_dialog);

        textView = (TextView)window.findViewById(R.id.textView);
        button = (Button)window.findViewById(R.id.button) ;
        button2 = (Button)window.findViewById(R.id.button2) ;

        textView.setText(titleString);

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public void cilckEvent(){
        if(button != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    mClick.cilckCancleButton(v);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    mClick.cilckComfirmButton(v);
                }
            });

        }
    }

}