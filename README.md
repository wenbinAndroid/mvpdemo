### 分享下自己使用的mvp结构文件,里面还有一些常用工具;

#### 结构![MVP](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/A74F0D09-C4F5-4959-80DB-56B5EFF29EF3.png)


##### 由于项目中使用了很多列表页面,每次都需要重新对列表进行各种判断代码太过于冗余,故将Activity和Fragment分为普通页面和普通列表页面,回调接口也是如此,在内部进行了处理也暴露了相应的接口用来处理其他的事物.

#### M层结构
```java

public interface IModel {
    //终止生命周期
    void onDestroy();
    //用于终止连接状态的tag,防止页面已经销毁还进行页面的刷新操作
    void setTag(Object tag);
}

public class BaseModel implements IModel {
    
    protected Object tag;

    @Override
    public void setTag(Object tag) {
        this.tag = tag;
    }

    @Override
    public void onDestroy() {
        tag = null;
    }
}
```

#### V层结构

```java
public interface IView {
    //普通错误回调
    void onError(StatusError msg);
    //生命周期监测
    Lifecycle getLifecycle();

}

public interface BaseView extends IView {


}

public interface BaseListView extends IView {
    //列表加载错误
    void onErrorList(StatusError data);
    //设置列表数据
    void setListData(List data);
    //用于重置下拉刷新状态
    void setRefreshState();

}
```

#### P层结构

```java
//使用Lifecycle来监测视图界面的生命周期并实现回调
public interface IPresenter<V> extends LifecycleObserver {
   
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void detachView();
}

//主要提供一个中间人的工作,防止view和model直接接触,逻辑也是放在P层中处理即可
public abstract class BasePresenter<T extends IModel, V extends IView>
        implements IPresenter<V> {
    //model
    private T mModel;
    //view
    private V mView;
    private static final String TAG = "BasePresenter";

    public BasePresenter(V view) {
        this.mView = view;
        this.mModel = createModel();
        mModel.setTag(view);
    }

    protected abstract T createModel();

    protected V getView() {
        if (mView == null)
            throw new IllegalStateException("view must be not null");

        return mView;
}

  protected T getModel() {
        if (mModel == null)
            throw new IllegalStateException("model must be not null");
        return mModel;
    }
    
    //页面销毁回调
    @Override
    public void detachView() {
    //移除生命周期的监听,并停止网络的加载,防止内存泄漏
        if (getView() != null && getView().getLifecycle() != null) {
            getView().getLifecycle().removeObserver(this);
        }
        mModel.onDestroy();
        mModel = null;
        mView = null;
    }
```

#### 普通的视图界面
```java
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
        initData();
    }

    public abstract T createPresenter();

    public abstract void setToolbar();

    public abstract void initData();

    public abstract int onLayout();
    
    @Override
    protected void setLifecycleRegistry() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
        //注册生命周期的监听
            getLifecycle().addObserver(mPresenter);
        }
    }

}


```

#### 普通的列表页面,则相对要负责一点

```java
public abstract class BaseListActivity<K extends IPresenter, V extends BaseQuickAdapter>
        extends SisoActivity implements BaseListView, BaseQuickAdapter.OnItemChildClickListener,
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
    //是否开启默认列表动画
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
        getData();
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
```



