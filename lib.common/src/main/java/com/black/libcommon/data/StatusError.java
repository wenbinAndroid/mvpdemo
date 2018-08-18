package com.black.libcommon.data;

/**
 * @author Mrz
 * @date  2018/7/18 19:18
 * 后台错误信息解析
 */

public class StatusError {
    public String errText;
    public int status;
    public int type;

    public StatusError() {

    }

    public StatusError(String errText) {
        this.errText = errText;
    }

    public StatusError(String errText, int status) {
        this.errText = errText;
        this.status = status;
    }

    public StatusError(String errText, int status, int type) {
        this.errText = errText;
        this.status = status;
        this.type = type;
    }
}
