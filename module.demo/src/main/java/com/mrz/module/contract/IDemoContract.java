package com.mrz.module.contract;

import com.siso.libcommon.data.BaseInfo;
import com.siso.libcommon.http.callback.BaseCallback;
import com.siso.libcommon.mvp.model.IModel;
import com.siso.libcommon.mvp.presenter.IPresenter;
import com.siso.libcommon.mvp.view.BaseListView;

import java.util.List;

/**
 * Description :
 *
 * @author Mrz
 * @date 2018/7/28  11:45
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IDemoContract {
    interface View extends BaseListView {
        void setData(BaseInfo info);
    }

    interface Presenter extends IPresenter<View> {
        void getList(int pager);

        void getData();
    }

    interface Model extends IModel {
        void getList(int pager, BaseCallback<List<String>> callback);

        void getData(BaseCallback<BaseInfo> callback);
    }
}
