package com.suwish.proc.task.entity;

import com.suwish.proc.provider.cmd.CmdResolver;

/**
 * @author min.su on 2017/2/19.
 */
public class ProcStat {

    private String[] fields;

    public ProcStat(String value){
        fields = value.split("\\s+");
    }

    public static ProcStat from(CmdResolver resolver){
        return new ProcStat(resolver.parseDetail());
    }

    public String getPriority(){
        return fields[17];
    }
}
