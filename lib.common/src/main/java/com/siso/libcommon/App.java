package com.siso.libcommon;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


/**
 * @author Mrz
 * @date 2018/7/20 10:32
 */
public class App extends Application {

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        Utils.init(this);
    }

}
