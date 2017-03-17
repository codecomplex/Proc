package com.suwish.proc.task;

import android.os.AsyncTask;

import com.suwish.proc.AppProcActivity;
import com.suwish.proc.log.LL;
import com.suwish.proc.provider.FileProvider;
import com.suwish.proc.provider.ProviderManager;
import com.suwish.proc.task.entity.AppProcSource;
import com.suwish.proc.task.entity.ProcSource;
import com.suwish.proc.utils.WeakHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author min.su on 2017/2/18.
 */
public class AppProcLoadingTask extends AsyncTask<Void, Void, List<AppProcSource>> {

    private final String TAG = LL.makeLogTag(AppProcLoadingTask.class);

    private EventHandler eventHandler;

    public AppProcLoadingTask(AppProcActivity activity){
        eventHandler = new EventHandler(activity);
    }

    @Override
    protected void onPreExecute() {
        eventHandler.onLoading();
    }

    @Override
    protected List<AppProcSource> doInBackground(Void... params) {
        List<AppProcSource> list = new ArrayList<>();
        FileProvider provider = ProviderManager.getManager().getProvider();
        try {
            File dir = new File("/proc");
            for (File file : dir.listFiles()){
                String path = file.getAbsolutePath();
                int pid = ProcSource.parseUserProc(path);
            if (pid == -1) continue;
                AppProcSource source = new AppProcSource(pid, new ProcSource(file));
                if (!source.buildProcInfo(provider)) continue;
                list.add(source);
            }
        }catch (Exception e){
            LL.e(TAG, "", e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<AppProcSource> items) {
        eventHandler.onResult(items);
    }

    private static class EventHandler extends WeakHandler<AppProcActivity>{

        EventHandler(AppProcActivity owner) {
            super(owner);
        }


        void onLoading(){
            AppProcActivity activity = getOwner();
            if (activity == null) return;
            activity.onLoading();
        }

        void onResult(List<AppProcSource> items){
            AppProcActivity activity = getOwner();
            if (activity == null) return;
            activity.onResult(items);
        }
    }

}
