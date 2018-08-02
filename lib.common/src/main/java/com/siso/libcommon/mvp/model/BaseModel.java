package com.siso.libcommon.mvp.model;

import com.lzy.okgo.OkGo;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */
public class BaseModel implements IModel {




    @Override
    public void onDestroy() {
        OkGo.getInstance().cancelTag(this);
    }
}
