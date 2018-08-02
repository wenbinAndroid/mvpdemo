package com.siso.libcommon.mvp.view;

import com.siso.libcommon.mvp.presenter.IPresenter;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */

public abstract class BaseActivity<T extends IPresenter> extends
        SisoActivity {

    protected T mPresenter;

    @Override
    protected void initToolbar() {
        setToolbar();
    }

    @Override
    protected int setLayout() {
        return onLayout();
    }

    @Override
    protected void init() {
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isResumeFlag) {
            isResumeFlag = true;
            initData();
        }
    }

    public abstract void initView();


    public abstract T createPresenter();

    public abstract void setToolbar();

    public abstract void initData();

    public abstract int onLayout();

    @Override
    protected void setLifecycleRegistry() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

}
