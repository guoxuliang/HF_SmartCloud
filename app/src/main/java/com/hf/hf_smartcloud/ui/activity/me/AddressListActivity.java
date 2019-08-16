package com.hf.hf_smartcloud.ui.activity.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.hf.hf_smartcloud.entity.DelAddressEntity;
import com.hf.hf_smartcloud.entity.QueryAddressEntity;
import com.hf.hf_smartcloud.entity.UnbundQQWchatEntity;
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
    //    private List<String> list;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private AddressListAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String sign = "";
    private String token = "";
    private Gson gson = new Gson();
    private QueryAddressEntity queryAddressEntity;
    private List<QueryAddressEntity.DataBean.ListsBean> lists = new ArrayList<QueryAddressEntity.DataBean.ListsBean>();
    private AlertDialog.Builder builder;
    private String delyes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initTitle();
        findView();
//        queryAddress();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
//                finish();
            }
        });
    }

    private void findView() {
        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(AddressListActivity.this, "点击:" + position, Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("recipients", lists.get(position).getRecipients());
                bundle.putString("telephone", lists.get(position).getTelephone());
                bundle.putString("postcode", lists.get(position).getPostcode());
                bundle.putString("address", lists.get(position).getAddress());
                bundle.putString("customer_address_id", lists.get(position).getCustomer_address_id());
                bundle.putString("is_default", lists.get(position).getIs_default());
                bundle.putString("xg", "xg");
                Log.i("======", "recipients:" + lists.get(position).getRecipients() + "telephone:" + lists.get(position).getTelephone() + "postcode:" + lists.get(position).getPostcode() + "address:" + lists.get(position).getAddress() + "is_default:" + lists.get(position).getIs_default());
                openActivity(AddaddressActivity.class, bundle);
            }

            @Override
            public void onLongClick(View view, int posotion) {
                showTwo(posotion);

            }
        }));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * 两个按钮的 dialog
     */
    private void showTwo(final int pos) {

        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("删除")
                .setMessage("确定删除该地址").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        delAddress(lists.get(pos).getCustomer_address_id(), pos);
//                        adapter.removeItem(pos);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }


    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView(List<QueryAddressEntity.DataBean.ListsBean> addlists) {
        adapter = new AddressListAdapter(addlists, this, true);
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

    private List<QueryAddressEntity.DataBean.ListsBean> getDatas(final int firstIndex, final int lastIndex) {
        List<QueryAddressEntity.DataBean.ListsBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < lists.size()) {
                resList.add(lists.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<QueryAddressEntity.DataBean.ListsBean> newDatas = getDatas(fromIndex, toIndex);
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

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Get_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
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
                                    initRefreshLayout();
                                    initRecyclerView(lists);
//                                        //TODO 绑定填充数据
//                                    }

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
    DelAddressEntity delAddressEntity;

    private void delAddress(final String id, final int pos) {

        if (!isConnNet(this)) {
            showToast("请检查网络");
            return;
        }
        HashMap<String, String> sendCodeSign = new HashMap<>();
        sendCodeSign.put("service", "Customer.Address.Del_address");
        sendCodeSign.put("language", "zh_cn");
        sendCodeSign.put("token", getStringSharePreferences("token", "token"));
        sendCodeSign.put("customer_address_id", id);

        sign = SignUtil.Sign(sendCodeSign);
        try {
            Map<String, String> map = new HashMap<>();
            map.put("service", "Customer.Address.Del_address");
            map.put("language", "zh_cn");
            map.put("token", getStringSharePreferences("token", "token"));
            map.put("customer_address_id", id);
            map.put("sign", sign);
            //创建一个线程
            new Thread(new Runnable() {

                @Override
                public void run() {
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
                                delAddressEntity = gson.fromJson(result, DelAddressEntity.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (delAddressEntity.getRet() == 200) {
                                            Log.i("delAddressEntity", "delAddressEntity");
                                            if (delAddressEntity.getData().getMsg() == "success") {
                                                adapter.removeItem(pos);

                                            } else if (delAddressEntity.getData().getMsg() == "fail") {
                                                String errlists = delAddressEntity.getData().getError().get_$0();
//                                        showToast(errlists);
                                            }

                                        } else {
//                                    showToast(delAddressEntity.getMsg());
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.i("Exception", "Exception" + e.toString());
                            }
                        }
                    });
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception", "Exception" + e.toString());
        }
    }

}
