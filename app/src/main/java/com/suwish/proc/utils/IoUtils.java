package com.suwish.proc.utils;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author min.su on 2017/2/18.
 */
public final class IoUtils {

    private IoUtils(){}

    public static void close(InputStream stream){
        if (stream == null) return;
        try {
            stream.close();
        }catch (Exception ignored){}
    }
    public static void close(Reader reader){
        if (reader == null) return;
        try {
            reader.close();
        }catch (Exception ignored){}
    }
}
