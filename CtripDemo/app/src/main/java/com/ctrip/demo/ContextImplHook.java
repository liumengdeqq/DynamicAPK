package com.ctrip.demo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by liumeng on 2018/5/30.
 */

public class ContextImplHook extends ContextWrapper{
    public ContextImplHook(Context context) {
        super(context);

    }

    @Override
    public Resources getResources() {
        return RuntimeArgs.delegateResources;
    }

    @Override
    public AssetManager getAssets() {
        return RuntimeArgs.delegateResources.getAssets();
    }
}
