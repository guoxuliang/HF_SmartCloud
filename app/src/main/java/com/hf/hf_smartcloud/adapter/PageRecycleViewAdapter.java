package com.hf.hf_smartcloud.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hf.hf_smartcloud.R;

import java.util.List;

public class PageRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> dataList;

    public PageRecycleViewAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_history, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.leixing.setText(dataList.get(position));
        recyclerViewHolder.leixing.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView chaunganqi,leixing;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            chaunganqi = (TextView) itemView.findViewById(R.id.chaunganqi);
            leixing = (TextView) itemView.findViewById(R.id.leixing);
        }
    }
}
