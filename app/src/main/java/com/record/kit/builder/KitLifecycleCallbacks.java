package com.record.kit.builder;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import arch.record.kit.L;

public class KitLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        L.i(Kit.APP_TAG, "onActivityCreated->", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        L.i(Kit.APP_TAG, "onActivityStarted->", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        L.i(Kit.APP_TAG, "onActivityResumed->", activity.getClass().getSimpleName());

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        L.i(Kit.APP_TAG, "onActivityPaused->", activity.getClass().getSimpleName());

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        L.i(Kit.APP_TAG, "onActivityStopped->", activity.getClass().getSimpleName());

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        L.i(Kit.APP_TAG, "onActivityDestroyed->", activity.getClass().getSimpleName());

    }
}
