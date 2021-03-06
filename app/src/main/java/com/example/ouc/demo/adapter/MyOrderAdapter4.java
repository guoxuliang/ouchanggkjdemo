package com.example.ouc.demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.MyOrderEntity;
import com.example.ouc.demo.entity.ShareSumEntity;

import java.util.List;


public class MyOrderAdapter4 extends BaseAdapter{
    private Context context;
private List<ShareSumEntity.DataBean> orderList;
    private  String name_str;
    private  String jlj_str;
    private  String syrw_str;
    private  String je_str,fy_type;
    private LayoutInflater inflater;
    public MyOrderAdapter4(Context context, List<ShareSumEntity.DataBean> orderList) {
        this.orderList=orderList;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.layout_mylist_fy_item, viewGroup, false);
            viewHolder.name_order = (TextView) view.findViewById(R.id.name_order);
            viewHolder.syrw_order = (TextView) view.findViewById(R.id.syrw_order);
            viewHolder.je_order = (TextView) view.findViewById(R.id.je_order);
            viewHolder.syrw_type = (TextView) view.findViewById(R.id.syrw_type);
            view.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (orderList != null) {

            for (int j=0; j<orderList.size();j++){
                name_str=orderList.get(i).getTitle();
                 syrw_str= orderList.get(i).getCreateTime();//剩余任务---->返佣时间
                Log.i("",""+syrw_str);
                  je_str= String.valueOf(orderList.get(i).getMoney());//金额------>金额

                fy_type = orderList.get(i).getType();
                if(fy_type.equals("1")){
                    viewHolder.syrw_type.setText("任务返佣");
                }else if(fy_type.equals("2")){
                    viewHolder.syrw_type.setText("分享返佣");
                }else if(fy_type.equals("3")){
                    viewHolder.syrw_type.setText("充值返佣");
                }else if(fy_type.equals("4")){
                    viewHolder.syrw_type.setText("购买返佣");
                }
                viewHolder.name_order.setText(name_str+"");
                viewHolder.syrw_order.setText("返佣时间:"+syrw_str+"");
                viewHolder.je_order.setText("+￥"+je_str+"");
            }
        }
        return view;
    }

    class ViewHolder {
        TextView name_order;
        TextView syrw_order;
        TextView je_order,syrw_type;
    }
}
