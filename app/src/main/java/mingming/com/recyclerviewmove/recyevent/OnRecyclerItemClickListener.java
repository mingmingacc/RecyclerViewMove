package mingming.com.recyclerviewmove.recyevent;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mingming on 2018/6/2.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
        //通过一个手势探测器 GestureDetectorCompat 来探测屏幕事件
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //通过手势监听器 SimpleOnGestureListener 来识别手势事件的种类，然后调用我们设置的对应的回调方法
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        //一次单独的轻触抬起手指操作，就是普通的点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //通过findChildViewUnder()获得点击的 item ，同时我们调用 RecyclerView 的另一个方法 getChildViewHolder()，可以获得该 item 的 ViewHolder，最后再回调我们定义的虚方法 onItemClick()
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            }
            return true;
        }

        //长按屏幕超过一定时长，就会出发，长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }
    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);
}
