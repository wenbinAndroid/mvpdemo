### 分享下自己使用的mvp结构文件,里面还要一些常用工具;

#### 结构![MVP](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/A74F0D09-C4F5-4959-80DB-56B5EFF29EF3.png)

#### V层视图
##### 由于项目中使用了很多列表页面,每次都需要重新对列表进行各种判断代码太过于冗余,故将Activity和Fragment分为普通页面和普通列表页面,在内部进行了处理也暴露了相应的接口用来处理其他的事物.
#### V层接口

```java
public interface IView {

    void onError(StatusError msg);

    Lifecycle getLifecycle();

}

public interface BaseView extends IView {


}

public interface BaseListView extends IView {

    void onErrorList(StatusError data);

    void setListData(List data);

    void setRefreshState();

}
```

#### M层 ![](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/37962DEA-69DF-470B-8A16-F219D20F18C2.png)




