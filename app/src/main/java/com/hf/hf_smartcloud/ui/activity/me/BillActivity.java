package com.hf.hf_smartcloud.ui.activity.me;

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

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.AbnormalAdapter;
import com.hf.hf_smartcloud.adapter.BillAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.main.AbnormalInfoActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.views.CircularStatisticsView;
import com.veken.chartview.bean.PieChartBean;
import com.veken.chartview.view.PieChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BillActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private CircularStatisticsView pieChartView;


    private final int PAGE_COUNT = 10;
    private SwipeRefreshLayout swipezdgl;
    private RecyclerView recyclerzdgl;
    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private BillAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initTitle();
        initViews();
        findView();
        initData();
        initRefreshLayout();
        initRecyclerView();
    }
    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add =  findviewByid(R.id.btn_add);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("账单管理");
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

    private void initViews() {
        pieChartView = findViewById(R.id.cs_view);//饼图
        pieChartView.setCircleWidth(30);
        pieChartView.setPercentage(1000,300);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add("转账/消费" + i);
        }
    }

    private void findView() {
        swipezdgl = findViewById(R.id.swipezdgl);
        recyclerzdgl = findViewById(R.id.recyclerzdgl);
        recyclerzdgl.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(BillActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
//                openActivity(AddaddressActivity.class);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(BillActivity.this, "长按:" + posotion, Toast.LENGTH_LONG).show();
//                showPopMenu(view, posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerzdgl.setLayoutManager(linearLayoutManager);
    }
    private void initRefreshLayout() {
        swipezdgl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipezdgl.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new BillAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerzdgl.setLayoutManager(mLayoutManager);
        recyclerzdgl.setAdapter(adapter);
        recyclerzdgl.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerzdgl.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        swipezdgl.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipezdgl.setRefreshing(false);
            }
        }, 1000);
    }
}
