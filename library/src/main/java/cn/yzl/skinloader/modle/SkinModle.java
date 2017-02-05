package cn.yzl.skinloader.modle;

import android.text.TextUtils;

import java.io.File;

import cn.yzl.skinloader.exception.SkinException;

/**
 * Created by YZL on 2017/2/5.
 */

public class SkinModle {
    /**
     * 是否是插件
     */
    private boolean isPlugin = false;

    /**
     * 插件路径
     */
    private String pluginPath;

    /**
     * 插件包名
     */
    private String pluginPkg;

    /**
     * 应用内换肤后缀,真正使用的是
     * {@link cn.yzl.skinloader.config.SkinConfig}中的 getSKIN_SUFFIX() +suffix
     */
    private String suffix;

    public SkinModle(String suffix) {
        this.suffix = suffix;
        this.isPlugin = false;
        this.pluginPath = null;
        this.pluginPkg = null;
    }

    public SkinModle(String pluginPath, String pluginPkg) {
        this.pluginPath = pluginPath;
        this.pluginPkg = pluginPkg;
        this.isPlugin = true;
    }


    public boolean isPlugin() throws SkinException {

        if (isPlugin && (TextUtils.isEmpty(pluginPath) || TextUtils.isEmpty(pluginPkg))) {
            throw new SkinException("皮肤插件信息不完整");
        }

        if (isPlugin && (!checkPluginExist())) {
            throw new SkinException("皮肤插件不存在,可能已被删除");
        }

        if ((!isPlugin) && TextUtils.isEmpty(getSuffix())) {
            throw new SkinException("皮肤后缀名为空");
        }
        return isPlugin;
    }

    /**
     * 检查当前 插件文件是否还存在
     *
     * @return
     */
    public boolean checkPluginExist() {
        File file = new File(this.pluginPath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlugin(boolean plugin) {
        isPlugin = plugin;
    }

    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public String getPluginPkg() {
        return pluginPkg;
    }

    public void setPluginPkg(String pluginPkg) {
        this.pluginPkg = pluginPkg;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
