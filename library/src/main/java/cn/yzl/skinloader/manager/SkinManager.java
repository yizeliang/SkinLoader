package cn.yzl.skinloader.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.yzl.skinloader.attr.SkinView;
import cn.yzl.skinloader.callback.SkinChangedListener;
import cn.yzl.skinloader.callback.SkinChangingCallback;

/**
 * Created by YZL on 2017/2/4.
 */

public class SkinManager {

    private static Context mContext;
    private static SkinManager skinManager;
    private ResourceManager resourceManager;

    private List<SkinChangedListener> mListeners = new ArrayList<>();

    private Map<SkinChangedListener, List<SkinView>> mSkinViewMaps = new HashMap<>();

    private String curSuffix;

    public SkinManager() {

    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SkinManager getInstance() {
        if (skinManager == null) {
            synchronized (SkinManager.class) {
                if (skinManager == null) {
                    skinManager = new SkinManager();
                }
            }
        }
        return skinManager;
    }

    private void loadSkin(String skinPath, String skinPkg) {
        Log.e("skinPath",skinPath);
        Log.e("skinPkg",skinPkg);

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath",
                    String.class);
            addAssetPathMethod.invoke(assetManager, skinPath);

            Resources resources = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(),
                    mContext.getResources().getConfiguration());

            resourceManager = new ResourceManager(resources, skinPkg, null);



        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public ResourceManager getResourceManager() {

        if (!TextUtils.isEmpty(curSuffix)) {
            return new ResourceManager(mContext.getResources(), mContext.getPackageName(), curSuffix);
        }
        return resourceManager;
    }


    public List<SkinView> getSkinViews(SkinChangedListener skinChangedListener) {
        return mSkinViewMaps.get(skinChangedListener);
    }

    public void addSkinView(SkinChangedListener skinChangedListener, List<SkinView> skinViews) {
        mSkinViewMaps.put(skinChangedListener, skinViews);
    }

    public void register(SkinChangedListener skinChangedListener) {
        mListeners.add(skinChangedListener);
    }

    public void unRegister(SkinChangedListener skinChangedListener) {
        mListeners.remove(skinChangedListener);
        mSkinViewMaps.remove(skinChangedListener);

    }

    public void changeSkin(final String skinPath, final String skinPkg, SkinChangingCallback callback) {
        if (callback == null) {
            callback = new SkinChangingCallback.DefaultSkinChagingCallback();
        }
        final SkinChangingCallback mCallback = callback;
        callback.onStart();
        new AsyncTask<Void, Void, Exception>() {

            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    loadSkin(skinPath, skinPkg);
                } catch (Exception e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception e) {
                if (e == null) {
                    mCallback.onComplete();
                    notifyChangedListener();
                } else {
                    mCallback.onError(e);
                }
            }
        }.execute();

    }

    private void notifyChangedListener() {

        for (SkinChangedListener skinChangedListener :
                mListeners) {

            List<SkinView> skinViews = mSkinViewMaps.get(skinChangedListener);

            for (SkinView skinView :
                    skinViews) {
                skinView.apply();
            }

            skinChangedListener.onSkinChange();
        }
    }

    public void changeSkin(String suffix) {
        curSuffix = suffix;
        notifyChangedListener();
    }
}
