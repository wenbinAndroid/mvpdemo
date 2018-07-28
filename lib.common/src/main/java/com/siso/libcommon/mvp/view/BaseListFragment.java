package com.siso.libcommon.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.siso.libcommon.data.Config;
import com.siso.libcommon.data.StatusError;
import com.siso.libcommon.mvp.presenter.IPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */

public abstract class BaseListFragment<T extends IPresenter, K extends BaseQuickAdapter> extends
        SisoFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter
        .OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, BaseListView {
    protected T mPresenter;
    protected K mAdapter;
    protected RecyclerView mRv;
    //分页加载大小
    protected int pagerSize = Config.PAGER_SIZE;
    //是否显示列表尾部的没有更多数据样式
    protected boolean isHideListFooter = false;
    //打开加载更多
    protected boolean isOpenLoadMore = true;
    //是否显示重新加载视图
    protected boolean isOpenErrorView = true;
    //是否显示加载错误的信息提示
    protected boolean isShowLoadingErrorText = true;

    @Override
    public int setLayout() {
        return onLayout();
    }

    @Override
    public void init() {
        init2();
        initAdapter();
        getData();
    }

    public abstract int onLayout();

    public abstract void init2();

    public abstract K getAdapter();

    public abstract RecyclerView getRecycler();

    //获取数据
    public abstract void getData();

    public abstract void setRefreshState();

    public abstract T createPresenter();


    protected void initAdapter() {
        if (getAdapter() != null && getRecycler() != null) {
            mRv = getRecycler();
            mAdapter = getAdapter();
            mRv.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener(this);
            mAdapter.setOnItemClickListener(this);
            if (isOpenLoadMore) {
                mAdapter.setOnLoadMoreListener(this, mRv);
            }
        }
    }

    @Override
    public void setListData(List data) {
        if (mCurrentPager == 1) {
            setRefreshState();
            if ((data == null || data.size() == 0)) {
                showEmptyView();
                return;
            }
            mAdapter.setNewData(data);
            mCurrentPager = 1;
            mNextPager = 2;
        } else {
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
            mNextPager++;
        }
        if (data.size() < pagerSize) {
            mAdapter.loadMoreEnd(isHideListFooter);
        }
    }

    @Override
    public void onErrorList(StatusError data) {
        if (isShowLoadingErrorText) showToast(data.errText);
        if (mCurrentPager > 1) {
            mAdapter.loadMoreFail();
        } else {
            if (isOpenErrorView) showErrView();

        }
    }

    /**
     * 显示错误视图
     */
    protected void showErrView() {
        mAdapter.setNewData(new ArrayList());
        mAdapter.setEmptyView(getRvErrView(mRv));
    }

    /**
     * 显示空视图
     */
    protected void showEmptyView() {
        mAdapter.setNewData(new ArrayList());
        mAdapter.setEmptyView(getRvEmptyView(mRv));
    }

    /**
     * 列表item子类点击
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 列表item点击
     *
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        mCurrentPager = mNextPager;
        getData();
    }

    /**
     * 注册生命观察者
     */
    @Override
    protected void setLifecycleRegistry() {
        super.setLifecycleRegistry();
        mPresenter = createPresenter();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

    @Override
    public void onRvErrLoading() {
        super.onRvErrLoading();
        getData();
    }
}
