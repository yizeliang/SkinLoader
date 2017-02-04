package cn.yzl.skinloader.attr;

import android.view.View;

/**
 * Created by YZL on 2017/2/4.
 */
public class SkinAttr {

    private String resName;
    private SkinAttrType mType;

    public void aplay(View view) {
        mType.apply(view,resName);
    }

    public SkinAttr(String resName, SkinAttrType type) {
        this.resName = resName;
        this.mType = type;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public SkinAttrType getType() {
        return mType;
    }

    public void setType(SkinAttrType type) {
        this.mType = mType;
    }
}
