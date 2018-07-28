package com.mrz.module.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mrz.module.R;
import com.siso.libutils.CommonViewHolder;

import java.util.List;

/**
 * @author Mrz
 * @date 2018/7/28 11:49
 */
public class DemoAdapter extends BaseQuickAdapter<String,CommonViewHolder> {
    public DemoAdapter(@Nullable List<String> data) {
        super(R.layout.item_demo,data);
    }

    @Override
    protected void convert(CommonViewHolder helper, String item) {

    }
}
