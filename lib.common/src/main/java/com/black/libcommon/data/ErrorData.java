package com.black.libcommon.data;

import com.lzy.okgo.exception.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 * http错误信息解析
 */

public class ErrorData {

    public static String errorMsg(Throwable throwable){
        String msg;
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException){
            msg = "网络链接失败,请检查网络!";
        }else if (throwable instanceof SocketTimeoutException){
            msg = "网络请求超时,请检查网络!";
        }else if (throwable instanceof HttpException){
            msg = "服务端未响应";
        }else {
            msg = throwable.getMessage();
        }

        return msg;
    }

  /*  *//**
     * 获取错误代码
     *
     * @param throwable
     * @return
     *//*
    public static int errorCode(Throwable throwable) {
        int code;
        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            *//*客户端网络出现问题*//*
            code = 1;
        } else if (throwable instanceof SocketTimeoutException) {
            *//*服务端未响应*//*
            code = 2;
        } else if (throwable instanceof HttpException) {
            *//*服务器返回空数据*//*
            code = 2;
        } else {
            code = 1;
        }

        return code;
    }*/
}
