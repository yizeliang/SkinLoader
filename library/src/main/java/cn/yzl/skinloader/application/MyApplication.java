package cn.yzl.skinloader.application;

import android.app.Application;

import cn.yzl.skinloader.attr.SkinAttr;
import cn.yzl.skinloader.manager.SkinManager;

/**
 * Created by YZL on 2017/2/4.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
