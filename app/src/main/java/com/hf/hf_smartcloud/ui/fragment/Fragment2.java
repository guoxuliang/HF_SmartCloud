package com.hf.hf_smartcloud.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.FacilityFAdapter;
import com.hf.hf_smartcloud.base.BaseFragment;
import com.hf.hf_smartcloud.ui.activity.facility.AddSbActivity;
import com.hf.hf_smartcloud.ui.activity.facility.HistoryMonitoringActivity;
import com.hf.hf_smartcloud.ui.activity.facility.ReplaceActivity;
import com.hf.hf_smartcloud.ui.activity.facility.SensorListActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends BaseFragment implements View.OnClickListener {
    View view;
private LinearLayout layout_cgq;
private TextView tv_addsb,tv_replace,tv_history;

    private final int PAGE_COUNT = 10;
    private SwipeRefreshLayout swipesbfragment;
    private RecyclerView recyclersbfragment;
    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private FacilityFAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inirViews();
        layout_cgq = view.findViewById(R.id.layout_cgq);
        layout_cgq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SensorListActivity.class);
            }
        });
        findView();
        initData();
//        initRefreshLayout();
        initRecyclerView();
    }

    private void inirViews() {
        tv_addsb = view.findViewById(R.id.tv_addsb);
        tv_replace = view.findViewById(R.id.tv_replace);
        tv_history = view.findViewById(R.id.tv_history);
        tv_addsb.setOnClickListener(this);
        tv_replace.setOnClickListener(this);
        tv_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_addsb:
                //TODO  添加设备  AddSbActivity
                openActivity(AddSbActivity.class);
                break;
            case R.id.tv_replace:
                //TODO  更换设备  ReplaceActivity
                openActivity(ReplaceActivity.class);
                break;
            case R.id.tv_history:
                //TODO  历史检测  HistoryMonitoringActivity
                openActivity(HistoryMonitoringActivity.class);
                break;
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            list.add("控制柜" + i);
        }
    }

    private void findView() {
//        swipebjxx = view.findViewById(R.id.swipebjxx);
        recyclersbfragment = view.findViewById(R.id.recyclersbfragment);
        recyclersbfragment.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点击:" + position, Toast.LENGTH_LONG).show();
                openActivity(SensorListActivity.class);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(getActivity(), "长按:" + posotion, Toast.LENGTH_LONG).show();
//                showPopMenu(view, posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclersbfragment.setLayoutManager(linearLayoutManager);
    }
//    private void initRefreshLayout() {
//        swipebjxx.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
//                android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        swipebjxx.setOnRefreshListener(this);
//    }

    private void initRecyclerView() {
        adapter = new FacilityFAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclersbfragment.setLayoutManager(mLayoutManager);
        recyclersbfragment.setAdapter(adapter);
        recyclersbfragment.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclersbfragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

//    @Override
//    public void onRefresh() {
//        swipebjxx.setRefreshing(true);
//        adapter.resetDatas();
//        updateRecyclerView(0, PAGE_COUNT);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipebjxx.setRefreshing(false);
//            }
//        }, 1000);
//    }

}
