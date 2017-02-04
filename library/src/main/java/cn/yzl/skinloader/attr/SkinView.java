package cn.yzl.skinloader.attr;

import android.view.View;

import java.util.List;

/**
 * Created by YZL on 2017/2/4.
 */
public class SkinView {
    private View view;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> mAttrs) {
        if(view==null){
            throw new NullPointerException("SkinView is not be null");
        }
        this.view = view;
        this.mAttrs = mAttrs;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<SkinAttr> getmAttrs() {
        return mAttrs;
    }

    public void setmAttrs(List<SkinAttr> mAttrs) {
        this.mAttrs = mAttrs;
    }

    public void apply(){
        for (SkinAttr attr:
             mAttrs) {
            attr.aplay(view);
        }
    }
}
