package com.suwish.proc.utils;

/**
 * @author min.su on 2017/3/14.
 */
public final class StatUtil {

    private StatUtil(){}

    /**
     *
     * <b>(jiffies是内核中的一个全局变量，用来记录自系统启动一来产生的节拍数，在linux中，
     * 一个节拍大致可理解为操作系统进程调度的最小时间片，不同linux内核可能值有不同，通常在1ms到10ms之间)
     *</b> <br/>
     * <p>
     * <b>user (38082)</b>    从系统启动开始累计到当前时刻，处于用户态的运行时间，不包含 nice值为负进程。
     *</p>
     *
     * <p>
     * <b>nice (627) </b>     从系统启动开始累计到当前时刻，nice值为负的进程所占用的CPU时间
     *</p>
     *
     * <p>
     * <b>system (27594)</b>  从系统启动开始累计到当前时刻，处于核心态的运行时间
     *</p>
     * <p>
     * <b>idle (893908)</b>   从系统启动开始累计到当前时刻，除IO等待时间以外的其它等待时间iowait (12256) 从系统启动开始累计到当前时刻，
     *                  IO等待时间(since 2.5.41)
     *</p>
     * <p>
     * <b>irq (581)</b>        从系统启动开始累计到当前时刻，硬中断时间(since 2.6.0-test4)
     *</p>
     * <p>
     * <b>softirq (895) </b>    从系统启动开始累计到当前时刻，软中断时间(since 2.6.0-test4)stealstolen(0)
     *                      which is the time spent in other operating systems when running in a virtualized
     *                      environment(since 2.6.11)
     *</p>
     *
     * <p>
      * <b>guest(0) </b>       which is the time spent running a virtual  CPU  for  guest operating systems under
     *                  the control of the Linux kernel(since 2.6.24)
     *</p>
     * @param args cmd line
     * @return cpu work time
     */
    public static long formatWorkTime(String[] args){
        return Long.parseLong(args[1]) + Long.parseLong(args[2]) + Long.parseLong(args[3]);
    }

    /**
     *
     * @param workTime {@link #formatWorkTime(String[])}
     * @param args cmd line
     * @return cpu total time
     */
    public static long formatTotalTime(long workTime, String[] args){
        return workTime + Long.parseLong(args[4]) + Long.parseLong(args[5]) +
                Long.parseLong(args[6]) + Long.parseLong(args[7]) + Long.parseLong(args[8]);
    }
}
