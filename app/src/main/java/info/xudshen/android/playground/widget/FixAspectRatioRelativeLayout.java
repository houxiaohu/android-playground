package info.xudshen.android.playground.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import info.xudshen.android.playground.R;


/**
 * Created by xudong on 2016/12/13.
 */

public class FixAspectRatioRelativeLayout extends RelativeLayout {
    private double aspectRatio;

    private ViewAspectRatioMeasurer varm;

    public FixAspectRatioRelativeLayout(Context context) {
        super(context);
    }

    public FixAspectRatioRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixAspectRatioRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setAspectRatio(double aspectRatio) {
        if (Double.isInfinite(aspectRatio) || Double.isNaN(aspectRatio))
            return;
        if (this.aspectRatio != aspectRatio && aspectRatio > 0) {
            this.aspectRatio = aspectRatio;
            varm = new ViewAspectRatioMeasurer(aspectRatio);
            requestLayout();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixAspectRatioRelativeLayout);
        aspectRatio = a.getFloat(R.styleable.FixAspectRatioRelativeLayout_aspectRatio, 0);
        a.recycle();

        if (aspectRatio > 0) {
            varm = new ViewAspectRatioMeasurer(aspectRatio);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (varm != null) {
            varm.measure(widthMeasureSpec, heightMeasureSpec);
            int w = varm.getMeasuredWidth();
            int h = varm.getMeasuredHeight();
            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(w, MeasureSpec.getMode(MeasureSpec.EXACTLY)),
                    MeasureSpec.makeMeasureSpec(h, MeasureSpec.getMode(MeasureSpec.EXACTLY)));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
