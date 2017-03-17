package com.suwish.proc.task.entity;

import android.text.TextUtils;

import com.suwish.proc.provider.FileProvider;
import com.suwish.proc.provider.cmd.CmdActuator;
import com.suwish.proc.provider.cmd.CmdResolver;

import java.io.IOException;
import java.util.Locale;

/**
 * @author min.su on 2017/2/18.
 */
public class AppProcSource {

    private int pid;
    private String cmdLine;

    private String cgroup;

    private ProcStat stat;

    public AppProcSource(int pid, ProcSource procSource){
        this.pid = pid;
    }

    /**
     *
     * background thread
     */
    public boolean buildProcInfo(FileProvider provider) throws IOException{
        CmdResolver resolver = CmdActuator.cat(String.format(Locale.getDefault(), "/proc/%d/cmdline", pid).trim());
        if (!resolver.checkAppProcess()) return false;
        if (!resolver.checkAvailable()) return false;
        cmdLine = resolver.parseDetail();
        resolver = CmdActuator.cat(String.format(Locale.getDefault(), "/proc/%d/cgroup", pid).trim());
        if (!resolver.checkAvailable()) return false;
        cgroup = resolver.parseDetail();
        resolver = CmdActuator.cat(String.format(Locale.getDefault(), "/proc/%d/stat", pid).trim());
        if (!resolver.checkAvailable()) return false;
        stat = ProcStat.from(resolver);
//        cmdLine = provider.readFile();

        return true;
    }

    public int getPid(){
        return pid;
    }
    public String getCmdLine() {
        return cmdLine;
    }

    public String getCGroup() {
        return cgroup;
    }
    public ProcStat getStat(){
        return stat;
    }

    public String parseDisplay(){
        return "PID " + pid + " " + parsePackageName();
    }

    private String parsePackageName(){
        if (TextUtils.isEmpty(cmdLine)) return "";
        int index = cmdLine.lastIndexOf(":");
        if (index <= 0) return cmdLine;
        return cmdLine.substring(0, index);
    }
}
