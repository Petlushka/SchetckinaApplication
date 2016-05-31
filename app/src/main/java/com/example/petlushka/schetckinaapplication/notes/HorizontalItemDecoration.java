package com.example.petlushka.schetckinaapplication.notes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Irina on 21.05.2016.
 */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {

    private final int mDecorationHeight;

    public HorizontalItemDecoration(int mDecorationHeight) {
        this.mDecorationHeight = mDecorationHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mDecorationHeight;
        }
    }
}
