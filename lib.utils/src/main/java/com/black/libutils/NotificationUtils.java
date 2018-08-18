package com.black.libutils;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 * 检测通知栏状态
 */
public class NotificationUtils {

    private static final String NOTIFICATION_COUNT = "notification_count";
    //未打开通知 提示打开通知提示次数
    private static int NOTIFICATION_TIPS_COUNT = 3;
    private static String OPEN_NOTIFICATION_TEXT = "打开通知接收更多活动消息";
    private static final String TAG = "NotificationUtils";


    public static void checkNotification(Context context) {
        if (!isNotificationEnabled(context)) {
            int count = SPUtils.getInstance().getInt(NOTIFICATION_COUNT, 0);
            if (count < NOTIFICATION_TIPS_COUNT) {
                SPUtils.getInstance().put(NOTIFICATION_COUNT, ++count);
                showTipsDialog(context);
            }
        }
    }

    /**
     * 显示对话框提示
     * @param context
     */
    private static void showTipsDialog(final Context context) {
        try {
            Log.e(TAG, "showTipsDialog: ");
            new AlertDialog.Builder(ActivityUtils.getCurrentActivity())
                    .setMessage(OPEN_NOTIFICATION_TEXT)
                    .setCancelable(false)
                    .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startSettingActivity(context);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create().show();
        } catch (Exception e) {

        }
    }

    /**
     * 是否打开通知
     * @param context
     * @return
     */
    private static boolean isNotificationEnabled(Context context) {
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE,
                    Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                    AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 跳转设置打开通知
     * @param context
     */
    public static void startSettingActivity(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
