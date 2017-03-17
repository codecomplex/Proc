package com.suwish.proc.task;

import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.suwish.proc.MainActivity;
import com.suwish.proc.adapter.items.BaseProcItem;
import com.suwish.proc.log.LL;
import com.suwish.proc.task.entity.ProcSource;
import com.suwish.proc.utils.WeakHandler;

/**
 * @author min.su on 2017/2/18.
 */
public class MainLoadingTask extends AsyncTask<Void, Void, List<BaseProcItem>> {

    private final String TAG = LL.makeLogTag(MainLoadingTask.class);

    private EventHandler mActivity;

    public MainLoadingTask(MainActivity activity){
        mActivity = new EventHandler(activity);
    }

    @Override
    protected void onPreExecute() {
        mActivity.onLoading();
    }

    @Override
    protected List<BaseProcItem> doInBackground(Void... params) {
        List<BaseProcItem> items = new ArrayList<>();

        try {
            File dir = new File("/proc");
            BaseProcItem item = new BaseProcItem(BaseProcItem.TYPE_DIR, "User Proc");
            items.add(item);
            for (File file : dir.listFiles()){
                String path = file.getAbsolutePath();
                if (ProcSource.isUserProc(path)) continue;
                items.add(/*item = */new BaseProcItem(new ProcSource(file)));
            }
        }catch (Exception e){
            LL.d(TAG, "",  e);
            e.printStackTrace();
        }
        return items;
    }

    @Override
    protected void onPostExecute(List<BaseProcItem> items) {
        mActivity.onResult(items);
    }

    private static class EventHandler extends WeakHandler<MainActivity>{
        EventHandler(MainActivity owner) {
            super(owner);
        }

        void onLoading(){
            MainActivity activity = getOwner();
            if (activity == null) return;
            activity.onLoading();
        }

        void onResult(List<BaseProcItem> items){
            MainActivity activity = getOwner();
            if (activity == null) return;
            activity.onResult(items);
        }
    }
}
