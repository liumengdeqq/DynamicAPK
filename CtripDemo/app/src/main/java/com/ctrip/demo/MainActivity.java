package com.ctrip.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String basePath="/data/user/0/com.ctrip.demo/lib/";
    public static  String sourcename=basePath+"libcom_taobao_firstbundle.so";
    public static  String outoptname=basePath+"bundle.dex";
    public static String sourcename1=basePath+"libcom_taobao_publicBundle.so";
    public static  String outoptname1=basePath+"bundle1.dex";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RuntimeArgs.androidApplication=getApplication();

        try {
            SysHacks.defineAndVerify();
        } catch (AssertionArrayException e) {
            e.printStackTrace();
        }
        try {
            DelegateResources.newDelegateResources(getApplication(),getApplication().getResources());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<File> files=new ArrayList();
        files.add(new File(sourcename));
        List<File> files1=new ArrayList();
        files1.add(new File(sourcename1));
        try {
            BundlePathLoader.installBundleDexs(this.getApplication().getClassLoader(),new File(outoptname),files,false);
            BundlePathLoader.installBundleDexs(this.getApplication().getClassLoader(),new File(outoptname1),files1,false);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            AndroidHack.injectInstrumentationHook(new InstrumentationHook(AndroidHack.getInstrumentation(),this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Intent intent=new Intent(this,Class.forName("com.taobao.firstbundle.FirstBundleActivity"));
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
