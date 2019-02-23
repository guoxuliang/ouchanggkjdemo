package com.example.ouc.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.ShoppingCategoryEntity;
import com.example.ouc.demo.entity.ShoppingSpecsEntity;
import com.example.ouc.demo.utils.Constant;

import java.util.ArrayList;


public class ShoppingCategoryAdapter extends BaseAdapter {
    private LayoutInflater inflater;
private ArrayList<ShoppingCategoryEntity.DataBean> shoppingCategoryData;
    private Context context;
    private GridView gridView;
    private int listColorSize;


    public ShoppingCategoryAdapter(Context context, ArrayList<ShoppingCategoryEntity.DataBean> shoppingCategoryData, GridView gridView){
        this.context = context;
        this.shoppingCategoryData = shoppingCategoryData;
        this.gridView = gridView;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        listColorSize = shoppingCategoryData.size();
        return shoppingCategoryData.size();
    }

    @Override
    public String getItem(int i) {
        return String.valueOf(shoppingCategoryData.get(i));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        String color = getItem(i);
        Log.d("hxl","color=="+color);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gradview,null);
            holder.tv_categoryname = (TextView) convertView.findViewById(R.id.tv_categoryname);
            holder.iv_categoryimg = (ImageView) convertView.findViewById(R.id.iv_categoryimg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_categoryname.setText(shoppingCategoryData.get(i).getDICT_DETAIL_NAME());
//        Glide.with(context).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548505825727&di=f2cc5cdb6342c83fb00b31af5a4dccff&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201502%2F11%2F20150211134734_LN5nz.jpeg").into(holder.iv_categoryimg);
        Glide.with(context).load(shoppingCategoryData.get(i).getDetail_img()).into(holder.iv_categoryimg);


//        Log.d("hxl","color=="+color);
//        holder.tv_categoryname.setTag("tv_categoryname"+i);
//        holder.tv_categoryname.setOnClickListener(new MyOnclickListerner(i));

        return convertView;
    }
    class ViewHolder{
        TextView tv_categoryname;
        ImageView iv_categoryimg;
    }

//    class MyOnclickListerner implements View.OnClickListener {
//        private int currentPositionColor;
//        public MyOnclickListerner(int i) {
//            currentPositionColor = i;
//        }
//
//        @Override
//        public void onClick(View view) {
//            String name = null;
//            String price=null;
//            String img=null;
//            TextView tv_categoryname = (TextView) gridView.findViewWithTag("tv_categoryname" + currentPositionColor);
//                for(int i=0;i<listColorSize;i++){
//                    TextView tv_categorynameAll = (TextView) gridView.findViewWithTag("tv_categoryname" + i);
//
//                }
//            name=shoppingCategoryData.get(currentPositionColor).getDICT_DETAIL_NAME();
////            price= String.valueOf(shoppingCategoryData.get(currentPositionColor).getPrice());
////            img= String.valueOf(shoppingCategoryData.get(currentPositionColor).getImg());
//            sendBrodcastReceiver(tv_categoryname.getText().toString(),name,price,img);
//            //Toast.makeText(context,tv_categoryname.getText(),Toast.LENGTH_SHORT).show();
//        }
//
//    }
    //发送广播给详情页面记录选中的颜色
//    private void sendBrodcastReceiver(String str,String name,String price,String img){
//        Intent intent = new Intent(Constant.SHOPPINGCART_COLORADAPTER_SEND_SHOPPINGCART_RECORD_COLOR);
//        intent.putExtra("currentPositionColor",str);
//        intent.putExtra("name",name);
//        intent.putExtra("price",price);
//        intent.putExtra("img",img);
//
//        context.sendBroadcast(intent);
//    }


}
