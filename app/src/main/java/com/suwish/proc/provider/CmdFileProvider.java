package com.suwish.proc.provider;

import com.suwish.proc.provider.cmd.CmdActuator;
import com.suwish.proc.provider.cmd.CmdResolver;

import java.io.IOException;

/**
 * @author min.su on 2017/2/18.
 */
public class CmdFileProvider extends FileProvider{

    @Override
    public int getProviderType() {
        return TYPE_CMD;
    }



    @Override
    public String readFile(String path) throws IOException {
        CmdResolver resolver = CmdActuator.cat(path);
        return resolver.parseDetail();
    }

    @Override
    public void debug(String path) {
        CmdResolver resolver = CmdActuator.cat(path);
        resolver.debug();
    }
}
