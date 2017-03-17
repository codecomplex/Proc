package com.suwish.proc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.suwish.proc.provider.cmd.CatResolver;
import com.suwish.proc.task.CatAppTask;

/**
 * @author min.su on 2017/2/18.
 */
public class CatViewActivity extends BaseActivity {

    public static final String KEY_TITLE = "title";
    public static final String KEY_DATA = "data";

    private TextView titleView;
    private TextView detailView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
    }

    @Override
    protected void setupView() {
        titleView = (TextView)findViewById(R.id.text_view);
        detailView = (TextView)findViewById(R.id.detail_view);
    }

    @Override
    protected void loadData() {
        String path = getIntent().getStringExtra(KEY_DATA);
        titleView.setText(path);
        new CatAppTask(this).execute(path);
    }

    public void onResult(CatResolver resolver){
        detailView.setText(resolver.parseDetail());
    }

    @Override
    protected void initEvent() {

    }
}
