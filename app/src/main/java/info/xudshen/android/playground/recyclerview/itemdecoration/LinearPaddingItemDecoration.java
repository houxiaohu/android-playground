package info.xudshen.android.playground.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xudong on 16/09/28.
 * PaddingItemDecoration for LinearLayoutManager
 */
public class LinearPaddingItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * {@link #orientation} = {@link OrientationHelper#VERTICAL}:
     * padding along the horizontal adjacent items
     * {@link #orientation} = {@link OrientationHelper#HORIZONTAL}:
     * padding along the vertical adjacent items
     */
    private int headerPadding, footerPadding, itemPadding;

    private int orientation = OrientationHelper.VERTICAL;
    private LinearLayoutManager linearLayoutManager;

    public LinearPaddingItemDecoration(int headerPadding, int footerPadding, int itemPadding) {
        this.headerPadding = headerPadding;
        this.footerPadding = footerPadding;
        this.itemPadding = itemPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (linearLayoutManager == null && LinearLayoutManager.class.isInstance(parent.getLayoutManager())) {
            linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
            orientation = linearLayoutManager.getOrientation();
        }
        if (linearLayoutManager == null) return;

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) return;

        final int itemCount = state.getItemCount();
        switch (orientation) {
            case OrientationHelper.VERTICAL: {
                /** first position */
                if (itemPosition == 0) {
                    outRect.set(outRect.left, headerPadding, outRect.right, itemPadding);
                }
                /** write bottomPadding of positions after first */
                if (itemPosition > 0) {
                    outRect.set(outRect.left, outRect.top, outRect.right, itemPadding);
                }
                /** write bottomPadding of last position */
                if (itemPosition == itemCount - 1) {
                    outRect.set(outRect.left, outRect.top, outRect.right, footerPadding);
                }
                break;
            }
            case OrientationHelper.HORIZONTAL: {
                /** first position */
                if (itemPosition == 0) {
                    outRect.set(headerPadding, outRect.top, itemPadding, outRect.bottom);
                }
                /** write rightPadding of positions after first */
                if (itemPosition > 0) {
                    outRect.set(outRect.left, outRect.top, itemPadding, outRect.bottom);
                }
                /** write rightPadding of last position */
                if (itemPosition == itemCount - 1) {
                    outRect.set(outRect.left, outRect.top, footerPadding, outRect.bottom);
                }
                break;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
