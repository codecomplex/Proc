package com.suwish.proc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import com.suwish.proc.adapter.MainAdapter;
import com.suwish.proc.adapter.decorations.VerticalDecoration;
import com.suwish.proc.adapter.items.BaseProcItem;
import com.suwish.proc.provider.ProviderManager;
import com.suwish.proc.task.MainLoadingTask;
import com.suwish.proc.utils.DialogHelper;
import com.suwish.proc.utils.PermissionsUtils;
import com.suwish.proc.widget.LoadingView;
import com.suwish.proc.widget.RecyclerGridView;

public class MainActivity extends BaseActivity implements
        View.OnClickListener, RecyclerGridView.OnItemClickListener,
        DialogHelper.DialogInputCallBack{

    private RecyclerGridView listView;
    private MainAdapter adapter;
    private LoadingView loadingView;

    private ProviderManager providerManager;
    private DialogHelper dialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setupView() {
        listView = (RecyclerGridView)findViewById(R.id.list_view);
        listView.setLayoutOrientation(RecyclerGridView.LAYOUT_VERTICAL_LINE);
        listView.setAdapter(adapter = new MainAdapter());
        listView.addItemDecoration(new VerticalDecoration());
        loadingView = (LoadingView)findViewById(R.id.loading_view);
        dialogHelper = DialogHelper.create(this);
    }

    @Override
    protected void loadData() {
        providerManager = ProviderManager.getManager();
        new MainLoadingTask(this).execute();
    }

    @Override
    protected void initEvent() {
        loadingView.setRetryCallback(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionsUtils.checkRequestPermission(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 0) return;

    }

    public void onLoading(){
        loadingView.setState(LoadingView.State.PROGRESS);
        listView.setVisibility(View.INVISIBLE);
    }

    public void onResult(List<BaseProcItem> items){
        listView.setVisibility(View.VISIBLE);
        loadingView.setState(items == null || items.size() <= 0 ?
                LoadingView.State.FAIL : LoadingView.State.SUCCESS);
        adapter.setDataSource(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerGridView recyclerGridView, View view, int position) {
        final BaseProcItem item = adapter.getItem(position);
        if (item.getType() == BaseProcItem.TYPE_FILE){
            Intent intent = new Intent(this, CatViewActivity.class);
            intent.putExtra(CatViewActivity.KEY_DATA, item.getPath());
            startActivity(intent);
        }else {
            startActivity(new Intent(this, AppProcActivity.class));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_title_cat:
                dialogHelper.createInputDialog(R.string.dialog_title_input_path, this);
                break;
            case R.id.menu_title_performance:
                startActivity(new Intent(this, PerformanceActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogInput(DialogInterface dialogInterface, String text) {
        Intent intent = new Intent(this, CatViewActivity.class);
        intent.putExtra(CatViewActivity.KEY_DATA, text);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_retry:
                loadData();
                break;
        }
    }
}
