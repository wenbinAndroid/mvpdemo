package com.siso.libcommon.data;

import android.content.pm.ActivityInfo;

/**
 * @author Mrz
 * @date 2018/7/18 19:18
 * 公共配置文件
 */

public class Config {
    //Activity默认竖屏
    public static final int ACTIVITY_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    //列表页默认加载10条数据
    public static final int PAGER_SIZE = 10;
    //http加密字符串
    public static final String HTTP_KEY = "AE86";
    //默认图片路径
    public static final String DEFAULT_IMG = "www.baidu.com";

    //文件路径
    public static final class FILE {
        //默认存储路径
        public static final String DIR = "/ABC";
        //缓存路径
        public static final String CACHE = DIR + "/cache";
        //图片缓存
        public static final String CACHE_IMAGE = CACHE + "/image";
    }
}
