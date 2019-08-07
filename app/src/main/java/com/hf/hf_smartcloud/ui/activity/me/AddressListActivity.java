package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.AddressListAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.constants.Constants;
import com.hf.hf_smartcloud.entity.QueryAddressEntity;
import com.hf.hf_smartcloud.entity.SelectTradeEntity;
import com.hf.hf_smartcloud.http.HttpUtils;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;
import com.hf.hf_smartcloud.utils.SignUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddressListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final int PAGE_COUNT = 10;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private AddressListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    private QueryAddressEntity queryAddressEntity;
    private List<QueryAddressEntity.DataBean.ListsBean> lists = new ArrayList<QueryAddressEntity.DataBean.ListsBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initTitle();
        findView();
//        initData();
        initRefreshLayout();
        initRecyclerView();
        queryAddress();
    }


    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add = findviewByid(R.id.btn_add);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("地址管理");
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

    private void initData() {
        list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            list.add("条目" + i);
        }
    }

    private void findView() {
        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AddressListActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
                openActivity(AddaddressActivity.class);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(AddressListActivity.this, "长按:" + posotion, Toast.LENGTH_LONG).show();
                showPopMenu(view, posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                adapter.removeItem(pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }


    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new AddressListAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        refreshLayout.setRefreshing(true);
        adapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
    //============查询收货地址======================================================================

    /**
     * ====================查询收货地址=============================================================
     */

    private void queryAddress() {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Address.Get_address");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sendCodeSign.put("recipients", "recipients");
        sendCodeSign.put("telephone", "telephone");
        sendCodeSign.put("email", "email");
        sendCodeSign.put("is_default", "is_default");

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Get_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("recipients", "recipients");
            map.put("telephone", "telephone");
            map.put("email", "email");
            map.put("is_default", "is_default");
            map.put("sign", sign);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Address.Get_address", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-queryAddress", "result-queryAddress:" + result);
                        queryAddressEntity = gson.fromJson(result, QueryAddressEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (queryAddressEntity.getRet() == 200) {
                                    showToast(queryAddressEntity.getMsg());
                                    lists = queryAddressEntity.getData().getLists();
                                    for (int i = 0; i <= lists.size(); i++) {
                                        String customer_address_id = lists.get(i).getCustomer_address_id();
                                        String customer_id = lists.get(i).getCustomer_id();
                                        String recipients = lists.get(i).getRecipients();
                                        String email = lists.get(i).getEmail();
                                        String telephone = lists.get(i).getTelephone();
                                        String address = lists.get(i).getAddress();
                                        String postcode = lists.get(i).getPostcode();
                                        String remark = lists.get(i).getRemark();
                                        String is_default = lists.get(i).getIs_default();
                                        //TODO 填充数据
                                    }

                                } else {
                                    showToast(queryAddressEntity.getMsg());
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

    /**
     * ====================删除收货地址=============================================================
     */
    private void delAddress() {
        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Address.Del_address");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sendCodeSign.put("customer_address_id", "29");

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Del_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("customer_address_id", "29");
            map.put("sign", sign);

            HttpUtils.doPost(Constants.SERVER_BASE_URL + "service=Customer.Address.Del_address", map, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("错误", "错误：" + e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        Log.i("result-delAddress", "result-delAddress:" + result);
                        queryAddressEntity = gson.fromJson(result, QueryAddressEntity.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (queryAddressEntity.getRet() == 200) {
                                    showToast(queryAddressEntity.getMsg());

                                } else {
                                    showToast(queryAddressEntity.getMsg());
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
