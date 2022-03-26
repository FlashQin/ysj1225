package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

public class MyItemTouchHelper extends ItemTouchHelper.Callback {

    Context context;
    UpImgAdapter1 myAdapter;

    public MyItemTouchHelper(Context context, UpImgAdapter1 myAdapter) {
        this.context = context;
        this.myAdapter = myAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();

        try {
            // Log.e("cjh>>>", "fromPosition:" + fromPosition + "   toPosition:" + toPosition + "   size-1:" + (myAdapter.getImgs().size()));
            if (fromPosition != myAdapter.getImgs().size() && toPosition != myAdapter.getImgs().size()) {
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        if (i < myAdapter.getImgs().size()) {
                            Collections.swap(myAdapter.getImgs(), i, i + 1);
                        }
                    }
                } else {
                    // cjh 修改数组下标越界 并try catch
                    for (int i = fromPosition; i > toPosition; i--) {
                        if (i > 0 && myAdapter.getImgs().size() > i) {
                            Collections.swap(myAdapter.getImgs(), i, i - 1);
                        }
                    }
                }
                myAdapter.notifyItemMoved(fromPosition, toPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }


    /**
     * 长按选中Item的时候开始调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 手指松开的时候还原
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }

    /**
     * 重写拖拽不可用
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

}
