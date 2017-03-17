package com.suwish.proc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.suwish.proc.R;


/**
 * @author min.su on 2017/2/18.
 */
public class LoadingView extends LinearLayout {

    private ProgressBar progressBar;
    private TextView textView;

    private Button button;

    private State state = State.PROGRESS;

    private OnClickListener clickListener;

    public LoadingView(Context context) {
        super(context);
        setup(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);
    }

    private void setup(Context context){
        inflate(context, R.layout.view_loading, this);
        progressBar = (ProgressBar)findViewById(R.id.loading_progress);
        textView = (TextView)findViewById(R.id.loading_text);
        button = (Button)findViewById(R.id.loading_retry);
        refresh();
    }

    public void setState(State state){
        if (state == null) return;
        if (this.state.ordinal() == state.ordinal()) return;
        this.state = state;
        refresh();
    }

    public State getState(){
        return state;
    }

    private void refresh(){
        if (state == null)
            state = State.PROGRESS;
        switch (state){
            case PROGRESS:
                progressBar.setVisibility(VISIBLE);
                textView.setVisibility(VISIBLE);
                textView.setText(R.string.widget_loading_now);
                button.setVisibility(GONE);
                break;
            case FAIL:
                progressBar.setVisibility(GONE);
                textView.setVisibility(VISIBLE);
                textView.setText(R.string.widget_loading_fail);
                button.setVisibility(clickListener == null ? GONE : VISIBLE);
                break;
            case SUCCESS:
                progressBar.setVisibility(GONE);
                textView.setVisibility(GONE);
                button.setVisibility(GONE);
                break;
        }
    }

    public void setRetryCallback(OnClickListener callback){
        this.clickListener = callback;
        button.setOnClickListener(callback);
    }

    /**
     * @see #refresh()
     */
    public enum State{

        PROGRESS,

        FAIL,

        SUCCESS
    }
}
