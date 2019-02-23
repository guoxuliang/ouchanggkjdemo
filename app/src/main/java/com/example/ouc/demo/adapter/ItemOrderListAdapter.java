package com.example.ouc.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.OrderListEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

public class ItemOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<OrderListEntity.DataBean> orderdatas;
    private Context context;
    private int normalType = 0;
    private int footType = 1;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private DisplayImageOptions build;

    public ItemOrderListAdapter(ArrayList<OrderListEntity.DataBean> orderdatas, Context context, boolean hasMore) {
        this.orderdatas = orderdatas;
        this.context = context;
        this.hasMore = hasMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.itemorderlist, null));
        } else {
            return new FootHolder(LayoutInflater.from(context).inflate(R.layout.footview, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ImageLoader instance = ImageLoader.getInstance();
        build = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//使用内存缓存
                .cacheOnDisk(true)//使用磁盘缓存
                .showImageOnLoading(R.mipmap.icon_error)//设置正在下载的图片
                .showImageForEmptyUri(R.mipmap.icon_error)//url为空或请求的资源不存在时
                .showImageOnFail(R.mipmap.icon_error)//下载失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片色彩模式 1px=2字节
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片的缩放模式
                .displayer(new RoundedBitmapDisplayer(10))//设置圆角 30代表半径 自定义
                .build();
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).gmshopName.setText(orderdatas.get(position).getShopname());
            ((NormalHolder) holder).gmjiage.setText("合计:￥"+orderdatas.get(position).getMoney()+"");
            ((NormalHolder) holder).gmkcun.setText("规格："+orderdatas.get(position).getInfo()+"");
//            ((NormalHolder) holder).tv_jlijin.setText("奖励金：￥" + datas.get(position).getGold());
            instance.displayImage(orderdatas.get(position).getBannerone(), ((NormalHolder) holder).gmshopimg, build);
        } else {
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            if (hasMore == true) {
                fadeTips = false;
                if (orderdatas.size() > 0) {
                    ((FootHolder) holder).tips.setText("正在加载更多...");

                }
            } else {
                if (orderdatas.size() > 0) {
                    ((FootHolder) holder).tips.setText("没有更多数据了");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            fadeTips = true;
                            hasMore = true;
                        }
                    }, 500);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderdatas.size() + 1;
    }

    public int getRealLastPosition() {
        return orderdatas.size();
    }


    public void updateList(ArrayList<OrderListEntity.DataBean> newDatas, boolean hasMore) {
        if (newDatas != null) {
            orderdatas.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private ImageView gmshopimg;
        private TextView gmshopName,gmkcun,gmjiage;

        public NormalHolder(View itemView) {
            super(itemView);
            gmshopimg = (ImageView) itemView.findViewById(R.id.gmshopimg);
            gmshopName = (TextView) itemView.findViewById(R.id.gmshopName);
            gmkcun = (TextView) itemView.findViewById(R.id.gmkcun);
            gmjiage = (TextView) itemView.findViewById(R.id.gmjiage);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
            tips.setVisibility(View.GONE);
        }
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void resetDatas() {
        orderdatas = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

//    public interface OnItemClickListener {
//        void onClick(int position);
//
//        void onLongClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }
}
