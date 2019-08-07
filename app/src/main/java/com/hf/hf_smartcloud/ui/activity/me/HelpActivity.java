package com.hf.hf_smartcloud.ui.activity.me;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends BaseActivity {

    private ExpandableListView expandableListView;
    private int lastClick = -1;// 上一次点击的group的position
    private List<String> groupArray;// 组列表
    private List<List<String>> childArray;// 子列表
    private ExpandableListViewaAdapter expandableListViewaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initTitle();
        initViews();
    }
    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add =  findviewByid(R.id.btn_add);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("使用帮助");
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
    private void initViews() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListView.setGroupIndicator(null);
        expandableListView.setCacheColorHint(Color.TRANSPARENT);
        expandableListViewaAdapter = new ExpandableListViewaAdapter(HelpActivity.this);
//        expandableListView.setAdapter(new ExpandableListViewaAdapter(HelpActivity.this));
//        expandableListView.setAdapter(expandableListViewaAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                if (lastClick == -1) {
                    expandableListView.expandGroup(groupPosition);
                }
                if (lastClick != -1 && lastClick != groupPosition) {
                    expandableListView.collapseGroup(lastClick);
                    expandableListView.expandGroup(groupPosition);
                } else if (lastClick == groupPosition) {
                    if (expandableListView
                            .isGroupExpanded(groupPosition)) {
                        expandableListView
                                .collapseGroup(groupPosition);
                    } else if (!expandableListView
                            .isGroupExpanded(groupPosition))
                        expandableListView
                                .expandGroup(groupPosition);
                }
                lastClick = groupPosition;
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }
    class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
        Activity activity;

        public ExpandableListViewaAdapter(Activity a) {
            activity = a;
        }

        /*-----------------Child */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            if(childArray.get(groupPosition) != null){
                return childArray.get(groupPosition).get(childPosition);
            }else{
                return null;
            }

        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            if(childArray.get(groupPosition) != null){
                String string = childArray.get(groupPosition).get(childPosition);
                // 获取二级列表对应的布局文件, 并将其各元素设置相应的属性
                convertView = LayoutInflater.from(HelpActivity.this)
                        .inflate(R.layout.file_child_item, null);
                convertView.setFocusable(true);
                TextView tv = (TextView) convertView
                        .findViewById(R.id.child_textview);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                tv.setTextSize(14);
                tv.setText(string);
            }
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            // TODO Auto-generated method stub
            if(childArray.get(groupPosition) != null){
                return childArray.get(groupPosition).size();
            }else{
                return 0;
            }

        }

        /* ----------------------------Group */
        @Override
        public Object getGroup(int groupPosition) {
            // TODO Auto-generated method stub
            return getGroup(groupPosition);
        }

        @Override
        public int getGroupCount() {
            // TODO Auto-generated method stub
            return groupArray.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            // TODO Auto-generated method stub
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String string = groupArray.get(groupPosition);
            if(string.contains("@@")){
                convertView = LayoutInflater.from(HelpActivity.this)
                        .inflate(R.layout.file_group_item, null);
                TextView tv = (TextView) convertView
                        .findViewById(R.id.group_textview);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                tv.setTextSize(16);
                tv.setPadding(10, 15, 15, 15);
                tv.setTextColor(getResources().getColor(R.color.list_item));
                tv.setText(string.substring(2, string.length()));
            }else{
                convertView = LayoutInflater.from(HelpActivity.this)
                        .inflate(R.layout.file_group_item, null);
                TextView tv = (TextView) convertView
                        .findViewById(R.id.group_textview);
                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                tv.setTextSize(14);
                tv.setPadding(20, 10, 15, 10);
                tv.setTextColor(getResources().getColor(R.color.gray));
                tv.setText(string);
            }

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return true;
        }

    }

    private void showInfo(){
        groupArray = new ArrayList<String>();
        childArray = new ArrayList<List<String>>();
        initdate();//多语言数据绑定
        expandableListView.setAdapter(new ExpandableListViewaAdapter(
                HelpActivity.this));
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                if (lastClick == -1) {
                    expandableListView.expandGroup(groupPosition);
                }
                if (lastClick != -1 && lastClick != groupPosition) {
                    expandableListView.collapseGroup(lastClick);
                    expandableListView.expandGroup(groupPosition);
                } else if (lastClick == groupPosition) {
                    if (expandableListView
                            .isGroupExpanded(groupPosition)) {
                        expandableListView
                                .collapseGroup(groupPosition);
                    } else if (!expandableListView
                            .isGroupExpanded(groupPosition))
                        expandableListView
                                .expandGroup(groupPosition);
                }
                lastClick = groupPosition;
                return true;
            }
        });
    }

    private void initdate() {
        InputStream inputStream = null;
            inputStream = getResources().openRawResource(R.raw.help_zh);
        String[] strs = getString(inputStream).split("\n");
        int index = 0;
        for (int i = 0; i < strs.length; i++) {
            if(strs[i].contains("@@")){
                addInfo(strs[i], null);
                index++;
            }else{
                if ((i+index) % 2 == 0) {
                    addInfo(strs[i], new String[] { strs[i + 1] });
                }
            }

        }
    }
//添加数据
    private void addInfo(String group, String[] child) {

        groupArray.add(group);

        List<String> childItem = new ArrayList<String>();
        if(child == null){
            childArray.add(null);
        }else{
            for (int index = 0; index < child.length; index++) {
                String[] child_item = child[index].split("#");
                for (int j = 0; j < child_item.length; j++) {

                    childItem.add(child_item[j]);
                }
            }
            childArray.add(childItem);
        }
    }

    public static String getString(InputStream inputStream) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
