package com.siato.app;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private RecyclerViewClickListener rvClickListener;
    private GestureDetector gestureDetector;

    public RecyclerViewTouchListener(Context context, final RecyclerView rv, final RecyclerViewClickListener rvClickListener){

        this.rvClickListener = rvClickListener;
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if(child != null && rvClickListener != null){
                    rvClickListener.onLongClick(child, rv.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());

        if(child != null && rv != null && gestureDetector.onTouchEvent(e)){
            rvClickListener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
