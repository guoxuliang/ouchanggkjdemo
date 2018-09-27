package com.example.ouc.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.MyOrderEntity;

import java.util.List;

public class MyOrderAdapter extends BaseAdapter{
private Context context;
private List<MyOrderEntity.DataBean> orderList;
    private  String name_str;
    private  String jlj_str;
    private  String syrw_str;
    private  String je_str;
    private LayoutInflater inflater;
    public MyOrderAdapter( Context context,List<MyOrderEntity.DataBean> orderList) {
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
            view = inflater.inflate(R.layout.layout_mylist_item, viewGroup, false);
            viewHolder.name_order = (TextView) view.findViewById(R.id.name_order);
            viewHolder.jlj_order = (TextView) view.findViewById(R.id.jlj_order);
            viewHolder.syrw_order = (TextView) view.findViewById(R.id.syrw_order);
            viewHolder.je_order = (TextView) view.findViewById(R.id.je_order);
            view.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (orderList != null) {

            for (int j=0; j<orderList.size();j++){
                name_str=orderList.get(i).getTitle();
                jlj_str= String.valueOf(orderList.get(i).getGold());
                 syrw_str= String.valueOf(orderList.get(i).getQuantity());
                  je_str= String.valueOf(orderList.get(i).getIntegral());
                viewHolder.name_order.setText(name_str+"");
                viewHolder.jlj_order.setText("奖励金:￥"+jlj_str+"元");
                viewHolder.syrw_order.setText("剩余任务:"+syrw_str+"");
                viewHolder.je_order.setText("+￥"+je_str+"");
            }
        }
        return view;
    }

    class ViewHolder {
        TextView name_order;
        TextView jlj_order;
        TextView syrw_order;
        TextView je_order;
    }
}
