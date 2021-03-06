package com.black.libcommon.mvp.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.black.libcommon.mvp.model.IModel;
import com.black.libcommon.mvp.view.IView;


/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */

public abstract class BasePresenter<T extends IModel, V extends IView>
        implements IPresenter<V> {
    private T mModel;
    private V mView;
    private static final String TAG = "BasePresenter";

    public BasePresenter(V view) {
        this.mView = view;
        this.mModel = createModel();
        Activity context;
        if (view instanceof Fragment) {
            context = ((Fragment) view).getActivity();
        } else {
            context = (Activity) view;
        }
        mModel.setActivity(context);
    }

    protected abstract T createModel();

    protected V getView() {
        if (mView == null)
            throw new IllegalStateException("view must be not null");

        return mView;
    }

    protected T getModel() {
        if (mModel == null)
            throw new IllegalStateException("model must be not null");
        return mModel;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop: ");
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void detachView() {
        if (getView() != null && getView().getLifecycle() != null) {
            getView().getLifecycle().removeObserver(this);
        }
        Log.e(TAG, "detachView: ");
        mModel.onDestroy();
        mModel = null;
        mView = null;
    }
}
