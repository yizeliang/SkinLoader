package cn.yzl.skinloader.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.yzl.skinloader.manager.ResourceManager;
import cn.yzl.skinloader.manager.SkinManager;

/**
 * Created by YZL on 2017/2/4.
 */
public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourceManager().getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            }
        }
    }, SRC("src") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourceManager().getDrawableByName(resName);
            if (drawable != null) {
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(drawable);
                }
                view.setBackgroundDrawable(drawable);
            }
        }
    }, TEXT_COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            ColorStateList colorStateList = getResourceManager().getColorByName(resName);
            if (colorStateList != null) {
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(colorStateList);
                }
            }
        }
    };

    public abstract void apply(View view, String resName);

    String resType;

    SkinAttrType(String resType) {
        this.resType = resType;
    }

    public ResourceManager getResourceManager() {
        return SkinManager.getInstance().getResourceManager();
    }

    public String getResType() {
        return resType;
    }
}
