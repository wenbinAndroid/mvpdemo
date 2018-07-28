package com.siso.libcommon.http.callback;

import com.siso.libcommon.data.StatusError;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */

public interface BaseCallback<T> {

    void onSuccess(T t);
    void onError(StatusError error);
}
