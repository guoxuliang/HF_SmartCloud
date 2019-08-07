package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.RechargeAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.ItemModel;
import com.hf.hf_smartcloud.ui.activity.main.PoliceInfoActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;

import java.util.ArrayList;

public class AccountChargesActivity extends BaseActivity implements View.OnClickListener {
private ArrayList<String>  list_total;
    private RecyclerView recyclerView;
    private RechargeAdapter adapter;
    private TextView tvPay;
    private String re_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_accountcharges);
        initTitle();
        list_total = new ArrayList<>();
        list_total.add("10元");
        list_total.add("20元");
        list_total.add("50元");
        list_total.add("100元");
        list_total.add("200元");
        list_total.add("300元");
        list_total.add("500元");
        list_total.add("1000元");

        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        tvPay = (TextView) findViewById(R.id.tvPay);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new RechargeAdapter());
        adapter.replaceAll(getData());
        tvPay.setOnClickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AccountChargesActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));
    }

    public ArrayList<ItemModel> getData() {
        ArrayList<ItemModel> list = new ArrayList<>();
        for (int i = 0; i < list_total.size(); i++) {
//            String count = i+10 + "元";
            String count = list_total.get(i);
            list.add(new ItemModel(ItemModel.ONE, count));
        }
        list.add(new ItemModel(ItemModel.TWO, null));

        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvPay:

                break;
        }
    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add =  findviewByid(R.id.btn_add);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("账户充值");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddaddressActivity.class);
            }
        });
    }

}
