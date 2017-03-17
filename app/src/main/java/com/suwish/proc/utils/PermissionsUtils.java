package com.suwish.proc.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.suwish.proc.BaseActivity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author min.su on 2016/12/27.
 */
public final class PermissionsUtils {

    private PermissionsUtils(){}

    /**
     *  判断是否拥有给出的此权限列表对应的权限，如果没有权限，则使用给定的操作码
     *  申请权限。任何一项权限需要申请都返回<code>false</code><p/>
     *
     *  没有可用性判断如
     *  <pre>
     *
     * ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.xx)){
     *      if (ActivityCompat.shouldShowRequestPermissionRationale(this,permission.xxx)){...
     *
     *  </pre>
     *
     * @param activity activity
     * @param permissions 需要获取的权限列表
     * @param requestCode 获取权限的操作码
     * @return 是否拥有这些权限
     */
    public static boolean checkRequestPermission(BaseActivity activity, String[] permissions, int requestCode){
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) return true;
        Set<String> noPermissionSets = new HashSet<>();
        for (String permission : permissions){
            if (!checkPermission(activity, permission)){
                noPermissionSets.add(permission);
            }
        }
        if (noPermissionSets.size() > 0){
            ActivityCompat.requestPermissions(activity, noPermissionSets.toArray(new String[noPermissionSets.size()]), requestCode);
            return false;
        }else {
            return true;
        }
    }

    /**
     * 检测当前应用是否有给出的权限  <p/>
     *
     * TODO :
     *  由于support v4最新更新中增加了<code>android.support.v4.content.PermissionChecker</code>工具类，
     *  稍后重写此方法
     *
     * @param context context
     * @param permission permission
     * @return 是否有权限
     */
    public static boolean checkPermission(Context context, String permission){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
//        return context.getPackageManager().checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要申请此选项
     *
     * @param context context
     * @param permission permission
     * @return 是否有权限
     */
    public static boolean needPermission(Context context, String permission){
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return false;//21一下不需要申请权限
        return(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) && !checkPermission(context, permission);
    }


    public static boolean onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults){
        for (int index = 0; index < permissions.length && index < grantResults.length; index ++){
            if (grantResults[index] != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

}
