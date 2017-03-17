package com.suwish.proc.provider;

import com.suwish.proc.log.LL;

/**
 * @author min.su on 2017/2/18.
 */
public final class ProviderManager {

    private final String TAG = LL.makeLogTag(ProviderManager.class);

    private static class ProviderHolder{
        private static final ProviderManager INSTANCE = new ProviderManager();
    }

    public static ProviderManager getManager(){
        return ProviderHolder.INSTANCE;
    }

    private FileProvider fileProvider;

    private ProviderManager(){
        FileProvider.Builder builder = new FileProvider.Builder();
        fileProvider = builder.setType(FileProvider.TYPE_CMD).build();
    }

    public FileProvider getProvider(){
        return fileProvider;
    }

    public void debugProc(String path){
        fileProvider.debug(path);
    }
}
