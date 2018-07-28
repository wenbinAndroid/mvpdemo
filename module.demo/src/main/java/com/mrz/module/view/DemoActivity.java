package com.mrz.module.view;

import com.mrz.module.R;
import com.mrz.module.contract.IDemoContract;
import com.mrz.module.presenter.DemoPresenter;
import com.siso.libcommon.data.BaseInfo;
import com.siso.libcommon.mvp.view.BaseActivity;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class DemoActivity extends BaseActivity<DemoPresenter> implements IDemoContract.View {

    @Override
    public DemoPresenter createPresenter() {
        return new DemoPresenter(this);
    }

    @Override
    public void setToolbar() {
        setToolbar(getString(R.string.demo));
    }

    @Override
    public void initData() {

    }

    @Override
    public int onLayout() {
        return R.layout.activity_demo;
    }

    @Override
    public void setData(BaseInfo info) {

    }
}

