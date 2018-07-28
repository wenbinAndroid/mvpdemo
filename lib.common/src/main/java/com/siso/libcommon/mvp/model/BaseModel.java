package com.siso.libcommon.mvp.model;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
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
