package com.siso.libcommon.http.callback;

import android.app.Activity;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.siso.libcommon.mvp.view.LoadingDialog;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {

    private LoadingDialog dialog;


    private void initDialog(Activity activity) {
        if (activity != null) {
            dialog = new LoadingDialog(activity);
        }
    }

    public DialogCallback(Activity activity, Class A, boolean isShow) {
        super(A);
        if (isShow) {
            initDialog(activity);
        } else {

        }
    }

    public DialogCallback(Activity activity, Class A, BaseCallback callback, boolean isShow) {
        super(A);
        this.mCallback = callback;
        if (isShow) {
            initDialog(activity);
        } else {

        }
    }

    public DialogCallback(Activity activity, Class A) {
        super(A);
        initDialog(activity);
    }

    public DialogCallback(Activity activity, Class A, BaseCallback callback) {
        super(A);
        initDialog(activity);
        this.mCallback = callback;
    }

    public void showDialog(Activity activity) {
        initDialog(activity);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        //网络请求结束后关闭对话框
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
    }

    @Override
    public void onSuccess(Response<T> response) {
        super.onSuccess(response);
    }
}