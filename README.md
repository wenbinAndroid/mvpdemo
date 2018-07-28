### 分享下自己使用的mvp结构文件,里面还要一些常用工具;

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

public interface IPresenter<V> extends LifecycleObserver {
    //使用Lifecycle来监测视图界面的生命周期并实现回调
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
        Log.e(TAG, "detachView: ");
        mModel.onDestroy();
        mModel = null;
        mView = null;
    }
```



