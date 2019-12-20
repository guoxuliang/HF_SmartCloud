//package com.hf.hf_smartcloud.adapter;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.hf.hf_smartcloud.R;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MyAdapter extends BaseAdapter {
//    private List<String> listText;
//    private Context context;
//    private Map<Integer, Boolean> map=new HashMap<>();
//    private int screenWidth;
//    public MyAdapter(List<String> listText, Context context){
//        this.listText=listText;
//        this.context=context;
//        this.screenWidth = screenWidth;
//    }
//    @Override
//    public int getCount() {
//        //return返回的是int类型，也就是页面要显示的数量。
//        return listText.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView==null){
//            //通过一个打气筒 inflate 可以把一个布局转换成一个view对象
//            convertView= View.inflate(context,R.layout.list_item,null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        }else {
//            holder = (ViewHolder) convertView.getTag();
//        }
////        ViewGroup.LayoutParams layoutParams = holder.itemlayout.getLayoutParams();
////        layoutParams.width = screenWidth / 7 * 2;
////        holder.itemlayout.setLayoutParams(layoutParams);
//
//
//        //单选按钮的文字
////        ImageView ItemImage=(ImageView)view.findViewById(R.id.ItemImage);
////        TextView tvCity=(TextView)view.findViewById(R.id.tvCity);
////        TextView tvCode=(TextView)view.findViewById(R.id.tvCode);
//
//        holder.tvCity.setText(listText.get(position));
//        //单选按钮
//        final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.radio);
//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkBox.isChecked()){
//                    map.put(position,true);
//
//                }else {
//                    map.remove(position);
//
//                }
//            }
//        });
//        if(map!=null&&map.containsKey(position)){
//            checkBox.setChecked(true);
//        }else {
//            checkBox.setChecked(false);
//        }
//        return convertView;
//    }
//
//    static class ViewHolder {
//        LinearLayout itemlayout;
//        TextView tvCity,tvCode;
//        ImageView ItemImage;
//        CheckBox radio;
//        ViewHolder(View view) {
//            itemlayout = view.findViewById(R.id.itemlayout);
//            tvCity = view.findViewById(R.id.tvCity);
//            tvCode = view.findViewById(R.id.tvCity);
//            ItemImage = view.findViewById(R.id.ItemImage);
////            radio = view.findViewById(R.id.radio);
//
//
//        }
//    }
//}
