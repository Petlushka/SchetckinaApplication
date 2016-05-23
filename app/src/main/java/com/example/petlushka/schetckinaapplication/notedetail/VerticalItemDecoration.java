package com.example.petlushka.schetckinaapplication.notedetail;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Vertical decorator for recycleView's items.
 */
public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final int mDecorationWidth;

    public VerticalItemDecoration(int mDecorationWidth) {
        this.mDecorationWidth = mDecorationWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = mDecorationWidth;
        }
    }
}
