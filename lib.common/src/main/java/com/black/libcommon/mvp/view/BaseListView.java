package com.black.libcommon.mvp.view;

import com.black.libcommon.data.StatusError;

import java.util.List;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 */

public interface BaseListView extends IView {

    void onErrorList(StatusError data);

    void setListData(List data);

    void setRefreshState();

}
