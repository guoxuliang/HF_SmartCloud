package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.CityListAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.CityListEntity;
import com.hf.hf_smartcloud.entity.QueryAddressEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResidentCityActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.btn_sancode)
    ImageView btnSancode;
    @BindView(R.id.recyclercityList)
    RecyclerView recyclercityList;
    @BindView(R.id.swipecityList)
    SwipeRefreshLayout swipecityList;
    private GridLayoutManager mLayoutManager;
    private CityListAdapter adapter;
    private int lastVisibleItem = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final int PAGE_COUNT = 10;
    private List<String> lists = new ArrayList<String>();
    private CityListEntity cityListEntity;
    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);
        ButterKnife.bind(this);
        initTitle();
        initViews();
        residentCityList();
    }
    private void initTitle() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvTitle.setText("常驻城市列表");
        btnAdd.setVisibility(View.GONE);
        btnSancode.setVisibility(View.GONE);
    }
    private void initViews() {
        swipecityList = findViewById(R.id.swipecityList);
        recyclercityList = findViewById(R.id.recyclercityList);
        recyclercityList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ResidentCityActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclercityList.setLayoutManager(linearLayoutManager);
    }

    private void initRefreshLayout() {
        swipecityList.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipecityList.setOnRefreshListener(this);
    }

    private void initRecyclerView(List<String> addlists) {
        adapter = new CityListAdapter(addlists, this, true);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclercityList.setLayoutManager(mLayoutManager);
        recyclercityList.setAdapter(adapter);
        recyclercityList.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclercityList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
            if (i < lists.size()) {
                resList.add(lists.get(i));
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
        swipecityList.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipecityList.setRefreshing(false);
            }
        }, 1000);
    }
    //=====================================
    /**
     * 常驻城市列表
     */
    private void residentCityList(){
            if (!isConnNet(this)) {
                showToast("请检查网络");
                return;
            }
            HashMap<String, String> sendCodeSign = new HashMap<>();
            sendCodeSign.put("service", "Customer.Safe.City_list");
            sendCodeSign.put("language", "zh_cn");
            sendCodeSign.put("token", getStringSharePreferences("token", "token"));

            sign = SignUtil.Sign(sendCodeSign);
            try {
                Map<String, String> map = new HashMap<>();
                map.put("service", "Customer.Safe.City_list");
                map.put("language", "zh_cn");
                map.put("token", getStringSharePreferences("token", "token"));
                map.put("sign", sign);

                HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Safe.City_list", map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("错误", "错误：" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String result = response.body().string();
                            Log.i("result-residentCityList", "result-residentCityList:" + result);
                            cityListEntity = gson.fromJson(result, CityListEntity.class);
                           ResidentCityActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (cityListEntity.getRet() == 200) {
                                        showToast(cityListEntity.getData().getMsg());
                                        lists = cityListEntity.getData().getLists();
                                        initRefreshLayout();
                                        initRecyclerView(lists);
//                                    }

                                    } else {
                                        showToast(cityListEntity.getMsg());
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
}
