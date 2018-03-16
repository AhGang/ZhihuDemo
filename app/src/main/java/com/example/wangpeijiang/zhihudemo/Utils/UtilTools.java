package com.example.wangpeijiang.zhihudemo.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by wangpeijiang on 2017/12/14.
 * 工具类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textview) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textview.setTypeface(fontType);
    }

}
