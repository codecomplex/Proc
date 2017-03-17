package com.suwish.proc.task.entity;

import java.io.File;

/**
 * @author min.su on 2017/2/18.
 */
public class ProcSource {

    public final int TYPE_DIR = 1;
    public final int TYPE_FILE = 2;

    private int type;

    private File procFile;

    public ProcSource(File procFile){
        if (procFile == null) throw new IllegalArgumentException("procFile is null");
        this.procFile = procFile;
        type = procFile.isDirectory() ? TYPE_DIR : TYPE_FILE;
    }

    public static boolean isUserProc(String path){
        return parseUserProc(path) != -1;
    }

    public static int parseUserProc(String path){
        int index = path.lastIndexOf("/");
        if (index <= 0) return -1;
        String pid = path.substring(index + 1);
        try {
            return Integer.parseInt(pid);
        }catch (Exception ignored){}
        return -1;
    }
    public int getType(){
        return type;
    }

    public String getPath(){
        return procFile.getAbsolutePath();
    }

    public String debug(){
        return "Dir ? " +  procFile.isDirectory()  + " path " + procFile.getAbsolutePath();
    }
}
