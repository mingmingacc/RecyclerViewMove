package mingming.com.recyclerviewmove.recyevent;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

import mingming.com.recyclerviewmove.RecyAdapter;

/**
 * Created by mingming on 2018/6/2.
 */

public class RecyItemTouchHelperCallback extends ItemTouchHelper.Callback{

    RecyclerView.Adapter mAdapter;
    boolean isSwipeEnable;
    boolean isFirstDragUnable;

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter){
        mAdapter = adapter;
        isSwipeEnable = true;
        isFirstDragUnable = false;
    }

    public RecyItemTouchHelperCallback(RecyclerView.Adapter adapter,boolean isSwipeEnable,boolean isFirstDragUnable){
        mAdapter = adapter;
        this.isSwipeEnable = isSwipeEnable;
        this.isFirstDragUnable = isFirstDragUnable;
    }
    //通过返回值来设置是否处理某次拖拽或者滑动事件
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags,swipeFlags);
        }else {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //注意：和拖拽的区别就是在这里
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
    }

    //当长按并进入拖拽状态时，拖拽的过程中不断的回调此方法
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition  = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (isFirstDragUnable && toPosition == 0) {
            return false;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(((RecyAdapter)mAdapter).getDataList(),i,i+1);
            }
        } else {
            for (int i = fromPosition; i > toPosition ; i--) {
                Collections.swap(((RecyAdapter)mAdapter).getDataList(),i,i-1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    //滑动删除的回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition  = viewHolder.getAdapterPosition();
        mAdapter.notifyItemRemoved(adapterPosition);
        ((RecyAdapter)mAdapter).getDataList().remove(adapterPosition);
    }

    //当长按item刚开始拖拽的时候调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //给被拖拽的item设置一个深颜色背景
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当完成拖拽手指松开的时候调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        //给已经完成拖拽的item恢复开始的背景
        //这里我们设置的颜色尽量和你item和xml中设置的颜色保持一致
        viewHolder.itemView.setBackgroundColor(Color.WHITE);
    }

    //重写此方法，返回false让它控制所有的item都不能拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return !isFirstDragUnable;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return isSwipeEnable;
    }
}
