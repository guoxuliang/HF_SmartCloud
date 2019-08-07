package com.hf.hf_smartcloud.weigets;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

/**
 * @类名称: MyDialog
 * @类描述: TODO(自定义Dialog)
 * @作者 fengxian
 * @日期 2013-9-6 下午1:20:44
 *
 */
public class MyDialog extends Dialog {
    Context context;

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.addcase_sending_dialog);
    }

}
