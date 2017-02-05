package cn.yzl.skinloader.attr;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import cn.yzl.skinloader.config.SkinConfig;

/**
 * Created by YZL on 2017/2/4.
 */

public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {

        List<SkinAttr> attrList = new ArrayList<>();
        for (int i = 0, n = attrs.getAttributeCount(); i < n; i++) {
            SkinAttrType skinAttrType = null;
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.trim().substring(1));

                    String resName = context.getResources().getResourceEntryName(id);

                    if (resName.startsWith(SkinConfig.getSkinConfig().getSKIN_PREFIX())) {
                        skinAttrType = getSupportAttrType(attrName);
                        if (skinAttrType != null) {
                            attrList.add(new SkinAttr(resName,skinAttrType));
                        }else{
                            continue;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return attrList;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType type :
                SkinAttrType.values()) {
            if (type.getResType().equals(attrName)) {
                return type;
            }
        }
        return null;
    }
}
