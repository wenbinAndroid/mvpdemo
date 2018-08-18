package com.black.module.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.black.libcommon.data.BaseInfo;
import com.black.libcommon.mvp.view.BaseActivity;
import com.black.module.R;
import com.black.module.R2;
import com.black.module.contract.IDemoContract;
import com.black.module.presenter.DemoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class DemoActivity extends BaseActivity<DemoPresenter> implements IDemoContract.View {

    @BindView(R2.id.tv)
    TextView mTv;

    @Override
    public void initView() {

    }

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
    public void setList(BaseInfo info) {

    }

    @Override
    public void setData(String info) {

    }

    public void start(View view) {
        mPresenter.getData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

