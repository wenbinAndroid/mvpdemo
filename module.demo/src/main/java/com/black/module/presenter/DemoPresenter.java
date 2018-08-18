package com.black.module.presenter;

import com.black.libcommon.data.StatusError;
import com.black.libcommon.http.callback.BaseCallback;
import com.black.libcommon.mvp.presenter.BasePresenter;
import com.black.module.contract.IDemoContract;
import com.black.module.model.DemoModel;

import java.util.List;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public class DemoPresenter extends BasePresenter<IDemoContract.Model, IDemoContract.View>
        implements IDemoContract.Presenter {

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
        getModel().getData(new BaseCallback<String>() {
            @Override
            public void onSuccess(String s) {
                getView().setData(s);
            }

            @Override
            public void onError(StatusError error) {
                getView().onError(error);
            }
        });
    }
}

