package com.example.ouc.demo.adapter;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.RecommendedListEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;
/**
 * 基本使用
 */
public class ItemAdapterF2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 正在加载
    public final int LOADING = 1;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 3;
    private DisplayImageOptions build;
    private ArrayList<RecommendedListEntity.DataBean> datas;
    public ItemAdapterF2(ArrayList<RecommendedListEntity.DataBean> datas) {
        //构造方法传入LoadMoreAdapter数据源
        // 相当于原来adapter中的构造函数。
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        引入item布局得到view对象
//        创建iewHolder对象，引入item最终return当前viewholder对象
        // 通过判断显示类型，来创建不同的View    vieType就是int i
        if (i == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item2, viewGroup, false);
            return new RecyclerViewHolder(view);
        } else if (i == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_refresh_footer, viewGroup, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

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
//        通过position获取当前item数据给hodler对象内的控件set操作。
        if (viewHolder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder= (RecyclerViewHolder) viewHolder;
            Log.i("datas.size()","datas.size()'''"+datas.size());
            recyclerViewHolder.mTv1.setText(datas.get(i).getTitle());
            recyclerViewHolder.mTv2.setText("奖励金：￥" + datas.get(i).getGold());
            recyclerViewHolder.mTv3.setText("剩余任务：" + datas.get(i).getQuantity());
            recyclerViewHolder.mTv4.setText("已浏览：" + datas.get(i).getBrowsevolume());
//            ((NormalHolder) holder).getbutton.setText(datas.get(position).getQuantity());
            if (datas.get(i).getQuantity() == 0) {
                recyclerViewHolder.getbutton.setText("无偿广告");
            } else {
                recyclerViewHolder.getbutton.setText("有偿广告");
            }
            instance.displayImage(datas.get(i).getCover(), recyclerViewHolder.iv_imglist, build);

        }else if(viewHolder instanceof FootViewHolder){
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadState){
                case LOADING:// 正在加载
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:// 加载完成
                    footViewHolder.pbLoading.setVisibility(View.VISIBLE);
                    footViewHolder.tvLoading.setVisibility(View.VISIBLE);
                    footViewHolder.llEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END: // 加载到底
                    footViewHolder.pbLoading.setVisibility(View.GONE);
                    footViewHolder.tvLoading.setVisibility(View.GONE);
                    footViewHolder.llEnd.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //数据源size
        return datas.size()+1;
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    //TODO 数据源集合
    static class ViewHolder extends RecyclerView.ViewHolder {
        // item内的子控件
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //view.findviewbyid操作
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_imglist;
        TextView mTv1;
        TextView mTv2;
        TextView mTv3;
        TextView mTv4;
        TextView getbutton;

        RecyclerViewHolder(View itemView) {
            super(itemView);
           iv_imglist = (ImageView) itemView.findViewById(R.id.iv_imglist);
            mTv1 = (TextView) itemView.findViewById(R.id.mTv1);
            mTv2 = (TextView) itemView.findViewById(R.id.mTv2);
            mTv3 = (TextView) itemView.findViewById(R.id.mTv3);
            mTv4 = (TextView) itemView.findViewById(R.id.mTv4);
            getbutton = (TextView) itemView.findViewById(R.id.getbutton);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        TextView tvLoading;
        LinearLayout llEnd;

        FootViewHolder(View itemView) {
            super(itemView);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_loading);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading);
            llEnd = (LinearLayout) itemView.findViewById(R.id.ll_end);
        }
    }
}
