package com.hf.hf_smartcloud.ui.activity.me;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.SelectTradeEntity;
import com.hf.hf_smartcloud.entity.SubmitEditorEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.http.HttpsTrustManager;
import com.hf.hf_smartcloud.utils.SignUtil;
import com.hf.hf_smartcloud.views.DateUtils;
import com.hf.hf_smartcloud.weigets.BitmapFileSetting;
import com.hf.hf_smartcloud.weigets.PhotoUtils;
import com.hf.hf_smartcloud.weigets.ToastHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditDataActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edtPhoto)
    CircleImageView edtPhoto;
    @BindView(R.id.etNickname)
    TextView etNickname;
    @BindView(R.id.llNickname)
    LinearLayout llNickname;
    @BindView(R.id.llSex)
    LinearLayout llSex;
    @BindView(R.id.etSri)
    TextView etSri;
    @BindView(R.id.llBirthday)
    LinearLayout llBirthday;
    @BindView(R.id.tvCityt)
    TextView tvCityt;
    @BindView(R.id.llCityt)
    LinearLayout llCityt;
    @BindView(R.id.etIndustry)
    TextView etIndustry;
    @BindView(R.id.llIndustry)
    LinearLayout llIndustry;
    @BindView(R.id.etCompany)
    TextView etCompany;
    @BindView(R.id.llCompany)
    LinearLayout llCompany;
    @BindView(R.id.btnSubmitEdit)
    Button btnSubmitEdit;
    @BindView(R.id.lll)
    LinearLayout lll;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.femle)
    RadioButton femle;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.show)
    TextView show;
    @BindView(R.id.tab)
    TableLayout tab;


    // 声明PopupWindow
    private PopupWindow popupWindow;
    // 声明PopupWindow对应的视图
    private View popupView;
    // 声明平移动画
    private TranslateAnimation animation;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    private SubmitEditorEntity submitEditorEntity;
    private SelectTradeEntity selectTradeEntity;
    private android.app.AlertDialog alertDialog2; //单选框
    private String[] arr;
    private String industry="餐饮业";

    private String date = "";
    private SimpleDateFormat format;
    private Calendar calendar;
    private String file="";
    private String time="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        edtPhoto.setOnClickListener(this);//头像
        etNickname.setOnClickListener(this);//昵称
        etSri.setOnClickListener(this);//生日;
        btnSubmitEdit.setOnClickListener(this);//提交
        etIndustry.setOnClickListener(this);//行业
        etCompany.setOnClickListener(this);//公司名称
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                String tip = checkedId == R.id.male ? "男" : "女";
                show.setVisibility(View.VISIBLE);
                show.setText(tip);

//在这里同时可以根据小组定义数据传递到服务器；
                if (checkedId == R.id.male) {
                    Toast.makeText(getApplicationContext(), "nan你选择了" + tip, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "nv你选择了" + tip, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtPhoto:
                changeIcon(v);
                lightoff();
                break;
            case R.id.etNickname:
                showInputDialog("nickname", "请输入昵称");
                break;
            case R.id.etSri:
                showDate();
                break;
            case R.id.etIndustry:
                SelectTrade(v);
                break;
            case R.id.etCompany:
                showInputDialog("gsname", "请输入公司名称");
                break;
            case R.id.btnSubmitEdit:
                file = String.valueOf(cropImageUri);
                String nickName="";  nickName= etNickname.getText().toString().trim();
//                String timec = DateUtils2.dataOne(time);
                String industry=""; industry = etIndustry.getText().toString().trim();
                String company ="";company = etCompany.getText().toString().trim();
                if(time==null){
                    showToast("生日为空");
                }
                submitEditor(nickName, "1", time, industry, company, file);
                Log.i("timec","timec:::"+time);
                break;
        }
    }

    private void showDate() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time = getTime(date);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               String timeriqi = format.format(date);
                etSri.setText(timeriqi);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.format(date);
        Log.i("timec","timec:"+ format.format(date));
        return format.format(date);
    }

    /**
     * 弹出popupWindow更改头像
     *
     * @param view
     */
    private void changeIcon(View view) {
        if (popupWindow == null) {
            popupView = View.inflate(this, R.layout.window_popup, null);
            // 参数2,3：指明popupwindow的宽度和高度
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    lighton();
                }
            });
            // 设置背景图片， 必须设置，不然动画没作用
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            // 设置点击popupwindow外屏幕其它地方消失
            popupWindow.setOutsideTouchable(true);
            // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateInterpolator());
            animation.setDuration(200);

            popupView.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开系统拍照程
                    ToastHelper.show(EditDataActivity.this, "点击拍照");
//                    takePhoto(v);
                    autoObtainCameraPermission();
                }
            });
            popupView.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    // 打开系统图库选择图片
                    ToastHelper.show(EditDataActivity.this, "点击相册");
//                    getPhoto(v);
                    autoObtainStoragePermission();
                }
            });
        }

        // 在点击之后设置popupwindow的销毁
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            lighton();
        }

        // 设置popupWindow的显示位置，此处是在手机屏幕底部且水平居中的位置
        popupWindow.showAtLocation(EditDataActivity.this.findViewById(R.id.lll), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupView.startAnimation(animation);
    }

    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }


    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastHelper.show(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                //通过FileProvider创建一个content类型的Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", fileUri);
                }
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastHelper.show(this, "设备没有SD卡！");
            }
        }
    }

    /**
     * 自动获取sdk权限
     */
    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastHelper.show(this, "设备没有SD卡！");
                    }
                } else {

                    ToastHelper.show(this, "请允许打开相机！！");
                }
                break;


            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {

                    ToastHelper.show(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Log.i("====cropImageUri", "cropImageUri" + cropImageUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Log.i("====cropImageUri", "cropImageUri" + cropImageUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(getApplicationContext(), getApplication().getPackageName() + ".FileProvider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastHelper.show(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        BitmapFileSetting bitmapFileSetting = new BitmapFileSetting();
                        File f = bitmapFileSetting.saveBitmapFile(bitmap, Environment.getExternalStorageDirectory().getPath());
                        Log.i("==filepath", "==filepath:" + f);
                        showImages(bitmap);
                    }
                    break;
                default:
            }
        }
    }

    /**
     * 展示图片
     *
     * @param bitmap
     */
    private void showImages(Bitmap bitmap) {
        edtPhoto.setImageBitmap(bitmap);
    }

    /**
     * 昵称设置dialog
     */
    private void showInputDialog(String name, String title) {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(EditDataActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(EditDataActivity.this);
        inputDialog.setTitle(title).setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (name.equals("nickname")) {
                            etNickname.setText(editText.getText().toString().trim());
                            showToast("" + editText.getText().toString().trim());
                        } else if (name.equals("gsname")) {
                            etCompany.setText(editText.getText().toString().trim());
                            showToast("" + editText.getText().toString().trim());
                        }


                    }
                });
        inputDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        inputDialog.show();
    }

    public void showSingleAlertDialog(View view, String[] arr) {
//        final String[] items = {"单选1", "单选2", "单选3", "单选4"};
        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(this);
        alertBuilder.setTitle("请选择行业");
        alertBuilder.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(GuideSettingActivity.this, arr[i], Toast.LENGTH_SHORT).show();
                industry = arr[i];
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!industry.equals("")) {
                    etIndustry.setText(industry);
                }
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }

    //==============================行业选择====================================================================================
    private void SelectTrade(View v) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Publicity.Sundry.Industry");
        sendCodeSign.put("language", "zh_cn");
        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Publicity.Sundry.Industry");
            map.put("language", "zh_cn");
            map.put("sign", sign);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Publicity.Sundry.Industry", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-SelectTrade", "result-SelectTrade:" + result);
                        selectTradeEntity = gson.fromJson(result, SelectTradeEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (selectTradeEntity.getRet() == 200) {
                                    showToast(selectTradeEntity.getMsg());
                                    List<String> listsData = selectTradeEntity.getData().getLists();
                                    final int size = listsData.size();
                                    arr = (String[]) listsData.toArray(new String[size]);
                                    if (arr != null) {
                                        showSingleAlertDialog(v, arr);
                                    }
                                } else {
                                    showToast(selectTradeEntity.getMsg());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //==============================提交资料====================================================================================


    public void submitEditor(String nickname, String sex, String birthday, String industry, String company, String file) {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                MediaType MEDIA_TYPE = MediaType.parse("image/jpeg");
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                Log.i("==gxl==fileCropUri", "" + file);
                File f1 = new File(file);
                Log.i("==gxl==f1", "==gxl==f1" + f1);
                HashMap<String, String> sendCodeSign = new HashMap<>();
                sendCodeSign.put("service", "Customer.Customer.Edit");
                sendCodeSign.put("language", "zh_cn");
                sendCodeSign.put("token", getStringSharePreferences("token", "token"));
                sendCodeSign.put("nickname", nickname);
                sendCodeSign.put("sex", sex);
                sendCodeSign.put("birthday", birthday);
                sendCodeSign.put("industry", industry);
                sendCodeSign.put("company", company);
                sendCodeSign.put("pic", file);
                sign = SignUtil.Sign(sendCodeSign);

                builder.addFormDataPart("service", "Customer.Customer.Edit")
                        .addFormDataPart("language", "zh_cn")
                        .addFormDataPart("token", getStringSharePreferences("token", "token"))
                        .addFormDataPart("nickname", nickname)
                        .addFormDataPart("sex", sex)
                        .addFormDataPart("birthday", birthday)
                        .addFormDataPart("industry", industry)
                        .addFormDataPart("company", company)
                        .addFormDataPart("sign", sign)
                        .addFormDataPart("pic", f1.getName(), RequestBody.create(MEDIA_TYPE, fileCropUri));
                MultipartBody requestBody = builder.build();
                Log.i("requestBody", "requestBody" + requestBody);
                //构建请求
                OkHttpClient client = new OkHttpClient();
                /**
                 * HTTPS证书问题
                 */
                OkHttpClient mOkHttpClient = client.newBuilder()
                        .sslSocketFactory(HttpsTrustManager.createSSLSocketFactory())
                        .hostnameVerifier(new HttpsTrustManager.TrustAllHostnameVerifier())
                        .build();
                Request request = new Request.Builder()
                        .url(Constants.SERVER_BASE_URL + "service=Customer.Customer.Edit")
                        .post(requestBody)
                        .build();
                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("onResponse", "onFailure: 访问失败!" + e);
//                        ToastHelper.show(MyInformationActivity.this,"访问失败!"+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.d("onResponse", "onResponse: 访问成功!");
                            String json = response.body().string();
                            Log.d("onResponse", "onResponse: json:" + json);
                            submitEditorEntity = gson.fromJson(json, SubmitEditorEntity.class);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (submitEditorEntity.getRet() == 200) {
//                                        if (submitEditorEntity.getData().getMsg() == "success") {
                                            showToast(submitEditorEntity.getData().getMsg());
                                            finish();
//                                        }

                                    }else {
showToast(submitEditorEntity.getData().getMsg());
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i("gxl======", "gxl" + e);
                        }
                    }
                });
            }
        }.start();
    }
}
