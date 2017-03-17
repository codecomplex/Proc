package com.suwish.proc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.suwish.proc.R;
import com.suwish.proc.adapter.items.BaseProcItem;
import com.suwish.proc.widget.RecyclerGridView;

/**
 * @author min.su on 2017/2/18.
 */
public class MainAdapter extends RecyclerGridView.Adapter<BaseProcItem> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proc, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerGridView.ViewHolder holder, int position) {
        BaseProcItem item = getItem(position);
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.textView.setText(item.getPath());
        viewHolder.imageView.setVisibility(item.getType() == BaseProcItem.TYPE_DIR ? View.VISIBLE : View.INVISIBLE);
    }

    private static class ViewHolder extends RecyclerGridView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ViewHolder(RecyclerGridView.Adapter adapter, View itemView) {
            super(adapter, itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
            imageView = (ImageView)itemView.findViewById(R.id.image_view);
        }
    }
}
