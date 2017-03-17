package com.suwish.proc.task;

import android.os.AsyncTask;

import com.suwish.proc.CatViewActivity;
import com.suwish.proc.provider.cmd.CatResolver;
import com.suwish.proc.provider.cmd.CmdActuator;
import com.suwish.proc.provider.cmd.CmdResolver;
import com.suwish.proc.utils.WeakHandler;

/**
 * @author min.su on 2017/2/18.
 */
public class CatAppTask extends AsyncTask<String, Void, CmdResolver> {

    private EventHandler handler;

    public CatAppTask(CatViewActivity activity){
        handler = new EventHandler(activity);
    }

    @Override
    protected CmdResolver doInBackground(String... params) {
        return CmdActuator.cat(params[0]);
    }

    @Override
    protected void onPostExecute(CmdResolver cmdResolver) {
        handler.onResult((CatResolver)cmdResolver);
    }

    private static class EventHandler extends WeakHandler<CatViewActivity>{

        EventHandler(CatViewActivity owner) {
            super(owner);
        }

        void onResult(CatResolver resolver){
            CatViewActivity activity = getOwner();
            if (activity == null ) return;
            activity.onResult(resolver);
        }
    }
}
