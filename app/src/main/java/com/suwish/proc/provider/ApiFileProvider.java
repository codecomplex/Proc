package com.suwish.proc.provider;

import com.suwish.proc.log.LL;

import java.io.File;
import java.io.IOException;

/**
 * @author min.su on 2017/2/18.
 */
class ApiFileProvider extends FileProvider{

    private final String TAG = LL.makeLogTag(ApiFileProvider.class);

    @Override
    public int getProviderType() {
        return TYPE_API;
    }

    @Override
    public String readFile(String path) throws IOException {
        return null;
    }

    @Override
    public void debug(String path) {
        LL.e(TAG, "ROOT " + path);
        File root = new File(path);
//        if (root.isDirectory()) return;
        File[] array = root.listFiles();
        if (array == null || array.length <= 0){
            LL.e(TAG, "null  " + path);
            return;
        }
        for (File file : array){
            if (file.isDirectory()){
                debug(file.getPath());
            }else {
                LL.e(TAG, file.getAbsolutePath());
            }
        }
    }
}
