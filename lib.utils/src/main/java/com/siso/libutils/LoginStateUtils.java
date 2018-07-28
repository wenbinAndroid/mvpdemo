package com.siso.libutils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 *
 */

public class LoginStateUtils {
    public static final String TOKEN = "token";

    public static boolean isLogin(boolean jumpLogin) {
        if (TextUtils.isEmpty(getToken())) {
            if (jumpLogin) jumpLogin();
            return false;
        }
        return true;
    }

    private static String getToken() {
        return SPUtils.getInstance().getString(TOKEN);
    }

    private static void jumpLogin() {

    }


}
