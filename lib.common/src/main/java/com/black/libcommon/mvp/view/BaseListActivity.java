package com.black.libcommon.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.black.libcommon.data.Config;
import com.black.libcommon.data.StatusError;
import com.black.libcommon.mvp.presenter.IPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */

public abstract class BaseListActivity<K extends IPresenter, V extends BaseQuickAdapter>
        extends BlackActivity implements BaseListView, BaseQuickAdapter.OnItemChildClickListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    protected V mAdapter;
    protected K mPresenter;
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
    //是否开启默认列表动画(如果有进行移除或者重新加载的情况会出现闪屏)
    protected boolean isOpenDefaultAnmation = true;

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
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isResumeFlag) {
            isResumeFlag = true;
            getData();
        }
    }

    public abstract int onLayout();

    public abstract void initView();

    public abstract void setToolbar();

    //如果使用列表必须有返回值
    public abstract V getAdapter();

    //如果使用列表必须有返回值
    public abstract K getPresenter();

    //如果使用列表必须有返回值
    public abstract RecyclerView getRecycler();

    //用于下拉刷新view的状态改变
    public abstract void setRefreshState();

    //用于获取数据,加载更多和错误重新加载也是调用此接口
    public abstract void getData();

    protected void initAdapter() {
        if (getAdapter() != null && getRecycler() != null) {
            mRv = getRecycler();
            mAdapter = getAdapter();
            openDefaultAnimation();
            mRv.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener(this);
            mAdapter.setOnItemClickListener(this);
            if (isOpenLoadMore) {
                mAdapter.setOnLoadMoreListener(this, mRv);
            }
        }
    }

    /**
     * 列表默认动画
     */
    protected void openDefaultAnimation() {
        if (isOpenDefaultAnmation) {
            mAdapter.isFirstOnly(false);
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        }
    }

    /**
     * 设置数据
     *
     * @param data
     */
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

    /**
     * 列表错误加载
     *
     * @param data
     */
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
     * 列表item元素点击
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
        mPresenter = getPresenter();
        if (mPresenter != null) {
            getLifecycle().addObserver(mPresenter);
        }
    }

    /**
     * 错误界面的重新加载
     */
    @Override
    public void onRvErrLoading() {
        super.onRvErrLoading();
        getData();
    }
}
