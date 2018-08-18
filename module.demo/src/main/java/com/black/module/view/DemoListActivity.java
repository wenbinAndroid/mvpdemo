package com.black.module.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.black.libcommon.data.BaseInfo;
import com.black.libcommon.mvp.view.BaseListActivity;
import com.black.module.R;
import com.black.module.R2;
import com.black.module.adapter.DemoAdapter;
import com.black.module.contract.IDemoContract;
import com.black.module.presenter.DemoPresenter;

import java.util.ArrayList;

import butterknife.BindView;

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
    public void setList(BaseInfo info) {

    }

    @Override
    public void setData(String info) {

    }

}
