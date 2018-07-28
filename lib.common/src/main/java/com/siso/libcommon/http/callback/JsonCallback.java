package com.siso.libcommon.http.callback;

/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance init the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.siso.libcommon.data.Config;
import com.siso.libcommon.data.Constans;
import com.siso.libcommon.data.ErrorData;
import com.siso.libcommon.data.StatusError;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.siso.libcommon.data.Constans.JSON_DEFAULT_STATUS;
import static com.siso.libcommon.data.Constans.NO_CONNECT_ERROR;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    protected Class<T> clazz;
    private static final String TAG = "JsonCallback";
    protected BaseCallback mCallback;
    private int status;

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    public JsonCallback(Class<T> clazz, BaseCallback callback) {
        this.clazz = clazz;
        this.mCallback = callback;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
        String token = SPUtils.getInstance().getString(Constans.TOKEN, "");
        String time = System.currentTimeMillis() + "";
        String sign = MD5(time + Config.HTTP_KEY);
        Map<String, String> map = new HashMap<>();
        map.put(Constans.TOKEN, token);
        map.put(Constans.TIMESTAMP, time);
        map.put(Constans.SIGN, sign);
        request.params(map);
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        status = JSON_DEFAULT_STATUS;
        if (body == null) {
            throw new IllegalStateException(NO_CONNECT_ERROR);
        }
        final String json = body.string();
        if (TextUtils.isEmpty(json)) {
            throw new IllegalStateException(NO_CONNECT_ERROR);
        }
        Log.e(TAG, "convertResponse: " + json);
        JSONObject jsonObject = new JSONObject(json);
        final int code = jsonObject.optInt(Constans.JSON_STATUS, JSON_DEFAULT_STATUS);
        final String msg = jsonObject.optString(Constans.JSON_MESSAGE, "");
        status = code;
        switch (code) {
            case -1:
                throw new IllegalStateException(Constans.GSON_ERROR);
            case 1:
                try {
                    if (clazz != null) {
                        return new Gson().fromJson(json, clazz);
                    } else {
                        throw new IllegalStateException(Constans.GSON_ERROR);
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e.toString());
                }

            case 2:
                login();
                throw new IllegalStateException("");
            case 3:
                throw new IllegalStateException(msg);
            case 4:
                throw new IllegalStateException(msg);
            default:
                throw new IllegalStateException(msg);
        }

    }


    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        if (mCallback != null) {
            StatusError error = new StatusError();
            error.errText = getErrText(response.getException());
            error.status = status;
            mCallback.onError(error);
            mCallback = null;
        }
    }

    /**
     * 获取错误信息提示
     *
     * @param throwable
     * @return
     */
    private String getErrText(Throwable throwable) {
        return ErrorData.errorMsg(throwable);
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        if (mCallback != null) {
            mCallback.onSuccess(response.body());
            mCallback = null;
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (mCallback != null) {
            mCallback = null;
        }
    }

    private void login() {


    }

}