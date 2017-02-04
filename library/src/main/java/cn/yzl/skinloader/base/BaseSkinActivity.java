package cn.yzl.skinloader.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.yzl.skinloader.attr.SkinAttr;
import cn.yzl.skinloader.attr.SkinAttrSupport;
import cn.yzl.skinloader.attr.SkinView;
import cn.yzl.skinloader.callback.SkinChangedListener;
import cn.yzl.skinloader.manager.SkinManager;

/**
 * Created by YZL on 2017/2/4.
 */

public abstract class BaseSkinActivity extends AppCompatActivity implements SkinChangedListener {

    private Method mCreateViewMethod = null;

    private final Class<?>[] sCreateViewSignature = new Class[]{View.class, String.class, Context.class, AttributeSet.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        SkinManager.getInstance().register(this);

        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutInflaterCompat.setFactory(mInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                View view = null;
                List<SkinAttr> skinAttrs = null;
                //首先完成 AppcontFactory 的工作
                AppCompatDelegate delegate = getDelegate();
                try {
                    if (mCreateViewMethod == null) {
                        mCreateViewMethod = delegate.getClass().getMethod("createView", sCreateViewSignature);
                    }
                    view = (View) mCreateViewMethod.invoke(delegate, parent, name, context, attrs);


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                skinAttrs = SkinAttrSupport.getSkinAttrs(attrs, context);

                if (skinAttrs == null || skinAttrs.size() == 0) {
                    return null;
                }

                if (view == null) {
                    view = createViewFromTag(context, name, attrs);
                }

                if (view != null) {
                    injectSkin(view, skinAttrs);
                }
                return view;
            }
        });


        super.onCreate(savedInstanceState);
    }

    protected void injectSkin(View view, List<SkinAttr> skinAttrs) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(this);

        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().addSkinView(this, skinViews);
        }
        skinViews.add(new SkinView(view, skinAttrs));
    }

    private final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    private static final Map<String, Constructor<? extends View>> sConstructorMap
            = new ArrayMap<>();

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (int i = 0; i < sClassPrefixList.length; i++) {
                    final View view = createView(context, name, sClassPrefixList[i]);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return createView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View createView(Context context, String name, String prefix)
            throws ClassNotFoundException, InflateException {
        Constructor<? extends View> constructor = sConstructorMap.get(name);

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null;
        }
    }

    @Override
    public void onSkinChange() {

    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance().unRegister(this);
        super.onDestroy();
    }
}
