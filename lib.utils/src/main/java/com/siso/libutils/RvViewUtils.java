package com.siso.libutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 * RecyclerView 视图界面管理,错误布局和空布局
 */

public class RvViewUtils {
    private final static int EMPTY_RES = -1;
    private final static String EMPTY_TEXT = "";
    private final static String DEFAULT_EMPTYY_TEXT = "暂无数据";
    private final static boolean OPEN_DEFAULT_EMPTYY_TEXT = true;
    private OnErrLoadingListener mListener;
    private static RvViewUtils mInstant;


    private RvViewUtils() {

    }

    public static RvViewUtils getInstant() {
        if (mInstant == null) {
            synchronized (RvViewUtils.class) {
                if (mInstant == null) {
                    mInstant = new RvViewUtils();
                }
            }
        }
        return mInstant;
    }

    private static int getCommonEmptyLayout() {
        return R.layout.include_recyclerview_empty_view;
    }

    public static View getEmptyView(Activity activity, RecyclerView rv) {
        return getEmptyView(activity, rv, EMPTY_TEXT);
    }

    public static View getEmptyView(Activity activity, int img, RecyclerView rv) {
        return getEmptyView(activity, rv, EMPTY_TEXT, getCommonEmptyLayout(), img);
    }

    public static View getEmptyView(Activity activity, RecyclerView rv, String emptyText) {
        return getEmptyView(activity, rv, emptyText, getCommonEmptyLayout(), EMPTY_RES);
    }

    public static View getEmptyView(Activity activity, RecyclerView rv, String emptyText, int img) {
        return getEmptyView(activity, rv, emptyText, getCommonEmptyLayout(), img);
    }


    public static View getEmptyView(Activity activity, RecyclerView rv, String emptyText,
                                    @LayoutRes int
                                            layoutID, @IdRes int imgRes) {
        if (layoutID == EMPTY_RES) {
            layoutID = getCommonEmptyLayout();
        }
        View emptyView = activity.getLayoutInflater().inflate(layoutID, (ViewGroup)
                rv.getParent(), false);
        if (imgRes != EMPTY_RES) {
            ImageView iv = emptyView.findViewById(R.id.iv);
            if (iv != null) {
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), imgRes);
                WeakReference<Bitmap> bit = new WeakReference<Bitmap>(bitmap);
                iv.setImageBitmap(bit.get());
            }
        }
        TextView tv = emptyView.findViewById(R.id.tv);
        if (tv != null) {
            tv.setText(TextUtils.isEmpty(emptyText) ? OPEN_DEFAULT_EMPTYY_TEXT ?
                    DEFAULT_EMPTYY_TEXT : "" : emptyText);
        }
        return emptyView;
    }


    public View getErrView(Activity activity, RecyclerView rv) {
        return getErrView(activity, rv, getCommonErrLayout());
    }

    public View getErrView(Activity activity, RecyclerView rv, @LayoutRes int layout) {
        View errView = activity.getLayoutInflater().inflate(layout, (ViewGroup)
                rv.getParent(), false);
        errView.findViewById(R.id.errLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRvErrLoading();
                    mListener = null;
                }
            }
        });
        return errView;
    }

    public int getCommonErrLayout() {
        return R.layout.include_recyclerview_err_view;
    }

    public interface OnErrLoadingListener {
        void onRvErrLoading();
    }

    public RvViewUtils setErrLoadingListener(OnErrLoadingListener listener) {
        this.mListener = listener;
        return this;
    }

}
