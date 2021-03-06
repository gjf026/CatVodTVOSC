package com.github.tvbox.osc.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import com.github.tvbox.osc.R;
import com.github.tvbox.osc.bean.SourceBean;

/**
 * @author pj567
 * @date :2020/12/23
 * @description:
 */
public class SourceSettingAdapter extends BaseQuickAdapter<SourceBean, BaseViewHolder> {
    public SourceSettingAdapter() {
        super(R.layout.item_source_setting_layout, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, SourceBean item) {
        TextView tvSourceAdd = helper.getView(R.id.tvSourceAdd);
        TextView tvSourceSpeed = helper.getView(R.id.tvSourceSpeed);
        TextView tvSource = helper.getView(R.id.tvSource);
        TextView tvSourceStatus = helper.getView(R.id.tvSourceStatus);
        if (item == SourceBean.addNewBean) {
            tvSourceAdd.setVisibility(View.VISIBLE);
            tvSource.setVisibility(View.GONE);
            tvSourceStatus.setVisibility(View.GONE);
            tvSourceSpeed.setVisibility(View.GONE);
            return;
        }
        if (item == SourceBean.speedTestBean) {
            tvSourceSpeed.setVisibility(View.VISIBLE);
            tvSource.setVisibility(View.GONE);
            tvSourceStatus.setVisibility(View.GONE);
            tvSourceAdd.setVisibility(View.GONE);
            return;
        }
        tvSourceSpeed.setVisibility(View.GONE);
        tvSourceAdd.setVisibility(View.GONE);
        tvSource.setVisibility(View.VISIBLE);
        tvSourceStatus.setVisibility(View.VISIBLE);
//        if (item.isHome()) {
//            tvSource.setTextColor(mContext.getResources().getColor(R.color.color_02F8E1));
//        } else {
//            tvSource.setTextColor(Color.WHITE);
//        }
        tvSource.setText(item.getName());

        if (item.isActive()) {
            tvSourceStatus.setTextColor(mContext.getResources().getColor(R.color.color_00FF0A));
        } else {
            tvSourceStatus.setTextColor(mContext.getResources().getColor(R.color.color_FF0057));
        }

        String status = item.isInternal() ? "???" : "";
        status += (item.isActive() ? "?????????" + (item.isHome() ? " ????????????" : "") : "?????????");
        tvSourceStatus.setText(status);
    }
}