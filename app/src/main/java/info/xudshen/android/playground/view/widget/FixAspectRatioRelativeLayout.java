package info.xudshen.android.playground.view.widget;

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

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixAspectRatioLayout);
        aspectRatio = a.getFloat(R.styleable.FixAspectRatioLayout_aspectRatio, 0);
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
