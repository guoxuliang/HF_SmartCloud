package com.hf.hf_smartcloud.ui.activity.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.MyLiveList;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.AddressListAdapter;
import com.hf.hf_smartcloud.adapter.PoliceInfoAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddaddressActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddressListActivity;
import com.hf.hf_smartcloud.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PoliceInfoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener/**, PoliceInfoAdapter.OnItemClickListener*/
{
    private int mEditMode = MYLIVE_MODE_CHECK;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private final int PAGE_COUNT = 30;
    private SwipeRefreshLayout swipebjxx;
    private RecyclerView recyclerbjxx;
    private int lastVisibleItem = 0;
    private GridLayoutManager mLayoutManager;
    private PoliceInfoAdapter adapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private TextView tvBj;
    private TextView select_all;
    private Button btn_delete;
    private LinearLayout mLlMycollectionBottomDialog;
    private boolean editorStatus = false;
    private boolean isSelectAll = false;
    private int index = 0;
    private LinearLayout ll_allread, ll_delete;
    private List<MyLiveList> mList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policeinfo);
        findView();
        initData();
        initListener();
        initRefreshLayout();
        initRecyclerView();
    }

    private void initListener() {
        tvBj.setOnClickListener(this);
        select_all.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle("这是第" + i + "个条目");
            myLiveList.setSource("来源" + i);
            myLiveList.setSelect(false);
            mList.add(myLiveList);
        }
        Log.i("===mList.size():::","===mList.size():::"+mList.size());
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            tvBj.setText("取消");
            ll_delete.setVisibility(View.VISIBLE);
            ll_allread.setVisibility(View.GONE);
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            tvBj.setText("编辑");
            ll_delete.setVisibility(View.GONE);
            ll_allread.setVisibility(View.VISIBLE);
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        adapter.setEditMode(mEditMode);
    }

    private void clearAll() {
//        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        select_all.setText("全选");
        setBtnBackground(0);
    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            btn_delete.setBackgroundResource(R.drawable.button_shape);
            btn_delete.setEnabled(true);
            btn_delete.setTextColor(Color.WHITE);
        } else {
            btn_delete.setBackgroundResource(R.drawable.button_noclickable_shape);
            btn_delete.setEnabled(false);
            btn_delete.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }

    private void findView() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("报警信息");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvBj = findviewByid(R.id.tvBj);
        tvBj.setVisibility(View.VISIBLE);
        tvBj.setText("编辑");
        ll_allread = findviewByid(R.id.ll_allread);
        ll_allread.setVisibility(View.VISIBLE);
        ll_delete = findviewByid(R.id.ll_delete);
        btn_delete = findviewByid(R.id.btn_delete);
        select_all = findviewByid(R.id.select_all);
        mLlMycollectionBottomDialog = findviewByid(R.id.ll_mycollection_bottom_dialog);
        swipebjxx = findViewById(R.id.swipebjxx);
        recyclerbjxx = findViewById(R.id.recyclerbjxx);
        recyclerbjxx.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                if (adapter == null) {
//                    return;
//                }
//                ImageView mCheckBox = (ImageView) view.findViewById(R.id.check_box);
//
//                MyLiveList myLive = mList.get(position);
//                boolean isSelect = myLive.isSelect();
//                //TODO 设置adapter中的数据
//                if (!isSelect) {
//                    mCheckBox.setImageResource(R.drawable.ic_checked);
////                            mList.setSelect(true);
//                    mList.get(position).setSelect(true);
//                    adapter.getMyLiveList().get(position).setSelect(true);
//                    boolean d = mList.get(position).isSelect();
//                    Log.i("====ddd", "===ddd:::" + d);
//                } else {
//                    mCheckBox.setImageResource(R.drawable.ic_uncheck);
//                }
//
//                myLive.setSelect(true);
//                if (index == mList.size()) {
//                    isSelectAll = true;
//                }
//                adapter.notifyDataSetChanged();

                //=======================================================================================================================================================
                if (adapter == null){
                    return;
                }
                ImageView mCheckBox = (ImageView) view.findViewById(R.id.check_box);
                boolean isSelect = mList.get(position).isSelect();
                if (!isSelect) {
                    index++;
                    mCheckBox.setImageResource(R.drawable.ic_checked);
                    mList.get(position).setSelect(true);
//                    btn_delete.setEnabled(true);

                }else {
                    index--;
                    mCheckBox.setImageResource(R.drawable.ic_uncheck);
                    mList.get(position).setSelect(false);
//                    btn_delete.setEnabled(false);
                }
                setBtnBackground(index);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int posotion) {
                Toast.makeText(PoliceInfoActivity.this, "长按:" + posotion, Toast.LENGTH_LONG).show();
//                showPopMenu(view, posotion);
            }
        }));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerbjxx.setLayoutManager(linearLayoutManager);
    }

    private void initRefreshLayout() {
        swipebjxx.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipebjxx.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new PoliceInfoAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerbjxx.setLayoutManager(mLayoutManager);
        recyclerbjxx.setAdapter(adapter);
        recyclerbjxx.setItemAnimator(new DefaultItemAnimator());
        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(AddressListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerbjxx.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    private List<MyLiveList> getDatas(final int firstIndex, final int lastIndex) {
        List<MyLiveList> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < mList.size()) {
                resList.add(mList.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<MyLiveList> newDatas = getDatas(fromIndex, toIndex);
        adapter.notifyDataSetChanged();
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }

    @Override
    public void onRefresh() {
//        swipebjxx.setRefreshing(true);
//        adapter.resetDatas();
//        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipebjxx.setRefreshing(false);
            }
        }, 1000);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBj:
                updataEditMode();
                break;
            case R.id.select_all:
                //TODO 全选
                selectAllMain();
                break;
            case R.id.btn_delete:
                //TODO 删除
                deleteVideo();
                break;
        }
    }


    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (adapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = adapter.getMyLiveList().size(); i < j; i++) {
                adapter.getMyLiveList().get(i).setSelect(true);
            }
            index = adapter.getMyLiveList().size();
            btn_delete.setEnabled(true);
            select_all.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = adapter.getMyLiveList().size(); i < j; i++) {
                adapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            btn_delete.setEnabled(false);
            select_all.setText("全选");
            isSelectAll = false;
        }
        adapter.notifyDataSetChanged();
        setBtnBackground(index);
//        mTvSelectNum.setText(String.valueOf(index));
    }


    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            btn_delete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = adapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    MyLiveList myLive = adapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {
                        adapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                setBtnBackground(index);
                if (adapter.getMyLiveList().size() == 0) {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }

//    @Override
//    public void onItemClickListener(int pos, List<MyLiveList> myLiveList) {
//        if (editorStatus) {
//            MyLiveList myLive = myLiveList.get(pos);
//            boolean isSelect = myLive.isSelect();
//            if (!isSelect) {
//                index++;
//                myLive.setSelect(true);
//                if (index == myLiveList.size()) {
//                    isSelectAll = true;
//                    select_all.setText("取消全选");
//                }
//
//            } else {
//                myLive.setSelect(false);
//                index--;
//                isSelectAll = false;
//                select_all.setText("全选");
//            }
//            setBtnBackground(index);
//            adapter.notifyDataSetChanged();
//        } else {
//            Toast.makeText(this, "哈哈哈哈哈哈啊哈", Toast.LENGTH_LONG).show();
//        }
//    }

}