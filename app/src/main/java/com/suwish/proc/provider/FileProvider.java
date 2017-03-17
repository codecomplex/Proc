package com.suwish.proc.provider;

import java.io.IOException;

/**
 * @author min.su on 2017/2/18.
 */
public abstract class FileProvider {

    public static final int TYPE_API = 1;
    public static final int TYPE_CMD = 2;


    public abstract int getProviderType();

    public static class Builder{

        private int type;

        public Builder setType(int type){
            this.type = type;
            return this;
        }

        public FileProvider build(){
            return new com.suwish.proc.provider.CmdFileProvider();
        }
    }

    public abstract String readFile(String path) throws IOException;

    public abstract void debug(String path);

}
