package cn.yzl.skinloader.manager;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by YZL on 2017/2/4.
 */

public class ResourceManager {

    private Resources mResources;
    private String mPkgName;

    private String suffix;

    public ResourceManager(Resources resources, String pkgName, String suffix) {
        this.mResources = resources;
        this.mPkgName = pkgName;
        this.suffix = suffix;
        if (this.suffix == null) {
            this.suffix = "";
        } else {
            this.suffix = "_" + this.suffix;

        }
    }

    public Drawable getDrawableByName(String name) {
        try {
            Log.e("res_pkg", mPkgName);
            Log.e("res_name", name);
            return mResources.getDrawable(mResources.getIdentifier(appenSuffix(name), "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorByName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(appenSuffix(name), "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String appenSuffix(String name) {
        return name + suffix;
    }
}

