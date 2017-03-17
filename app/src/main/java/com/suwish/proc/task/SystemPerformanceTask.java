package com.suwish.proc.task;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.suwish.proc.BaseApplication;
import com.suwish.proc.PerformanceActivity;
import com.suwish.proc.log.LL;
import com.suwish.proc.provider.cmd.CmdActuator;
import com.suwish.proc.provider.cmd.CmdResolver;
import com.suwish.proc.task.entity.Performance;
import com.suwish.proc.utils.StatUtil;
import com.suwish.proc.utils.WeakHandler;

import java.util.Locale;

/**
 * @author by min.su on 2017/2/26.
 */
public class SystemPerformanceTask extends AsyncTask<Void, Void, Performance> {

    private final String TAG = LL.makeLogTag(SystemPerformanceTask.class);

    private EventHandler handler;

    public SystemPerformanceTask(PerformanceActivity activity){
        handler = new EventHandler(activity);
    }

    @Override
    protected Performance doInBackground(Void... voids) {
        Performance performance = new Performance();
        calCpuUsage(performance);
        calMemInfo(performance);
        calNetwork(performance);
        return performance;
    }

    private void calCpuUsage(Performance performance){
        CmdResolver resolver = CmdActuator.cat("/proc/stat");
        String detail = resolver.parseDetail();
        String[] args = detail.split("\\n")[0].split("\\s+");
        long workTimeStart = StatUtil.formatWorkTime(args);
        long totalTimeStart = StatUtil.formatTotalTime(workTimeStart, args);
        try {
            Thread.sleep(300);
        }catch (Exception ignored){}
        resolver = CmdActuator.cat("/proc/stat");
        detail = resolver.parseDetail();
        args = detail.split("\\n")[0].split("\\s+");
        long workTimeEnd = StatUtil.formatWorkTime(args);
        long totalTimeEnd = StatUtil.formatTotalTime(workTimeEnd, args);

        long totalTime = totalTimeEnd - totalTimeStart;
        double workTime = workTimeEnd - workTimeStart;
        double num = workTime * 100 / totalTime;
        String cpuUsage = String.format(Locale.getDefault(), "%.2f %%", num);
        LL.e(TAG, "CPU usage :" +  cpuUsage);
        performance.setCpuUsage(cpuUsage);
    }

    private void calMemInfo(Performance performance){
        Context context = BaseApplication.getAppContext();
        CmdResolver memResolver = CmdActuator.cat("/proc/meminfo");
        String[] items = memResolver.parseDetail().split("\\n");
        long memory = Long.parseLong(items[0].split("\\s+")[1]) * 1024;
        performance.setTotalMemory(Formatter.formatFileSize(context, memory));
//        long freeMemory = Long.parseLong(items[1].split("\\s+")[1]) +
//                Long.parseLong(items[2].split("\\s+")[1]) +
//                Long.parseLong(items[3].split("\\s+")[1]);
//        LL.e(TAG, "total memory " + memory + " free memory " + freeMemory);
//        performance.setCatFreeMemory(Formatter.formatFileSize(context, freeMemory));

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memoryInfo);
        performance.setApiFreeMemory(Formatter.formatFileSize(context, memoryInfo.availMem));
    }

    private void calNetwork(Performance performance){
        Context context = BaseApplication.getAppContext();
        CmdResolver netResolver = CmdActuator.cat("/proc/net/dev");
        String detail = netResolver.parseDetail();
        //android.net.TrafficStats
        long netSend = 0;
        long netReceive = 0;
        String[] lines = detail.split("\\n");
        for (String line : lines){
            if (TextUtils.isEmpty(line)) continue;
            if (!line.contains("wlan0")) continue;
            String[] items = line.trim().split("\\s+");
            netReceive = Long.parseLong(items[1]);
            netSend = Long.parseLong(items[9]);
            break;
        }
        performance.setNetReceive(Formatter.formatFileSize(context, netReceive));
        performance.setNetSend(Formatter.formatFileSize(context, netSend));
    }

    @Override
    protected void onPreExecute() {
        handler.onLoading();
    }
    @Override
    protected void onPostExecute(Performance performance) {
        handler.onResult(performance);
    }

    private static class EventHandler extends WeakHandler<PerformanceActivity>{
        EventHandler(PerformanceActivity owner) {
            super(owner);
        }

        void onLoading(){
            PerformanceActivity activity = getOwner();
            if (activity == null) return;
            activity.onLoading();
        }

        void onResult(Performance performance){
            PerformanceActivity activity = getOwner();
            if (activity == null) return;
            activity.onResult(performance);
        }
    }
}
