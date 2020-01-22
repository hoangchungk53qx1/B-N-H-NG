package com.doan.banhang.utils;

import android.view.Display;
import android.view.WindowManager;

import com.doan.banhang.base.BaseApplication;

import static android.content.Context.WINDOW_SERVICE;

public class ScreenUtils {
    private static ScreenUtils  screenUtils;
    private static Display      display;

    public static ScreenUtils getInstance(){
        if (screenUtils == null){
            screenUtils = new ScreenUtils();
            display = ((WindowManager) BaseApplication.getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        }
        return screenUtils;
    }

    public int getHeight(){
        return display.getWidth();
    }

    public int getWidth(){
        return display.getWidth();
    }
}
