package com.suwish.proc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.suwish.proc.adapter.AppProcAdapter;
import com.suwish.proc.adapter.decorations.VerticalDecoration;
import com.suwish.proc.task.AppProcLoadingTask;
import com.suwish.proc.task.entity.AppProcSource;
import com.suwish.proc.utils.DialogHelper;
import com.suwish.proc.widget.LoadingView;
import com.suwish.proc.widget.RecyclerGridView;

import java.util.List;

/**
 * @author min.su on 2017/2/18.
 */
public class AppProcActivity extends BaseActivity
        implements View.OnClickListener, RecyclerGridView.OnItemClickListener{

    private RecyclerGridView listView;
    private AppProcAdapter adapter;

    private LoadingView loadingView;

    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void setupView() {
        listView = (RecyclerGridView)findViewById(R.id.list_view);
        listView.setAdapter(adapter = new AppProcAdapter());
        listView.setLayoutOrientation(RecyclerGridView.LAYOUT_VERTICAL_LINE);
        listView.addItemDecoration(new VerticalDecoration());
        loadingView = (LoadingView)findViewById(R.id.loading_view);

        dialogHelper = DialogHelper.create(this);

    }
    @Override
    protected void loadData() {
        new AppProcLoadingTask(this).execute();
    }
    @Override
    protected void initEvent() {
        listView.setOnItemClickListener(this);
        loadingView.setRetryCallback(this);
    }

    public void onLoading(){
        loadingView.setState(LoadingView.State.PROGRESS);
        listView.setVisibility(View.INVISIBLE);
    }
    public void onResult(List<AppProcSource> items){
        listView.setVisibility(View.VISIBLE);
        loadingView.setState(items == null || items.size() <= 0 ?
                LoadingView.State.FAIL : LoadingView.State.SUCCESS);
        adapter.setDataSource(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_retry:
                loadData();
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerGridView recyclerGridView, View view, int position) {
        AppProcSource source = adapter.getItem(position);
        StringBuilder builder = new StringBuilder();
        builder.append("cmd Line :\n");
        builder.append(source.getCmdLine());
        builder.append("\ncgroup :\n");
        builder.append(source.getCGroup());
        builder.append("\npriority :\n");
        builder.append(source.getStat().getPriority());
        dialogHelper.show(String.valueOf(source.getPid()), builder.toString(), null);
    }
}
