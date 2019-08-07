package com.hf.hf_smartcloud.ui.activity.facility;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddaddressActivity;
import com.hf.hf_smartcloud.ui.zxing.QrCodeActivity;

public class AddSbActivity extends BaseActivity implements View.OnClickListener {
    private TextView alert_edit;
    private  LinearLayout ll_erwcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsb);
        initTitle();
        initView();
        // 请选择您的初始化方式
//        initAccessToken();
//        initAccessTokenWithAkSk();
    }
    /**
     * 以license文件方式初始化
     */
//    private void initAccessToken() {
//        OCR.getInstance(this).initAccessToken(new OnResultListener<AccessToken>() {
//            @Override
//            public void onResult(AccessToken accessToken) {
//                String token = accessToken.getAccessToken();
//                hasGotToken = true;
//            }
//
//            @Override
//            public void onError(OCRError error) {
//                error.printStackTrace();
//                alertText("licence方式获取token失败", error.getMessage());
//            }
//        }, getApplicationContext());
//    }

    /**
     * 用明文ak，sk初始化
     */
//    private void initAccessTokenWithAkSk() {
//        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
//            @Override
//            public void onResult(AccessToken result) {
//                String token = result.getAccessToken();
//                hasGotToken = true;
//            }
//
//            @Override
//            public void onError(OCRError error) {
//                error.printStackTrace();
//                alertText("AK，SK方式获取token失败", error.getMessage());
//            }
//        }, getApplicationContext(),  "CgGxziykqgMPXB9A01vDhCVC", "0hGHgiPvMskIhmdH2pLK9o33gGQwl52p");
//    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_sancode =  findviewByid(R.id.btn_sancode);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("添加设备");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        btn_sancode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO 文字识别
//                Intent intent = new Intent(AddSbActivity.this, CameraActivity.class);
//                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
//                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
//                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
//                        CameraActivity.CONTENT_TYPE_GENERAL);
//                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
//            }
//        });
    }

    private void initView() {
        ll_erwcode = findviewByid(R.id.ll_erwcode);
        ll_erwcode.setOnClickListener(this);
        alert_edit = findviewByid(R.id.alert_edit);
        alert_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(AddSbActivity.this);
                new AlertDialog.Builder(AddSbActivity.this).setTitle("请输入授权码").
                        setIcon(android.R.drawable.sym_def_app_icon).
                        setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消",null).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_erwcode:
                //二维码链接
                new IntentIntegrator(this).setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                        // 扫码的类型,可选：一维码，二维码，一/二维码 //.setPrompt("请对准二维码")// 设置提示语
                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
                        .setCaptureActivity(QrCodeActivity.class)//自定义扫码界面
                        .initiateScan();
                break;
        }
    }
}

