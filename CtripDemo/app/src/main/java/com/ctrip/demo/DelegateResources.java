package com.ctrip.demo;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumeng on 2018/5/30.
 */

public class DelegateResources extends Resources{


    public DelegateResources(AssetManager assets, Resources resources) {
        super(assets, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    public static void newDelegateResources(Application application, Resources resources) throws Exception {

            Resources delegateResources;
            List<String> arrayList = new ArrayList<>();
            arrayList.add(application.getApplicationInfo().sourceDir);
//            for (Bundle bundle : bundles) {
//                arrayList.add(((BundleImpl) bundle).getArchive().getArchiveFile().getAbsolutePath());
//            }
        arrayList.add(MainActivity.sourcename);
        arrayList.add(MainActivity.sourcename1);
            AssetManager assetManager = AssetManager.class.newInstance();
            for (String str : arrayList) {
                if(Build.VERSION.SDK_INT>=24) {
                      SysHacks.AssetManager_addAssetPathAsSharedLibrary.invoke(assetManager, str);
                }else{
                    SysHacks.AssetManager_addAssetPath.invoke(assetManager, str);
                }

            }
            //处理小米UI资源
            if (resources == null || !resources.getClass().getName().equals("android.content.res.MiuiResources")) {
                delegateResources = new DelegateResources(assetManager, resources);
            } else {
                Constructor declaredConstructor = Class.forName("android.content.res.MiuiResources").getDeclaredConstructor(new Class[]{AssetManager.class, DisplayMetrics.class, Configuration.class});
                declaredConstructor.setAccessible(true);
                delegateResources = (Resources) declaredConstructor.newInstance(new Object[]{assetManager, resources.getDisplayMetrics(), resources.getConfiguration()});
            }
            RuntimeArgs.delegateResources = delegateResources;
            AndroidHack.injectResources(application, delegateResources);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("newDelegateResources [");
            for (int i = 0; i < arrayList.size(); i++) {
                if (i > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(arrayList.get(i));
            }
            stringBuffer.append("]");
    }
}
