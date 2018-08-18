package com.black.libcommon.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.black.libcommon.R;


/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        View view = View.inflate(context, R.layout.common_dialog_loadding, null);
        setContentView(view);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }
        setCancelable(true);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
