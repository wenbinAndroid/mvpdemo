package com.siso.libcommon.mvp.view;

import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.siso.libcommon.App;
import com.siso.libcommon.data.BaseEvent;
import com.siso.libcommon.data.StatusError;
import com.siso.libutils.RvViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */

public abstract class SisoFragment extends Fragment implements
        RvViewUtils.OnErrLoadingListener, BaseView, BaseListView, LifecycleOwner {

    protected View mRootView;
    public boolean isVisible;                  //是否可见状态
    protected boolean isPrepared;                 //标志位，View已经初始化完成。
    protected boolean isFirstLoad;
    protected int mCurrentPager = 1;
    protected int mNextPager = 2;
    protected AppCompatActivity mActivity;
    protected Toolbar mToolbar;
    private TextView mTvTitle;
    protected boolean isLoadingSucceed;
    Unbinder unbinder;

    public abstract int setLayout();


    protected void beforeInit(Bundle state) {

    }

    public abstract void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        isFirstLoad = true;
        isPrepared = true;
        mActivity = (AppCompatActivity) getActivity();
        mRootView = inflater.inflate(setLayout(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        beforeInit(savedInstanceState);
        isRegister();
        setLifecycleRegistry();
        init();
        lazyLoad();
        return mRootView;
    }

    private void isRegister() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void setToolbar(String title) {
        try {
            mToolbar = (Toolbar) mRootView.findViewById(com.siso.libcommon.R.id.toolbar);
            mTvTitle = (TextView) mRootView.findViewById(com.siso.libcommon.R.id
                    .tv_toolbar_title);
            mTvTitle.setText(title);
            mToolbar.setTitle("");
            mToolbar.setTitleTextColor(Color.WHITE);
            StatusBarUtil.setColor(mActivity, ContextCompat.getColor(mActivity, com.siso.libcommon.R
                    .color.colorPrimary), 0);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled
                    (true);
            mToolbar.setNavigationIcon(com.siso.libcommon.R.mipmap.ic_home_back);
        } catch (Exception e) {
            throw new IllegalStateException("toolbar may be null");
        }
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }

        isFirstLoad = false;
        initData();
    }

    protected void initData() {

    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }


    @Subscribe
    public void onEventMainThread(BaseEvent event) {


    }

    protected View getRvEmptyView(RecyclerView rv) {
        return RvViewUtils.getEmptyView(mActivity, rv);
    }

    protected View getRvErrView(RecyclerView rv) {
        return RvViewUtils.getInstant().setErrLoadingListener(this).getErrView(mActivity, rv);
    }

    @Override
    public void onRvErrLoading() {

    }

    protected void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册LifeCycle来监听生命周期
     */
    protected void setLifecycleRegistry() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(this);
        mActivity = null;

    }


    @Override
    public void onError(StatusError msg) {
        showToast(msg.errText);
    }

    @Override
    public void onErrorList(StatusError data) {

    }

    @Override
    public void setListData(List data) {

    }

    @Override
    public void setRefreshState() {

    }

}
