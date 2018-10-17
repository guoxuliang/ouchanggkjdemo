package com.example.ouc.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.example.ouc.demo.ui.activity.AdvertisingVideoActivity;
import com.example.ouc.demo.utils.ToastHelper;
import com.example.ouc.demo.utils.ViewHolder;

import java.util.List;


public class MyAdapter extends MyBaseAdapter<RecommendedListEntity.DataBean> {
private Context context;
    public MyAdapter(List<RecommendedListEntity.DataBean> data,Context context) {
        super(data);
        this.context=context;
    }
    @Override
    public void setData(ViewHolder holder, RecommendedListEntity.DataBean t) {
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()); int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, context.getResources().getDisplayMetrics());
        Glide.with(context).load(t.getCover()).override(width,height).into((ImageView) holder.getView(R.id.iv_imglist));
        holder.setText(R.id.mTv1, t.getTitle());
        holder.setText(R.id.mTv2, "奖励金：￥"+t.getGold());
        holder.setText(R.id.mTv4, "已浏览："+t.getBrowsevolume()+"w");
        holder.setText(R.id.mTv3, "剩余任务："+t.getQuantity());
        holder.setText(R.id.getbutton, "有偿广告");
        double money=t.getGold();
//        holder.getView(R.id.getbutton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ToastHelper.show(context,"任务已领取");
////                Intent intent=new Intent(context, AdvertisingVideoActivity.class);
////                context.startActivity(intent);
//            }
//        });
    }
}



//public class MyAdapter extends BaseAdapter {
//
//    private List<RecommendedListEntity.DataBean> dataBeans = new ArrayList<>();
//    private LayoutInflater inflater;
//    private Context context;
//    private String mTv1,mTv2,mTv3,mTv4,iv_imglist;
//    public MyAdapter(List<RecommendedListEntity.DataBean> dataBeans, Context context) {
////public SsAdapter(ArrayList<SsBean.DataEntity.RaceListEntity> venList, Context context) {
//        this.dataBeans = dataBeans;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return dataBeans.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return dataBeans.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder = null;
//        if (view == null) {
//            viewHolder = new ViewHolder();
//            view = inflater.inflate(R.layout.list_item, viewGroup, false);
//            viewHolder.mTv1 = (TextView) view.findViewById(R.id.mTv1);
//            viewHolder.mTv2 = (TextView) view.findViewById(R.id.mTv2);
//            viewHolder.mTv3 = (TextView) view.findViewById(R.id.mTv3);
//            viewHolder.mTv4 = (TextView) view.findViewById(R.id.mTv4);
//            viewHolder.iv_imglist = (ImageView) view.findViewById(R.id.iv_imglist);
//            viewHolder.getbutton = (Button) view.findViewById(R.id.getbutton);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//        viewHolder.getbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO
//                ToastHelper.show(context,"领取任务");
//            }
//        });
//        if (dataBeans != null) {
//
//            for (int j=0; j<dataBeans.size();j++){
//                mTv1=dataBeans.get(i).getTitle();
//                mTv2=dataBeans.get(i).getGold()+"";
//                mTv3=dataBeans.get(i).getQuantity()+"";
//                mTv4=dataBeans.get(i).getBrowsevolume();
//                iv_imglist=dataBeans.get(i).getCover();
//                viewHolder.mTv1.setText(mTv1);
//                viewHolder.mTv2.setText(mTv2);
//                viewHolder.mTv3.setText(mTv3);
//                viewHolder.mTv4.setText(mTv4);
//                Glide.with(context).load(iv_imglist).into(viewHolder.iv_imglist);
//
//            }
//        }
//        return view;
//    }
//    class ViewHolder {
//        ImageView iv_imglist;
//        TextView mTv1;
//        TextView mTv2;
//        TextView mTv3;
//        TextView mTv4;
//        Button getbutton;
//    }
//}