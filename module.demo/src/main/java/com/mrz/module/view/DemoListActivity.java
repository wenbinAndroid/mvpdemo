package com.mrz.module.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mrz.module.R;
import com.mrz.module.R2;
import com.mrz.module.adapter.DemoAdapter;
import com.mrz.module.contract.IDemoContract;
import com.mrz.module.presenter.DemoPresenter;
import com.siso.libcommon.data.BaseInfo;
import com.siso.libcommon.mvp.view.BaseListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoListActivity extends BaseListActivity<DemoPresenter, DemoAdapter> implements IDemoContract.View {


    @BindView(R2.id.recycler)
    RecyclerView mRecycler;

    @Override
    public int onLayout() {
        return R.layout.activity_demo_list;
    }

    @Override
    public void initView() {

        mRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setToolbar() {
        setToolbar(getString(R.string.demo_list));
    }

    @Override
    public DemoAdapter getAdapter() {
        return new DemoAdapter(new ArrayList<String>());
    }

    @Override
    public DemoPresenter getPresenter() {
        return new DemoPresenter(this);
    }

    @Override
    public RecyclerView getRecycler() {
        return mRecycler;
    }

    //设置刷新状态
    @Override
    public void setRefreshState() {

    }

    //获取数据
    @Override
    public void getData() {
        mPresenter.getList(mCurrentPager);
    }

    @Override
    public void setData(BaseInfo info) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
