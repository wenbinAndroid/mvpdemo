package com.siso.libcommon.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.siso.libcommon.R;
import com.siso.libcommon.data.BaseEvent;
import com.siso.libcommon.data.Config;
import com.siso.libcommon.data.StatusError;
import com.siso.libutils.ActivityUtils;
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

public abstract class SisoActivity extends AppCompatActivity
        implements BaseView, BaseListView,
        RvViewUtils.OnErrLoadingListener {

    protected Toolbar mToolbar;
    protected Context mContext;
    //是否第一次加载
    protected boolean isFristLoading = true;
    //是否加载成功
    protected boolean isLoadingSucceed = true;
    //是否开启进入动画
    protected boolean isStartAnimation = true;
    //是否关闭退出动画
    protected boolean isFinishAnimation = true;
    //是否需要返回数据
    protected boolean isNeedActivityResult = false;

    protected TextView mTvToolbarTitle;
    //第一页
    protected int mCurrentPager = 1;
    //下一页
    protected int mNextPager = 2;
    //当前选中
    protected int mCurrentSelectPosition = -1;
    //
    protected boolean isResumeFlag = false;
    private Unbinder mBind;

    protected void beforeInit(Bundle state) {

    }

    protected abstract void initToolbar();

    protected abstract int setLayout();

    protected abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isRegister();
        setActivityAttr();
        setContentView(setLayout());
        mBind = ButterKnife.bind(this);
        beforeInit(savedInstanceState);
        initToolbar();
        setLifecycleRegistry();
        init();
    }

    /**
     * 注册EventBus
     */
    private void isRegister() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 设置Activity属性
     */
    private void setActivityAttr() {
        setRequestedOrientation(Config.ACTIVITY_ORIENTATION);
        ActivityUtils.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 设置toolbar
     *
     * @param title 标题(默认居中)
     */
    protected void setToolbar(String title) {
        try {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle("");
            mTvToolbarTitle = findViewById(R.id.tv_toolbar_title);
            mTvToolbarTitle.setText(title);
            mToolbar.setTitleTextColor(Color.WHITE);
            StatusBarUtil.setColor(this, ContextCompat.getColor(mContext, R.color.colorPrimary), 0);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.mipmap.ic_home_back);
        } catch (Exception e) {
            throw new IllegalStateException("toolbar may be null");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isNeedActivityResult) {
            setResult(RESULT_OK, new Intent());
            finish();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 获取数据错误回调
     *
     * @param msg
     */
    @Override
    public void onError(StatusError msg) {
        showToast(msg.errText);
    }

    /**
     * EventBus
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(BaseEvent event) {

    }

    /**
     * 注册LifeCycle来监听生命周期
     */
    protected void setLifecycleRegistry() {

    }

    /**
     * 显示toast
     *
     * @param text
     */
    protected void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取列表的空布局页面
     *
     * @param rv
     * @return
     */
    protected View getRvEmptyView(RecyclerView rv) {
        return RvViewUtils.getEmptyView(this, rv);
    }

    /**
     * 获取列表错误视图并添加重新点击的监听
     *
     * @param rv
     * @return
     */
    protected View getRvErrView(RecyclerView rv) {
        return RvViewUtils.getInstant().setErrLoadingListener(this).getErrView(this, rv);
    }

    /**
     * 列表错误界面点击回调
     */
    @Override
    public void onRvErrLoading() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        EventBus.getDefault().unregister(this);
        ActivityUtils.removeActivity(this);
        mContext = null;
    }
    //列表加载错误
    @Override
    public void onErrorList(StatusError data) {

    }
    //设置列表数据
    @Override
    public void setListData(List data) {

    }
    //改变刷新状态
    @Override
    public void setRefreshState() {

    }
}
