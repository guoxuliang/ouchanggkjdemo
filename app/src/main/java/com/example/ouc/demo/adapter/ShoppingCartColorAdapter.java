package com.example.ouc.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.ShoppingSpecsEntity;
import com.example.ouc.demo.utils.Constant;

import java.util.ArrayList;


public class ShoppingCartColorAdapter extends BaseAdapter {
    private LayoutInflater inflater;
private ArrayList<ShoppingSpecsEntity.DataBean> dataShopSpecs;
    private Context context;
    private GridView gridView;
    private int listColorSize;


    public ShoppingCartColorAdapter(Context context, ArrayList<ShoppingSpecsEntity.DataBean> dataShopSpecs, GridView gridView){
        this.context = context;
        this.dataShopSpecs = dataShopSpecs;
        this.gridView = gridView;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        listColorSize = dataShopSpecs.size();
        return dataShopSpecs.size();
    }

    @Override
    public String getItem(int i) {
        return String.valueOf(dataShopSpecs.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        String color = getItem(i);
//        String color =dataShopSpecs.get(i).getValue();
        Log.d("hxl","color222=="+color);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_shoppingcart_attribute,null);
            holder.rbAttribute = (RadioButton) convertView.findViewById(R.id.rb_attribute);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rbAttribute.setText(dataShopSpecs.get(i).getValue());
        Log.d("hxl","color=="+color);
        holder.rbAttribute.setTag("rbAttribute"+i);

        holder.rbAttribute.setOnClickListener(new MyOnclickListerner(i));

        return convertView;
    }
    class ViewHolder{
        RadioButton rbAttribute;
    }

    class MyOnclickListerner implements View.OnClickListener {
        private int currentPositionColor;
        public MyOnclickListerner(int i) {
            currentPositionColor = i;
        }

        @Override
        public void onClick(View view) {
            String name = null;
            String price=null;
            String img=null;
            RadioButton rbAttribute = (RadioButton) gridView.findViewWithTag("rbAttribute" + currentPositionColor);
                for(int i=0;i<listColorSize;i++){
                    RadioButton rbAttributeAll = (RadioButton) gridView.findViewWithTag("rbAttribute" + i);
                    rbAttributeAll.setChecked(false);

                }
            rbAttribute.setChecked(true);
            name=dataShopSpecs.get(currentPositionColor).getName();
            price= String.valueOf(dataShopSpecs.get(currentPositionColor).getPrice());
            img= String.valueOf(dataShopSpecs.get(currentPositionColor).getImg());
            sendBrodcastReceiver(rbAttribute.getText().toString(),name,price,img);
            //Toast.makeText(context,rbAttribute.getText(),Toast.LENGTH_SHORT).show();
        }

    }
    //发送广播给详情页面记录选中的颜色
    private void sendBrodcastReceiver(String str,String name,String price,String img){
        Intent intent = new Intent(Constant.SHOPPINGCART_COLORADAPTER_SEND_SHOPPINGCART_RECORD_COLOR);
        intent.putExtra("currentPositionColor",str);
        intent.putExtra("name",name);
        intent.putExtra("price",price);
        intent.putExtra("img",img);

        context.sendBroadcast(intent);
    }


}
