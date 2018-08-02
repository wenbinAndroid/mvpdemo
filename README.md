### 分享下自己使用的mvp结构文件,里面还有一些常用工具;

#### 结构![MVP](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/A74F0D09-C4F5-4959-80DB-56B5EFF29EF3.png)


##### 由于项目中使用了很多列表页面,每次都需要重新对列表进行各种判断代码太过于冗余,故将Activity和Fragment都分为普通页面和普通列表页面单独封装,回调接口也是如此,在内部进行了处理也暴露了相应的接口用来处理其他的事物.

#### M层结构
```java

public interface IModel {
    //取消资源的调用
    void onDestroy();
   
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

### 使用方法  

#### 使用[MVP插件](https://github.com/longforus/MvpAutoCodePlus)可以快速生成结构,这是我的配置信息

![配置](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/B1544D6F-A5A4-45BB-831E-093FC4617848.png)


#### 普通的视图界面
```java
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

   }

```

#### 普通的列表页面,设置数据只要调用setListData方法,其余的列表空数据界面,错误界面,错误界面重新加载都进行了相应的封装,不需要写太多的代码,都在内部进行了判断
```java
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
    //列表对应的适配器
    @Override
    public DemoAdapter getAdapter() {
        return new DemoAdapter(new ArrayList<String>());
    }
    
    @Override
    public DemoPresenter getPresenter() {
        return new DemoPresenter(this);
    }
    
    //列表对应的Recyclerview
    @Override
    public RecyclerView getRecycler() {
        return mRecycler;
    }

    //用于下拉刷新重置下拉视图的状态
    @Override
    public void setRefreshState() {

    }

    //获取数据(上拉加载更多,错误点击也是调用这个方法)
    @Override
    public void getData() {
        mPresenter.getList(mCurrentPager);
    }
    
}

```

#### 注意事项
##### 1.如果在模块使用了Butterknife,则该模块也必须要配置参数,不能只在common中配置,不然会出现空指针的情况

##### 2.如果要调用model的东西,则必须要返回presenter,如果不使用则不必返回

##### 3.列表页面的获取数据必须写在getData方法中,不然会出现错误,因为加载更多,点击重新加载都会调用这个方法

##### 4.使用插件生成结构的话,Activity必须要在清单文件中注册,不然爆找不到的异常

##### 5.utls的引用了一些库都是一些常用的库,如果不使用可以去除.




