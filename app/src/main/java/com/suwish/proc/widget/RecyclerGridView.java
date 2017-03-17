package com.suwish.proc.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 使用新的Support v7 RecyclerView 构建的可回收的列表元素。
 * 之前没有解决子元素绘制顺序与放大动画，以及边框问题。<p/>
 *
 * 由于完全重新实现AdapterView，并支持点击触摸和焦点事件，
 * 存在非常多的问题，因此使用RecyclerView作为基础控件进行扩展。
 *
 * <p/>
 *
 * 由于RecyclerView没有实现过多的事件和默认的界面绘制方法，
 * 所以需要自己进行再次封装，但是由于RecyclerView开放的接口
 * 非常多，并且设计上非常的合理以及高效，所以从效果上可以实现列表操作
 *
 * <p/>
 *
 * 布局方式：<br/>
 *
 * 布局方式总的分为四种，并且是相互依赖关系，为了封装后使用方便，
 * 将其划分为四种<p/>
 *
 * <ul>
 *     <li>LAYOUT_HORIZONTAL_LINE：横向单行列表</li>
 *     <li>LAYOUT_HORIZONTAL_GRID：横向多行列表</li>
 *     <li>LAYOUT_VERTICAL_LINE：纵向单行列表</li>
 *     <li>LAYOUT_VERTICAL_GRID：纵向多行列表</li>
 * </ul>
 *
 * <p/>
 *
 * 当为多行时需要配合<code>layoutSpan</code>属性，即spanCount。
 * 表示行数(横向)、列数(纵向)
 *
 * <p/>
 *
 * 使用方法set itemWidth, itemHeight, margin, setLayoutOrientation,
 * setAdapter
 *
 * <p/>
 *
 * <b>事件</b> <br/>
 *  目前仅仅实现单击和长按事件，即<code>OnItemClickListener</code>，
 *  <code>OnItemLongClickListener</code>其他类型的事件 与点击相同(未实现),
 *  空鼠与触摸事件实际上由子容器的焦点事件控制和激发
 *
 *
 * @author min.su on 2016/12/27.
 */
public class RecyclerGridView extends RecyclerView {

    public static final int LAYOUT_HORIZONTAL_LINE = 1;
    public static final int LAYOUT_HORIZONTAL_GRID = 2;
    public static final int LAYOUT_VERTICAL_LINE = 3;
    public static final int LAYOUT_VERTICAL_GRID = 4;

    private int mLayoutSpan = 1;
    private int mLayoutOrientation = -1;

    private SpaceItemDecoration itemDecoration;
    private LayoutManager layoutManager = null;

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
//    private OnItemSelectionListener mItemSelectionListener;

    private ContextMenu.ContextMenuInfo mContextMenuInfo = null;

    public RecyclerGridView(Context context) {
        super(context);
    }

    public RecyclerGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerGridView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
//        if (!(adapter instanceof Adapter))
//            throw new IllegalArgumentException("in RecycleGridView Adapter must use " +
//                    "RecycleGridView.Adapter");
        super.setAdapter(adapter);
    }

    public void setLayoutOrientation(int LayoutOrientation) {
        setLayoutOrientation(LayoutOrientation, 1);
    }
    public void setLayoutOrientation(int LayoutOrientation, int LayoutSpan) {
        mLayoutOrientation = LayoutOrientation;
        mLayoutSpan = LayoutSpan;
        buildLayout();
    }

    private void buildLayout() {
        if (layoutManager != null || mLayoutOrientation == -1) return;
        switch (mLayoutOrientation) {
            case LAYOUT_HORIZONTAL_LINE:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case LAYOUT_HORIZONTAL_GRID:
                layoutManager = new GridLayoutManager(getContext(), mLayoutSpan, LinearLayoutManager.HORIZONTAL, false);
                break;
            case LAYOUT_VERTICAL_LINE:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                break;
            case LAYOUT_VERTICAL_GRID:
                layoutManager = new GridLayoutManager(getContext(), mLayoutSpan, LinearLayoutManager.VERTICAL, false);
                break;
        }
        if (layoutManager == null)
            throw new IllegalArgumentException("Orientation is not in (" +
                    "LAYOUT_HORIZONTAL_LINE, LAYOUT_HORIZONTAL_GRID, LAYOUT_VERTICAL_LINE," +
                    "LAYOUT_VERTICAL_GRID)");
        setLayoutManager(layoutManager);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        itemLongClickListener = longClickListener;
    }
//    public void setOnItemSelectionListener(OnItemSelectionListener itemSelectionListener){
//        mItemSelectionListener = itemSelectionListener;
//    }


    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int position = getChildAdapterPosition(originalView);
        if (position >= 0) {
//            final long itemId = getAdapter().getItemId(position);
//            mContextMenuInfo = createContextMenuInfo(position);
            createContextMenuInfo(position);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    private void createContextMenuInfo(int position) {
        if (mContextMenuInfo == null)
            mContextMenuInfo = new ContextMenuInfo(position);
        else
            ((ContextMenuInfo)mContextMenuInfo).setValues(position);
    }
    public static class ContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public int position;
        ContextMenuInfo(int position) {
            setValues(position);
        }
        void setValues(int position){
            this.position = position;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerGridView recyclerGridView, View view, int position);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClick(RecyclerGridView recyclerGridView, View view, int position);
    }
//    public interface OnItemSelectionListener{
//        void onItemSelection(RecycleGridView recycleGridView, View view, int position);
//    }

    public static abstract class Adapter<T> extends RecyclerView.Adapter<ViewHolder> implements
            OnClickListener, OnLongClickListener{

        private RecyclerGridView gridView;

        private List<T> dataSource = new ArrayList<>();

        public void addItem(T t){
            dataSource.add(t);
        }
        public void removeItem(int index){
            if (index < 0 || index >= dataSource.size()) return;
            dataSource.remove(index);
        }
        public void addDataSource(List<T> list){
            dataSource.addAll(list);
        }

        public void setDataSource(List<T> list){
            dataSource.clear();
            dataSource.addAll(list);
        }

        public void clear(){
            dataSource.clear();
        }
        @Override
        public int getItemCount() {
            return dataSource.size();
        }

        public T getItem(int position){
            return position < 0 || position >= dataSource.size() ? null : dataSource.get(position);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            gridView = (RecyclerGridView)recyclerView;
        }

        @Override
        public void onClick(View view) {
            if (gridView == null || gridView.itemClickListener == null) return;
            gridView.itemClickListener.onItemClick(gridView, view, gridView.getChildAdapterPosition(view));
        }

        @Override
        public boolean onLongClick(View view) {
            return !(gridView == null || gridView.itemLongClickListener == null)
                    && gridView.itemLongClickListener.onItemLongClick(gridView, view, gridView.getChildAdapterPosition(view));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(Adapter adapter, View itemView) {
            super(itemView);
            itemView.setOnClickListener(adapter);
            itemView.setOnLongClickListener(adapter);
        }
    }

    class SpaceItemDecoration extends ItemDecoration{

        private int mSpace;

        public void setSpace(int space){
            mSpace = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            outRect.right = mSpace;
            outRect.bottom = mSpace;
        }
    }
}
