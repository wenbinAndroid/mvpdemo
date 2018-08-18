package com.black.module.model;

import com.black.libcommon.http.callback.BaseCallback;
import com.black.libcommon.http.callback.JsonCallback;
import com.black.libcommon.mvp.model.BaseModel;
import com.black.module.contract.IDemoContract;
import com.lzy.okgo.OkGo;

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
    //这边是一个无效的接口,只是为了示范,并没有实际的意义
    private static final String URL = "www.baidu.com";

    @Override
    public void getData(BaseCallback<String> callback) {
        OkGo.<String>get(URL)
                .tag(this)
                .execute(new JsonCallback<String>(String.class, callback) {
                });

    }
}

