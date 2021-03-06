package com.example.ouc.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ouc.demo.R;
import com.example.ouc.demo.entity.RecordEntity;

import java.util.List;

/**
 * 基本使用
 */
public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private List<RecordEntity.DataBean> list;
    // 当前加载状态，默认为加载完成
    private int loadState = 3;


    public LoadMoreAdapter(List list) {
        //构造方法传入LoadMoreAdapter数据源
        // 相当于原来adapter中的构造函数。
        this.list = list;
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyle_item, viewGroup, false);
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
            RecyclerViewHolder recyclerViewHolder= (RecyclerViewHolder) viewHolder;
            recyclerViewHolder.tv_tx_name.setText(list.get(i).getUsername()+"");
           String type =  list.get(i).getType();
            String bankNumber = list.get(i).getBanknumber();
            String bankNumberzhf = list.get(i).getAlipayaccopunt();
            String balance = String.valueOf(list.get(i).getMoney());
            if(type.equals("1")){
                recyclerViewHolder.tv_tx_kahao.setText("银行卡号:"+bankNumber+"");
                recyclerViewHolder.tv_tx_jine.setText("金额:￥"+balance+"元");
            }else {
                recyclerViewHolder.tv_tx_kahao.setText("支付宝账号:"+bankNumberzhf+"");
                recyclerViewHolder.tv_tx_jine.setText("金额:￥"+balance+"元");
            }


            recyclerViewHolder.tv_tx_shijian.setText(list.get(i).getCashtime()+"");
            String type_zt = list.get(i).getStatus();
            if(!type_zt.equals("")){
                if(type_zt.equals("1")){
                    recyclerViewHolder.tv_zhuangt.setText("待处理");
                }else if(type_zt.equals("2")){
                    recyclerViewHolder.tv_zhuangt.setText("已受理");
                }else if(type_zt.equals("3")){
                    recyclerViewHolder.tv_zhuangt.setText("已撤销");
                }else if(type_zt.equals("4")){
                    recyclerViewHolder.tv_zhuangt.setText("已驳回");
                }
            }


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
        return list.size()+1;
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

        TextView tv_tx_name;
        TextView tv_tx_kahao;
        TextView tv_tx_jine;
        TextView tv_tx_shijian;
        TextView tv_zhuangt;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            tv_tx_name = (TextView) itemView.findViewById(R.id.tv_tx_name);
            tv_tx_kahao = (TextView) itemView.findViewById(R.id.tv_tx_kahao);
            tv_tx_jine = (TextView) itemView.findViewById(R.id.tv_tx_jine);
            tv_tx_shijian = (TextView) itemView.findViewById(R.id.tv_tx_shijian);
            tv_zhuangt = (TextView) itemView.findViewById(R.id.tv_zhuangt);
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
