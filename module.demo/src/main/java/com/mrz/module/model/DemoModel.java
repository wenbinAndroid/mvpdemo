package com.mrz.module.model;

import com.mrz.module.contract.IDemoContract;
import com.siso.libcommon.data.BaseInfo;
import com.siso.libcommon.http.callback.BaseCallback;
import com.siso.libcommon.mvp.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class DemoModel extends BaseModel implements IDemoContract.Model {
    private final int COUNT = 3;

    @Override
    public void getList(int pager, BaseCallback<List<String>> callback) {
        List<String> data = new ArrayList<>();
        if (pager < COUNT) {
            for (int i = 0; i < 10; i++) {
                data.add("");
            }
        }
        callback.onSuccess(data);
    }

    @Override
    public void getData(BaseCallback<BaseInfo> callback) {
        callback.onSuccess(new BaseInfo());
    }
}

