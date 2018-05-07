package info.xudshen.android.playground.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by xudong on 16/11/10
 * PaddingItemDecoration for GridLayoutManager
 */
public class GridPaddingItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * {@link #orientation} = {@link OrientationHelper#VERTICAL}:
     * padding along the horizontal adjacent items
     * {@link #orientation} = {@link OrientationHelper#HORIZONTAL}:
     * padding along the vertical adjacent items
     */
    private int spanHeaderPadding, spanFooterPadding, spanItemPadding;

    /**
     * sum of each single item's leftPadding and rightPadding
     * or
     * sum of each single item's topPadding and bottomPadding
     */
    private int calculatedItemTotalPadding;
    /**
     * used when {@link #orientation} = {@link OrientationHelper#VERTICAL}
     */
    private SparseIntArray itemRightPaddingMap = new SparseIntArray();
    /**
     * used when {@link #orientation} = {@link OrientationHelper#VERTICAL}
     */
    private SparseIntArray itemBottomPaddingMap = new SparseIntArray();

    private int orientation = OrientationHelper.VERTICAL;
    private int spanCount = 1;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    public GridPaddingItemDecoration(int spanHeaderPadding, int spanFooterPadding, int spanItemPadding) {
        this.spanHeaderPadding = spanHeaderPadding;
        this.spanFooterPadding = spanFooterPadding;
        this.spanItemPadding = spanItemPadding;
    }

    private boolean checkSpanInfo(RecyclerView parent) {
        if (spanSizeLookup == null && GridLayoutManager.class.isInstance(parent.getLayoutManager())) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
            if (gridLayoutManager != null) {
                orientation = gridLayoutManager.getOrientation();
                spanCount = gridLayoutManager.getSpanCount();
                spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

                calculatedItemTotalPadding = (int) ((spanHeaderPadding + spanFooterPadding
                        + (spanCount - 1) * spanItemPadding) * 1.0f / spanCount);
            }
        }
        return spanSizeLookup != null;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (!checkSpanInfo(parent)) return;

        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) return;

        //占满整个span的不设置padding
        if (spanSizeLookup.getSpanSize(itemPosition) == spanCount) return;

        //对span内的每个item设置不同的padding
        int spanIndex = spanSizeLookup.getSpanIndex(itemPosition, spanCount);
        switch (orientation) {
            case OrientationHelper.VERTICAL: {
                /** first position */
                if (spanIndex == 0) {
                    int left = spanHeaderPadding;
                    int right = calculatedItemTotalPadding - left;
                    itemRightPaddingMap.put(spanIndex, right);

                    outRect.set(left, outRect.top, right, outRect.bottom);
                }
                /** write rightPadding of positions after first */
                if (spanIndex > 0) {
                    int leftItemRightPadding = itemRightPaddingMap.get(spanIndex - 1, 0);

                    int left = spanItemPadding - leftItemRightPadding;
                    int right = calculatedItemTotalPadding - left;
                    itemRightPaddingMap.put(spanIndex, right);

                    outRect.set(left, outRect.top, right, outRect.bottom);
                }
                /** write rightPadding of last position */
                if (spanIndex == spanCount - 1) {
                    outRect.set(outRect.left, outRect.top, spanFooterPadding, outRect.bottom);
                }
                break;
            }
            case OrientationHelper.HORIZONTAL: {
                /** first position */
                if (spanIndex == 0) {
                    int top = spanHeaderPadding;
                    int bottom = calculatedItemTotalPadding - top;
                    itemBottomPaddingMap.put(spanIndex, bottom);

                    outRect.set(outRect.left, top, outRect.right, bottom);
                }
                /** write bottomPadding of positions after first */
                if (spanIndex > 0) {
                    int topItemBottomPadding = itemBottomPaddingMap.get(spanIndex - 1, 0);

                    int top = spanItemPadding - topItemBottomPadding;
                    int bottom = calculatedItemTotalPadding - top;
                    itemBottomPaddingMap.put(spanIndex, bottom);

                    outRect.set(outRect.left, top, outRect.right, bottom);
                }
                /** write bottomPadding of last position */
                if (spanIndex == spanCount - 1) {
                    outRect.set(outRect.left, outRect.top, outRect.right, spanFooterPadding);
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
