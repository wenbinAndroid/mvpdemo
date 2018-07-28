package com.siso.libcommon.mvp.view;

import com.siso.libcommon.mvp.presenter.IPresenter;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */
public abstract class BaseFragment<T extends IPresenter> extends SisoFragment {

    protected T mPresenter;

    @Override
    public int setLayout() {
        return onLayout();
    }

    @Override
    public void init() {
        init2();
    }

    public abstract int onLayout();

    public abstract void init2();

    public abstract T getPresenter();

    @Override
    protected void setLifecycleRegistry() {
        super.setLifecycleRegistry();
        mPresenter = getPresenter();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

}
