package com.record.kit.builder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.record.kit.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * Created by hljdrl on 2018/3/19.
 */

public class PermissionCompat {

    public static boolean hasPermission(Context context, String permission) {
        int hasWriteStoragePermission = ContextCompat.checkSelfPermission(context, permission);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            return true;
        } else {
            //没有权限，向用户请求权限
            return false;
        }
    }

    public static void check(Context context, final OnFinishListener listener) {
        List<PermissionItem> permissions = new ArrayList<PermissionItem>();
        final String TAG = "Permission";
        Log.i(TAG, "unPermission-> ");
        permissions.add(new PermissionItem(Manifest.permission.RECORD_AUDIO,
                context.getString(R.string.permission_mic),
                R.drawable.permission_ic_micro_phone));
        //
        permissions.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                context.getString(R.string.permission_storage),
                R.drawable.permission_ic_storage));

        permissions.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE,
                context.getString(R.string.permission_phone),
                R.drawable.permission_ic_phone));
        //
        permissions.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE,
                context.getString(R.string.permission_phone),
                R.drawable.permission_ic_phone));
        //=======================================================
        permissions.add(new PermissionItem(Manifest.permission.CAMERA,
                context.getString(R.string.permission_camera),
                R.drawable.permission_ic_camera));
        //=======================================================
        HiPermission.create(context)
                .permissions(permissions)
                .checkMutiPermission(new PermissionCallback() {
                    OnFinishListener onFinishListener = listener;
                    @Override
                    public void onClose() {
                        Log.i(TAG, "onClose");
                    }
                    @Override
                    public void onFinish() {
                        Log.i(TAG, "onFinish");
                        if (onFinishListener != null) {
                            onFinishListener.onCallFinish();
                        }
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                        Log.i(TAG, "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                        Log.i(TAG, "onGuarantee");
                    }
                });
    }
}
