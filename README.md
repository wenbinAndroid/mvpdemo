### 分享下自己使用的mvp结构文件,里面还要一些常用工具;

#### 结构![MVP](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/A74F0D09-C4F5-4959-80DB-56B5EFF29EF3.png)

#### V层视图
##### 由于项目中使用了很多列表页面,每次都需要重新对列表进行各种判断代码太过于冗余,故将Activity和Fragment分为普通页面和普通列表页面,在内部进行了处理也暴露了相应的接口用来处理其他的事物.
#### V层接口

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




