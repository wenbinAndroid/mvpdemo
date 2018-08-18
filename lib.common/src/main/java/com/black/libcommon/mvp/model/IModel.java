package com.black.libcommon.mvp.model;

import android.app.Activity;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */
public interface IModel {

    void onDestroy();

    void setActivity(Activity activity);

}
