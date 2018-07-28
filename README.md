### 分享下自己使用的mvp结构文件,里面还要一些常用工具;

#### 结构![MVP](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/A74F0D09-C4F5-4959-80DB-56B5EFF29EF3.png)

#### V层
##### 由于项目中使用了很多列表页面,每次都需要重新对列表进行各种判断代码太过于冗余,故将Activity和Fragment分为普通页面和普通列表页面,在内部进行了处理也暴露了相应的接口用来处理其他的事物.![SisoActivity](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/89D2AAE3-E292-4EC8-95C7-38CF635C066F.png)

#### ![BaseView](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/C2ACD235-54BE-4823-AEA9-429700E2D446.png)
#### ![BaseListview](https://github.com/wenbinAndroid/mvpdemo/blob/master/image/F0196300-C637-4998-94EB-957A38DB8C00.png)



