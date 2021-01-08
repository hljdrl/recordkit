package com.record.kit;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.record.kit.builder.Kit;
import com.record.kit.builder.KitLifecycleCallbacks;

import arch.record.kit.L;

public class KitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new KitLifecycleCallbacks());
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public Context getApplicationContext() {
        L.i(Kit.APP_TAG,"getApplicationContext");
        return super.getApplicationContext();
    }

    @Override
    public int checkCallingOrSelfPermission(String permission) {
        L.i(Kit.APP_TAG,"checkCallingOrSelfPermission->",permission);
        return super.checkCallingOrSelfPermission(permission);
    }

    @Override
    public int checkCallingPermission(String permission) {
        L.i(Kit.APP_TAG,"checkCallingPermission->",permission);
        return super.checkCallingPermission(permission);
    }

    @Override
    public int checkSelfPermission(String permission) {
        L.i(Kit.APP_TAG,"checkSelfPermission->",permission);
        return super.checkSelfPermission(permission);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        L.i(Kit.APP_TAG,"checkCallingOrSelfUriPermission->",uri," modeFlags=",modeFlags);
        return super.checkCallingOrSelfUriPermission(uri, modeFlags);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        L.i(Kit.APP_TAG,"checkCallingUriPermission->",uri," modeFlags=",modeFlags);
        return super.checkCallingUriPermission(uri, modeFlags);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
