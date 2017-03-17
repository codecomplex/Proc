package com.suwish.proc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suwish.proc.R;
import com.suwish.proc.task.entity.AppProcSource;
import com.suwish.proc.widget.RecyclerGridView;

/**
 * @author min.su on 2017/2/18.
 */
public class AppProcAdapter extends RecyclerGridView.Adapter<AppProcSource> {


    @Override
    public RecyclerGridView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proc, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerGridView.ViewHolder holder, int position) {
        AppProcSource source = getItem(position);
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.imageView.setVisibility(View.VISIBLE);
        viewHolder.logoView.setVisibility(View.GONE);
        viewHolder.titleView.setText(source.parseDisplay());
    }

    private static class ViewHolder extends RecyclerGridView.ViewHolder{
        TextView titleView;
        ImageView imageView;
        ImageView logoView;
        ViewHolder(RecyclerGridView.Adapter adapter, View itemView) {
            super(adapter, itemView);
            titleView = (TextView)itemView.findViewById(R.id.text_view);
            imageView = (ImageView)itemView.findViewById(R.id.image_view);
            logoView = (ImageView)itemView.findViewById(R.id.image_icon);
        }
    }
}
