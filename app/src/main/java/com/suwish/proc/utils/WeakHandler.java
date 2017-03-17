package com.suwish.proc.utils;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 弱引用Handler <p/>
 *
 * 内部使用<code>WeakReference</code>做Context类对象的弱引用，
 * 主要目的是便于<code>Activity</code>对象的回收，子类的实现最好使用
 * 静态类。对于Activity的引用使用{@link #getOwner()}方法，由于一本实现做
 * 内部类，所以基本可以引用到此Activity对象中的所有成员（脑包括私有成员）。
 * <p/>
 *
 * 使用方法与普通的{@link Handler}方式一样，唯一的区别是使用{@link #getOwner()}
 * 方法判断界面是否被回收。
 *
 * @param <T> 泛型，一般为Activity
 *
 * @author min.su on 2015 04 01
 */
public abstract class WeakHandler<T> extends Handler {
    private WeakReference<T> mOwner;

    public WeakHandler(T owner) {
        super(Looper.getMainLooper());
        mOwner = new WeakReference<T>(owner);
    }

    /**
     * 返回界面元素，由于使用弱引用，所以不能保证Activity一定存在，如果不存在则认为
     * 其已经被回收，在{@link Handler#handleMessage(Message)}回调中使用此判断终止
     * 逻辑调用。
     *
     * @return Activity
     */
    protected T getOwner() {
        return mOwner.get();
    }
}
