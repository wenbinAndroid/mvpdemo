package com.black.libutils;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 * 配合adapter使用,优化了一些常用方法
 */

public class CommonViewHolder extends BaseViewHolder {
    private static final String NULL = "null";

    public CommonViewHolder(View view) {
        super(view);
    }

    @Override
    public BaseViewHolder setText(int viewId, CharSequence value) {
        String text = value.toString();
        if (TextUtils.equals(text, NULL)) {
            text = "";
        }
        return super.setText(viewId, text);
    }

    @Override
    public BaseViewHolder setTextColor(int viewId, int textColor) {

        int color = ContextCompat.getColor(convertView.getContext(), textColor);
        return super.setTextColor(viewId, color);
    }
}
