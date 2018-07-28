package com.siso.libcommon.mvp.model;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
public interface IModel {

    void onDestroy();

    void setTag(Object tag);
}
