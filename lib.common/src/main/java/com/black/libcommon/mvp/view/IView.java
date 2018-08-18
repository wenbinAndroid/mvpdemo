package com.black.libcommon.mvp.view;

import android.arch.lifecycle.Lifecycle;

import com.black.libcommon.data.StatusError;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */

public interface IView {

    void onError(StatusError msg);

    Lifecycle getLifecycle();

}
