package com.hf.hf_smartcloud.ui.activity.facility;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.HostDevicesAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.wrapper.EndlessRecyclerOnScrollListener;
import com.hf.hf_smartcloud.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchingUnitActivity extends BaseActivity {
    @BindView(R.id.recycler_view_sun)
    RecyclerView recyclerViewSun;
    @BindView(R.id.swipe_refresh_layout_sun)
    SwipeRefreshLayout swipeRefreshLayoutSun;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.serachview)
    SearchView serachview;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.rbMain)
    RadioButton rbMain;
    @BindView(R.id.rbEquipment)
    RadioButton rbEquipment;
    @BindView(R.id.rbTimer)
    RadioButton rbTimer;
    @BindView(R.id.rbMe)
    RadioButton rbMe;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.f2)
    LinearLayout f2;
    private LoadMoreWrapper loadMoreWrapper;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchingunit);
        ButterKnife.bind(this);
        initTile();
        initViews();
    }

    private void initTile() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("金花西路");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        serachview.setFocusable(false);
        serachview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                serachview.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        swipeRefreshLayoutSun.setColorSchemeColors(Color.parseColor("#4DB6AC"));

        // 模拟获取数据
        getData();
        HostDevicesAdapter adapter = new HostDevicesAdapter(dataList);
        loadMoreWrapper = new LoadMoreWrapper(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSun.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewSun.setAdapter(loadMoreWrapper);
        recyclerViewSun.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO 点击
                showToast("您点击了" + position + "项");
                openActivity(DevicesDetailsActivity.class);
                finish();
            }

            @Override
            public void onLongClick(View view, int posotion) {
                //TODO 点击
                showToast("您长按了第" + posotion + "项");
            }
        }));

        // 设置下拉刷新
        swipeRefreshLayoutSun.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                getData();
                loadMoreWrapper.notifyDataSetChanged();

                // 延时1s关闭下拉刷新
                swipeRefreshLayoutSun.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayoutSun != null && swipeRefreshLayoutSun.isRefreshing()) {
                            swipeRefreshLayoutSun.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        // 设置加载更多监听
        recyclerViewSun.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < 52) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            dataList.add(String.valueOf(letter));
            letter++;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        serachview.setFocusable(true);
        serachview.setFocusableInTouchMode(true);
        serachview.requestFocus();
    }
}
