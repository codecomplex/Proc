package com.suwish.proc.provider.cmd;

import android.text.TextUtils;

import com.suwish.proc.log.LL;

/**
 * @author min.su on 2017/2/18.
 */
public abstract class CmdResolver {

    private final String TAG = LL.makeLogTag(CmdResolver.class);

    private final String TAG_PERMISSION_DENIED = "Permission denied";
    private final String TAG_NOT_AVAILABLE = "No such file or directory";

    protected String[] source;

    CmdResolver(String[] source){
        this.source = source;
    }

    public static CmdResolver create(String cmd, String[] source){
        if (TextUtils.isEmpty(cmd))throw new IllegalArgumentException("null");
        CmdResolver resolver = null;
        switch (cmd){
            case CmdActuator.CMD_LS:
                resolver = new LsResolver(source);
                break;
            case CmdActuator.CMD_CAT:
                resolver = new CatResolver(source);
                break;
        }
        return resolver;
    }

    public boolean checkAppProcess(){
        if (source == null || source.length <= 0) return false;
        for (String value : source){
            if (TextUtils.isEmpty(value)) continue;
            if (value.contains("/")) return false;
            if (!value.contains(".")) return false;
        }

        return true;

    }

    public boolean checkPermission(){
        for (String line : source){
            if (TextUtils.isEmpty(line)) continue;
            if (line.trim().contains(TAG_PERMISSION_DENIED)) return false;
        }
        return true;
    }

    public boolean checkAvailable(){
        for (String line : source){
            if (TextUtils.isEmpty(line)) continue;
            if (line.trim().contains(TAG_PERMISSION_DENIED)) return false;
            if (line.trim().contains(TAG_NOT_AVAILABLE)) return false;
        }
        return true;
    }

    public String parseDetail(){
        if (source == null || source.length <= 0){
            return "NULL";
        }
        StringBuilder builder = new StringBuilder();
        for (String value : source){
            builder.append(value);
            builder.append("\n");
        }
        return builder.toString();
    }

    public void debug(){
        if (source == null || source.length < 0){
            LL.e(TAG, "Source null");
            return;
        }
        for (String path : source){
            LL.e(TAG, path);
        }
    }
}
