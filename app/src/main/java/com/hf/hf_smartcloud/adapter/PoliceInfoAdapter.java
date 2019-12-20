package com.hf.hf_smartcloud.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.MyLiveList;
import com.hf.hf_smartcloud.R;

import java.util.ArrayList;
import java.util.List;

public class PoliceInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;    // 上下文Context
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private List<MyLiveList> mMyLiveList;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private  MyLiveList myLive;
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler
//    private OnItemClickListener mOnItemClickListener;
    public PoliceInfoAdapter(List<MyLiveList> mMyLiveList, Context context, boolean hasMore) {
        // 初始化变量  
        this.mMyLiveList = mMyLiveList;
        this.context = context;
        this.hasMore = hasMore;
        if (!hasMore) {
            this.mMyLiveList = mMyLiveList;
        } else {
            this.mMyLiveList.addAll(mMyLiveList);
        }
        notifyDataSetChanged();
    }

    public List<MyLiveList> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }
    // 获取条目数量，之所以要加1是因为增加了一条footView
    @Override
    public int getItemCount() {
        return mMyLiveList.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView  
    public int getRealLastPosition() {
        return mMyLiveList.size();
    }


    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种  
        if (viewType == normalType) {
            View view =LayoutInflater.from(context).inflate(R.layout.item_bjxx_list, null);
            NormalHolder holder = new NormalHolder(view);
            return holder;
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
//         myLive = mMyLiveList.get(holder.getAdapterPosition());
        // 如果是正常的imte，直接设置TextView的值
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).tvdate.setText(mMyLiveList.get(position).getTitle());
            ((NormalHolder) holder).infoName.setText(mMyLiveList.get(position).getTitle());
            ((NormalHolder) holder).infoContent.setText(mMyLiveList.get(position).getTitle());
        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView  
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”  
            if (hasMore == true) {
                // 不隐藏footView提示  
                fadeTips = false;
                if (mMyLiveList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多  
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (mMyLiveList.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了  
                    ((FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行  
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条  
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true  
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多  
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }

        if (mEditMode == MYLIVE_MODE_CHECK) {
            ((NormalHolder) holder).mCheckBox.setVisibility(View.GONE);
        } else {
            ((NormalHolder) holder).mCheckBox.setVisibility(View.VISIBLE);

            if (mMyLiveList.get(position).isSelect()) {
                ((NormalHolder) holder).mCheckBox.setImageResource(R.drawable.ic_checked);
            } else {
                ((NormalHolder) holder).mCheckBox.setImageResource(R.drawable.ic_uncheck);
            }
        }
//        ((NormalHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
//            }
//        });

    }
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空  
    public void resetDatas() {
        mMyLiveList = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false  
    public void updateList(List<MyLiveList> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据  
        if (newDatas != null) {
            mMyLiveList.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
    public void removeItem(int pos){
        mMyLiveList.remove(pos);
        notifyItemRemoved(pos);
    }
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }
//    public interface OnItemClickListener {
//        void onItemClickListener(int pos,List<MyLiveList> myLiveList);
//    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
        View viewItem;
        TextView tvdate,infoName,infoContent;
        ImageView mCheckBox;

        public NormalHolder(View itemView) {
            super(itemView);
            viewItem=itemView;
            tvdate= (TextView) itemView.findViewById(R.id.tvdate);
            infoName= (TextView) itemView.findViewById(R.id.infoName);
            infoContent= (TextView) itemView.findViewById(R.id.infoContent);
            mCheckBox= (ImageView) itemView.findViewById(R.id.check_box);
        }
    }

    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }
}