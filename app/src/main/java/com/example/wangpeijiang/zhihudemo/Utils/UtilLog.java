package com.example.wangpeijiang.zhihudemo.Utils;

import android.util.Log;

/**
 * Created by wangpeijiang on 2017/12/25.
 * LOG封装
 */

public class UtilLog {

    //开关
    public static final  boolean DEBUG = true;
    //TAG
    public static final String TAG = "Demo" ;

    //五个等级  DIWE
//IOC AOP
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
}
