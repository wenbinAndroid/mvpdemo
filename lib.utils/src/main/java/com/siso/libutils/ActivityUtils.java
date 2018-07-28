package com.siso.libutils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 * Activity管理工具
 */

public class ActivityUtils {
    private static List<Activity> mActivityList = new ArrayList<>();

    public static void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        mActivityList.remove(activity);
    }

    public static Activity getCurrentActivity() {
        return mActivityList.get(mActivityList.size() - 1);
    }

    public static void finishAllActivity() {
        for (int i = 0; i < mActivityList.size(); i++) {
            mActivityList.get(i).finish();
        }
    }

    public static void finishAllActivityElse(String className) {
        for (int i = 0; i < mActivityList.size(); i++) {
            String name = mActivityList.get(i).getClass().getName();
            if (!className.equals(name)) {
                mActivityList.get(i).finish();
            }
        }
    }
}
