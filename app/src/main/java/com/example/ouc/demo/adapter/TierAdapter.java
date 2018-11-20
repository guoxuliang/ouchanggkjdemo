package com.example.ouc.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.TierEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * 基本使用
 */
public class TierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private List<TierEntity.DataBean> list;
    // 当前加载状态，默认为加载完成
    private int loadState = 3;

    private Context context;
    public TierAdapter(List list, Context context) {
        //构造方法传入LoadMoreAdapter数据源
        // 相当于原来adapter中的构造函数。
        this.list = list;
        this.context = context;
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyle_item_dj, viewGroup, false);
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
//        通过position获取当前item数据给hodler对象内的控件set操作。
        if (viewHolder instanceof RecyclerViewHolder) {
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
                Glide.with(context). load(list.get(i).getHeadImg()+"").error( R.mipmap.icon_error)
                 .placeholder( R.mipmap.icon_error)
                        .fallback( R.mipmap.icon_error)
                            .into(((RecyclerViewHolder)viewHolder).headImg_pic);

            recyclerViewHolder.tv_name.setText(list.get(i).getUsername() + "");
            recyclerViewHolder.tv_phone.setText("电话:" + list.get(i).getMobilePhone() + "");
            recyclerViewHolder.tv_tjm.setText("推荐码:" + list.get(i).getCommendNo() + "");
            String tv_dj = list.get(i).getLevel();
            if(tv_dj.equals("1")){
                recyclerViewHolder.tv_dj.setText("普通会员");
            }else if(tv_dj.equals("2")){
                recyclerViewHolder.tv_dj.setText("超级会员");
            }else if(tv_dj.equals("3")){
                recyclerViewHolder.tv_dj.setText("白金会员");
            }else if(tv_dj.equals("4")){
                recyclerViewHolder.tv_dj.setText("VIP代理");
            }


        } else if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadState) {
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
        return list.size() + 1;
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
        ImageView headImg_pic;
        TextView tv_name;
        TextView tv_phone;
        TextView tv_tjm;
        TextView tv_dj;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            headImg_pic = (ImageView) itemView.findViewById(R.id.headImg_pic);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_tjm = (TextView) itemView.findViewById(R.id.tv_tjm);
            tv_dj = (TextView) itemView.findViewById(R.id.tv_dj);

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
