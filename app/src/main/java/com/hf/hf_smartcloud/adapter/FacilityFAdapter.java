package com.hf.hf_smartcloud.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;

import java.util.ArrayList;
import java.util.List;

public class FacilityFAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> datas; // 数据源
    private Context context;    // 上下文Context

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    public FacilityFAdapter(List<String> datas, Context context, boolean hasMore) {
        // 初始化变量  
        this.datas = datas;
        this.context = context;
        this.hasMore = hasMore;
    }

    // 获取条目数量，之所以要加1是因为增加了一条footView  
    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView  
    public int getRealLastPosition() {
        return datas.size();
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

    // 正常item的ViewHolder，用以缓存findView操作
    class NormalHolder extends RecyclerView.ViewHolder {
        View viewItem;
        ImageView recycler_view_test_item_person_name_tv;//头像
        TextView sb_name,sb_mac,sb_count,sb_date,sb_zt;

        public NormalHolder(View itemView) {
            super(itemView);
            viewItem=itemView;
            recycler_view_test_item_person_name_tv= (ImageView) itemView.findViewById(R.id.recycler_view_test_item_person_name_tv);
            sb_name= (TextView) itemView.findViewById(R.id.sb_name);
            sb_mac= (TextView) itemView.findViewById(R.id.sb_mac);
            sb_count= (TextView) itemView.findViewById(R.id.sb_count);
            sb_date= (TextView) itemView.findViewById(R.id.sb_date);
            sb_zt= (TextView) itemView.findViewById(R.id.sb_zt);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种  
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.facility_item, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 如果是正常的imte，直接设置TextView的值  
        if (holder instanceof NormalHolder) {

            ((NormalHolder) holder).sb_name.setText(datas.get(position));
            ((NormalHolder) holder).sb_mac.setText(datas.get(position));
            ((NormalHolder) holder).sb_count.setText(datas.get(position));
            ((NormalHolder) holder).sb_date.setText(datas.get(position));
            ((NormalHolder) holder).sb_zt.setText(datas.get(position));

        } else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView  
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”  
            if (hasMore == true) {
                // 不隐藏footView提示  
                fadeTips = false;
                if (datas.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多  
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                    ((FootHolder) holder).tips.setVisibility(View.GONE);
                }
            } else {
                if (datas.size() > 0) {
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
    }

    // 暴露接口，改变fadeTips的方法  
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空  
    public void resetDatas() {
        datas = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false  
    public void updateList(List<String> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据  
        if (newDatas != null) {
            datas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
    public void removeItem(int pos){
        datas.remove(pos);
        notifyItemRemoved(pos);
    }
//    public interface OnItemClickListener {
//        void onClick(int position);
//
//        void onLongClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }
}  