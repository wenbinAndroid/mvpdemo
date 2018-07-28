package com.mrz.module.presenter;

import com.mrz.module.contract.IDemoContract;
import com.mrz.module.model.DemoModel;
import com.siso.libcommon.data.BaseInfo;
import com.siso.libcommon.data.StatusError;
import com.siso.libcommon.http.callback.BaseCallback;
import com.siso.libcommon.mvp.presenter.BasePresenter;

import java.util.List;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class DemoPresenter extends BasePresenter<IDemoContract.Model, IDemoContract.View> implements IDemoContract.Presenter {

    public DemoPresenter(IDemoContract.View view) {
        super(view);
    }

    @Override
    protected IDemoContract.Model createModel() {
        return new DemoModel();
    }

    @Override
    public void getList(int pager) {
        getModel().getList(pager, new BaseCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                getView().setListData(strings);
            }

            @Override
            public void onError(StatusError error) {
                getView().onErrorList(error);
            }
        });
    }

    @Override
    public void getData() {
        getModel().getData(new BaseCallback<BaseInfo>() {
            @Override
            public void onSuccess(BaseInfo baseInfo) {
                getView().setData(baseInfo);
            }

            @Override
            public void onError(StatusError error) {
                getView().onError(error);
            }
        });
    }
}

