package com.suwish.proc.adapter.items;

import com.suwish.proc.task.entity.ProcSource;

/**
 * @author min.su on 2017/2/18.
 */
public class BaseProcItem {

    public static final int TYPE_DIR = 1;
    public static final int TYPE_FILE = 2;

    private int type = TYPE_FILE;

    private String path;

    public BaseProcItem(int type){
        this.type = type;
    }
    public BaseProcItem(int type, String desc){
        this.type = type;
        path = desc;
    }
    public BaseProcItem(ProcSource item){
        path = item.getPath();
    }

    public int getType(){
        return type;
    }
    public String getPath(){
        return path;
    }
}
