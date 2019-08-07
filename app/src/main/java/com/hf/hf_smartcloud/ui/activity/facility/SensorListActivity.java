package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.PoliceInfoAdapter;
import com.hf.hf_smartcloud.adapter.SensorAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.main.PoliceInfoActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddaddressActivity;
import com.hf.hf_smartcloud.ui.zxing.QrCodeActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SensorListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private final int PAGE_COUNT = 10;
    private SwipeRefreshLayout swipecgq;
    private RecyclerView recyclercgq;
    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private SensorAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView iv_nono_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        findView();
        initData();
        initRefreshLayout();
        initRecyclerView();
    }
    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add("通知" + i);
        }
    }

    private void findView() {
        iv_nono_right = findviewByid(R.id.iv_nono_right);
        iv_nono_right.setOnClickListener(this);
        swipecgq = findViewById(R.id.swipecgq);
        recyclercgq = findViewById(R.id.recyclercgq);
        recyclercgq.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SensorListActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
//                openActivity(AddaddressActivity.class);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(SensorListActivity.this, "长按:" + posotion, Toast.LENGTH_LONG).show();
//                showPopMenu(view, posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclercgq.setLayoutManager(linearLayoutManager);
    }
    private void initRefreshLayout() {
        swipecgq.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipecgq.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new SensorAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclercgq.setLayoutManager(mLayoutManager);
        recyclercgq.setAdapter(adapter);
        recyclercgq.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclercgq.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (adapter.isFadeTips() == false && lastVisibleItem + 1 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (adapter.isFadeTips() == true && lastVisibleItem + 2 == adapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(), adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private List<String> getDatas(final int firstIndex, final int lastIndex) {
        List<String> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < list.size()) {
                resList.add(list.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<String> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
        swipecgq.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipecgq.setRefreshing(false);
            }
        }, 1000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_nono_right:
                // TODO zxing扫码
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
