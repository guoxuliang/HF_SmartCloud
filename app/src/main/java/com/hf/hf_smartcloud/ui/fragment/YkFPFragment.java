package com.hf.hf_smartcloud.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.AllFpAdapter;
import com.hf.hf_smartcloud.adapter.SensorAdapter;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：${NanFeiLong}
 * 日期 2016/12/9 17:32
 * 店铺对应的fragment
 */

public class YkFPFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private final int PAGE_COUNT = 10;
    private SwipeRefreshLayout swipeykfp;
    private RecyclerView recyclerykfp;
    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private AllFpAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView iv_nono_right;

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.ykfp,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initData();
        initRefreshLayout();
        initRecyclerView();
    }

    private void initViews() {
        swipeykfp = view.findViewById(R.id.swipeykfp);
        recyclerykfp = view.findViewById(R.id.recyclerykfp);
        recyclerykfp.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点击:" + position, Toast.LENGTH_LONG).show();
//                openActivity(AddaddressActivity.class);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(getActivity(), "长按:" + posotion, Toast.LENGTH_LONG).show();
//                showPopMenu(view, posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerykfp.setLayoutManager(linearLayoutManager);
    }
    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add("已开发票" + i);
        }
    }
    private void initRefreshLayout() {
        swipeykfp.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeykfp.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new AllFpAdapter(getDatas(0, PAGE_COUNT), getActivity(), getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerykfp.setLayoutManager(mLayoutManager);
        recyclerykfp.setAdapter(adapter);
        recyclerykfp.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerykfp.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        swipeykfp.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeykfp.setRefreshing(false);
            }
        }, 1000);
    }

}