package com.black.module.contract;

import com.black.libcommon.data.BaseInfo;
import com.black.libcommon.http.callback.BaseCallback;
import com.black.libcommon.mvp.model.IModel;
import com.black.libcommon.mvp.presenter.IPresenter;
import com.black.libcommon.mvp.view.BaseListView;

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
        void setList(BaseInfo info);

        void setData(String info);
    }

    interface Presenter extends IPresenter<View> {
        void getList(int pager);

        void getData();
    }

    interface Model extends IModel {
        void getList(int pager, BaseCallback<List<String>> callback);

        void getData(BaseCallback<String> callback);
    }
}
