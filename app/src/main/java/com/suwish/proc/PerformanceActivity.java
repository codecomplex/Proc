package com.suwish.proc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.suwish.proc.task.SystemPerformanceTask;
import com.suwish.proc.task.entity.Performance;

/**
 * @author by min.su on 2017/2/26.
 */
public class PerformanceActivity extends BaseActivity {

    private TextView textCpu;
    private TextView textMemory;
    private TextView textNet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
    }

    @Override
    protected void setupView() {
        textCpu = (TextView)findViewById(R.id.text_cpu_usage);
        textMemory = (TextView)findViewById(R.id.text_memory);
        textNet = (TextView)findViewById(R.id.text_network);
    }

    public void onLoading(){}

    public void onResult(Performance performance){
        textCpu.setText(performance.getCpuUsage());
        String memory = "Memory Total : " + performance.getTotalMemory() +
//                "\n Memory Free cat : " + performance.getCatFreeMemory() +
                "\nMemory Free Api : " + performance.getApiFreeMemory();
        textMemory.setText(memory);
        String network = "Net send : " + performance.getNetSend() +
                "\nNet Receive : " + performance.getNetReceive();
        textNet.setText(network);
    }

    @Override
    protected void loadData() {
        new SystemPerformanceTask(this).execute();
    }

    @Override
    protected void initEvent() {

    }
}
