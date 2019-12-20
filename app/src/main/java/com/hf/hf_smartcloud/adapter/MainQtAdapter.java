package com.hf.hf_smartcloud.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.bean.BleO9Bean;
import com.hf.hf_smartcloud.bean.BleOFBean;
import com.hf.hf_smartcloud.bean.UserEvent;
import com.hf.hf_smartcloud.utils.iconview.CircleTextImage;

import java.util.List;

public class MainQtAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BleOFBean> dataList;
    private List<BleO9Bean> dataList09;
    private View view;
    public MainQtAdapter(List<BleOFBean> dataList,List<BleO9Bean> dataList09) {
        this.dataList = dataList;
        this.dataList09 = dataList09;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new RecyclerViewHolder(view);
    }
    String str;
    String str1;// 截取空格之前字符串
    String str2;// 截取空格之后字符串
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
         str = dataList.get(position).getQtname();
        str1 = str.substring(0, str.indexOf(" "));
        str2 = str.substring(str1.length()+1, str.length());
        recyclerViewHolder.circleTextImage.setText4CircleImage(str1);
        recyclerViewHolder.qiti.setText(str2);
        recyclerViewHolder.nongdu.setText(dataList.get(position).getNongdu());
        recyclerViewHolder.baojing.setText(dataList.get(position).getBaojing());
        if(dataList09.size()!=0){
            if(dataList09.get(position).getOnepolice()!=null && dataList09.get(position).getTwopolice()!=null){
                recyclerViewHolder.onepolice.setText(dataList09.get(position).getOnepolice());
                recyclerViewHolder.twopolice.setText(dataList09.get(position).getTwopolice());
            }

        }
        if(dataList.get(position).getBaojing()=="正常"){
            Drawable drawableLeft = view.getResources().getDrawable(
                    R.drawable.icon_zc_bg);
            recyclerViewHolder.baojing.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null, null, null);
            recyclerViewHolder.baojing.setCompoundDrawablePadding(10);
        }else if(dataList.get(position).getBaojing()=="1级报警"){
            Drawable drawableLeft = view.getResources().getDrawable(
                    R.drawable.icon_cb_bg);
            recyclerViewHolder.baojing.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null, null, null);
            recyclerViewHolder.baojing.setCompoundDrawablePadding(10);
        }else if(dataList.get(position).getBaojing()=="2级报警"){
            Drawable drawableLeft = view.getResources().getDrawable(
                    R.drawable.icon_yz_bg);
            recyclerViewHolder.baojing.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null, null, null);
            recyclerViewHolder.baojing.setCompoundDrawablePadding(10);
        }else if(dataList.get(position).getBaojing()=="传感器故障或者未接"){

        }else if(dataList.get(position).getBaojing()=="超限报警"){

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView qiti,nongdu,baojing,onepolice,twopolice;
        CircleTextImage circleTextImage;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            circleTextImage = itemView.findViewById(R.id.circleTextImage);
            qiti = (TextView) itemView.findViewById(R.id.qiti);
            nongdu = (TextView) itemView.findViewById(R.id.nongdu);
            baojing = (TextView) itemView.findViewById(R.id.baojing);
            onepolice = (TextView) itemView.findViewById(R.id.onepolice);
            twopolice = (TextView) itemView.findViewById(R.id.twopolice);
        }
    }
}
