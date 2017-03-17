package com.suwish.proc;

import android.app.Application;

/**
 * @author by min.su on 2017/2/27.
 */
public class BaseApplication extends Application {

    private static BaseApplication APPLICATION;

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
    }

    public static BaseApplication getAppContext(){
        return APPLICATION;
    }
}
