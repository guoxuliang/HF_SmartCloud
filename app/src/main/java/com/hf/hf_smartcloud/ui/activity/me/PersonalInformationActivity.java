package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_editor)
    TextView tvEditor;
    @BindView(R.id.name_photo)
    CircleImageView namePhoto;
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvNickname)
    TextView tvNickname;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvSri)
    TextView tvSri;
    @BindView(R.id.tvCityt)
    TextView tvCityt;
    @BindView(R.id.tvIndustry)
    TextView tvIndustry;
    @BindView(R.id.tvCompany)
    TextView tvCompany;
    private ImageView btn_back;//返回
    private TextView nackName;//昵称用户名


    private String pic;
    private String account;
    private String nickname;
    private String sex;
    private String birthday;
    private String industry;
    private String company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinformation);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            pic = bundle.getString("pic");
            account = bundle.getString("account");
            nickname = bundle.getString("nickname");
            sex = bundle.getString("sex");
            birthday = bundle.getString("birthday");
            industry = bundle.getString("industry");
            company = bundle.getString("company");
        }
        initView();
    }

    private void initView() {
        btnBack.setOnClickListener(this);//返回
        tvEditor.setOnClickListener(this);//编辑资料
        tvCityt.setText(getStringSharePreferences("city", "city"));
//        Glide.with(PersonalInformationActivity.this)
//                .load(pic)
//                .placeholder(R.mipmap.icon_heard)
//                .priority(Priority.LOW)
//                .error(R.mipmap.icon_heard)
//                .into(namePhoto);
        Glide.with(PersonalInformationActivity.this).load(pic).into(namePhoto);

        tvAccount.setText(account);
        tvNickname.setText(nickname);
        if (sex.equals("1")) {
            tvSex.setText("男");
        } else if (sex.equals("2")) {
            tvSex.setText("女");
        }
        tvSri.setText(birthday);
        tvIndustry.setText(industry);
        tvCompany.setText(company);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                //TODO 返回
                PersonalInformationActivity.this.finish();
                break;
            case R.id.tv_editor:
                //TODO 编辑资料
                openActivity(EditDataActivity.class);
                PersonalInformationActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
