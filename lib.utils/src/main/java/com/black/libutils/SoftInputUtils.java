package com.black.libutils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 * 软键盘管理工具
 */
public class SoftInputUtils {

    /*关闭软键盘*/
    public static void closeInput(Activity activity) {
        boolean state = getInputState(activity);
        try {
            ((InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                                    .getWindowToken(),
                            0);
        }catch (Exception e){

        }


    }

    /*获取软键盘的状态*/
    public static boolean getInputState(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();//isOpen若返回true，则表示输入法打开
        return isOpen;
    }

    public static void checkInpuStateAndClose(Activity activity) {
        if (getInputState(activity)) {
            closeInput(activity);
        }
    }


    public static void showOrHidePasswd(EditText editText, boolean isShow) {
        editText.setTransformationMethod(isShow ? HideReturnsTransformationMethod.getInstance() :
                PasswordTransformationMethod.getInstance());
        String content = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            editText.setSelection(content.length());
        }
    }


    /*自动获取焦点，并弹出软键盘*/
    public static void openInput(final EditText editText) {
        editText.requestFocus();
        //打开软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager imm = (InputMethodManager) editText.getContext()
                                       .getSystemService(Context.INPUT_METHOD_SERVICE);
                               imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
                               imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                                       InputMethodManager.HIDE_IMPLICIT_ONLY);
                           }
                       },
                200);
    }

}
