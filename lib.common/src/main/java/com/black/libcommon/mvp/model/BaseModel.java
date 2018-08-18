package com.black.libcommon.mvp.model;

import android.app.Activity;

import com.lzy.okgo.OkGo;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
public class BaseModel implements IModel {



    public Activity mActivity;

    @Override
    public void onDestroy() {
        //取消网络调用
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }
}
