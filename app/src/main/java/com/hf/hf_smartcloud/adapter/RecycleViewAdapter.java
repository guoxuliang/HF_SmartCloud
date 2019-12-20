package com.hf.hf_smartcloud.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.ui.activity.facility.DevicesDetailsActivity;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DevicesDetailsActivity devicesDetailsActivity;
    private List<String> list;

    public RecycleViewAdapter(DevicesDetailsActivity devicesDetailsActivity, List<String> list) {
        this.devicesDetailsActivity = devicesDetailsActivity;
        this.list = list;
    }
    private OnItemClickListener onRecyclerViewItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }
    private int thisPosition;

    public int getthisPosition() {
        return thisPosition;
    }
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public void setOnRecyclerViewItemClickListener(OnItemClickListener onItemClickListener) {
        this.onRecyclerViewItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(devicesDetailsActivity).inflate(R.layout.item_recycle, parent, false);
        RecViewViewHolder viewViewHolder = new RecViewViewHolder(view);
        return viewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        RecViewViewHolder viewViewHolder = (RecViewViewHolder) holder;
        if (viewViewHolder != null) {
            viewViewHolder.leixing.setText(list.get(position) + "");
            if (position == getthisPosition()) {
                //選中的顔色就設成了  黃色
//                viewViewHolder.rlt.setBackgroundColor(Color.parseColor("#b4a4F7"));
//                viewViewHolder.rlt.setBackgroundColor(R.drawable.button_circle_shape_zise);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(10);
//                drawable.setStroke(1, Color.parseColor("#cccccc"));
                drawable.setColor(Color.parseColor("#b4a4F7"));
                viewViewHolder.rlt.setBackgroundDrawable(drawable);

            } else {
                //未選中的顔色 就設成了 白色
//                viewViewHolder.rlt.setBackgroundColor(Color.WHITE);
//                viewViewHolder.rlt.setBackgroundColor(R.drawable.button_circle_shape_white);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(10);
//                drawable.setStroke(1, Color.parseColor("#cccccc"));
                drawable.setColor(Color.parseColor("#ffffff"));
                viewViewHolder.rlt.setBackgroundDrawable(drawable);
            }
        }
        if (onRecyclerViewItemClickListener != null) {
            //點擊事件
            viewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onClick(position);
                }
            });
            //長按事件
            viewViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRecyclerViewItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class RecViewViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlt;
        TextView chaunganqi, leixing;

        RecViewViewHolder(View itemView) {
            super(itemView);
            chaunganqi = (TextView) itemView.findViewById(R.id.chaunganqi);
            leixing = (TextView) itemView.findViewById(R.id.leixing);
            rlt = (RelativeLayout) itemView.findViewById(R.id.rlt);
        }
    }
}