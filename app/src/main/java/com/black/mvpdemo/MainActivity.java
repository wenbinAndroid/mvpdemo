package com.black.mvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.black.module.view.DemoActivity;
import com.black.module.view.DemoListActivity;
import com.black.libcommon.mvp.presenter.IPresenter;
import com.black.libcommon.mvp.view.BaseActivity;
import com.mrz.mvpdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.tv_normal)
    TextView mTvNormal;
    @BindView(R.id.tv_list)
    TextView mTvList;

    @Override
    public void initView() {

    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    public void setToolbar() {
        setToolbar("Demo");
    }

    @Override
    public void initData() {

    }

    @Override
    public int onLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_normal, R.id.tv_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_normal:
                startActivity(new Intent(mContext, DemoActivity.class));
                break;
            case R.id.tv_list:
                startActivity(new Intent(mContext, DemoListActivity.class));
                break;
        }
    }
}
