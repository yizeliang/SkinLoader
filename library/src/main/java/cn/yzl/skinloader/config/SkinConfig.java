package cn.yzl.skinloader.config;

/**
 * Created by YZL on 2017/2/4.
 */

public class SkinConfig {
    /**
     * 前缀_用户插件式换肤
     */
    public String SKIN_PREFIX = "skin_";

    /**
     * 后缀_用于应用内换肤
     */
    public String SKIN_SUFFIX = "_";

    private static SkinConfig skinConfig;

    public static SkinConfig initSkinPrefix(String prefix) {
        skinConfig = getSkinConfig();
        skinConfig.setSKIN_PREFIX(prefix);
        return skinConfig;
    }

    public static SkinConfig initSkinSuffix(String prefix) {
        skinConfig = getSkinConfig();
        skinConfig.setSKIN_SUFFIX(prefix);
        return skinConfig;
    }


    public static SkinConfig getSkinConfig() {
        if (skinConfig != null) {
            return skinConfig;
        }
        return new SkinConfig();
    }

    public String getSKIN_PREFIX() {
        return SKIN_PREFIX;
    }

    public void setSKIN_PREFIX(String SKIN_PREFIX) {
        this.SKIN_PREFIX = SKIN_PREFIX;
    }

    public String getSKIN_SUFFIX() {
        return SKIN_SUFFIX;
    }

    public void setSKIN_SUFFIX(String SKIN_SUFFIX) {
        this.SKIN_SUFFIX = SKIN_SUFFIX;
    }
}
