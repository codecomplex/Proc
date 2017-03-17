package com.suwish.proc.provider.cmd;

import com.suwish.proc.log.LL;
import com.suwish.proc.utils.IoUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author min.su on 2017/2/18.
 */
public final class CmdActuator {

    private static final String TAG = LL.makeLogTag(CmdActuator.class);

    public static final String CMD_LS = "/system/bin/ls";
    public static final String CMD_CAT = "/system/bin/cat";

    public static final String PARAMS_LS_DETAIL = " -l ";

    private CmdActuator(){}

    public static CmdResolver ls(String path){
        return execute(CMD_LS, PARAMS_LS_DETAIL, path);
    }

    /**
     *
     * TODO change to RandomAccessFile read and seek
     *
     * @param file
     * @return
     */
    public static CmdResolver cat(String file){
        return execute(CMD_CAT, file);
    }

    public static CmdResolver execute(String[] params){
        String[] source = null;
        try {
            source = execCmd(params);
        }catch (Exception e){
            e.printStackTrace();
            LL.e(TAG, "", e);
        }
        return CmdResolver.create(params[0], source);
    }

    public static CmdResolver execute(String cmd, String ... params){
        String[] cmds = new String[params.length + 1];
        cmds[0] = cmd;
        for (int index = 0; index < params.length; index ++){
            cmds[index + 1] = params[index];
        }
        return execute(cmds);
    }

    private static String[] execCmd(String[] params) throws Exception{
        List<String> list = new ArrayList<>();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(params);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            inputStream = process.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null){
                list.add(line);
            }
        }finally {
            IoUtils.close(inputStream);
            IoUtils.close(reader);
        }
        return list.toArray(new String[list.size()]);
    }
}
