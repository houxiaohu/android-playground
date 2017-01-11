package info.xudshen.android.playground.animation.svg;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xudong on 16/8/18.
 */

public class PathTracingDrawable extends Drawable implements Animatable {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final SvgHelper mSvg = new SvgHelper(mPaint);
    private int mSvgResource;

    private final Object mSvgLock = new Object();
    private List<SvgHelper.SvgPath> mPaths = new ArrayList<SvgHelper.SvgPath>(0);

    private float mPhase;
    private AnimatorSet animatorSet;

    private void setupPaint() {
        mPaint.setColor(0xFF20B7AF);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public PathTracingDrawable(Context context, int mSvgResource) {
        this.mSvgResource = mSvgResource;
        mSvg.load(context, this.mSvgResource);

        setupPaint();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mPaths = mSvg.getPathsForViewport(bounds.width(), bounds.height());
    }

    //<editor-fold desc="Drawable">
    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (mSvgLock) {
            canvas.save();
            final int count = mPaths.size();
            for (int i = 0; i < count; i++) {
                SvgHelper.SvgPath svgPath = mPaths.get(i);
                canvas.drawPath(svgPath.renderPath, mPaint);
            }
            canvas.restore();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Animatable">
    public static final Property<PathTracingDrawable, Float> PHASE =
            new Property<PathTracingDrawable, Float>(Float.class, "phase") {
                @Override
                public void set(PathTracingDrawable pathTracingDrawable, Float value) {
                    pathTracingDrawable.setPhase(value);
                }

                @Override
                public Float get(PathTracingDrawable pathTracingDrawable) {
                    return pathTracingDrawable.getPhase();
                }
            };


    public float getPhase() {
        return mPhase;
    }

    public void setPhase(float phase) {
        mPhase = phase;
        synchronized (mSvgLock) {
            updatePathsPhaseLocked();
        }
        invalidateSelf();
    }

    private void updatePathsPhaseLocked() {
        final int count = mPaths.size();
        for (int i = 0; i < count; i++) {
            SvgHelper.SvgPath svgPath = mPaths.get(i);
            svgPath.renderPath.reset();
            if (mPhase >= 0) {
                svgPath.measure.getSegment(0.0f, svgPath.length * mPhase, svgPath.renderPath, true);
            } else {
                svgPath.measure.getSegment(svgPath.length * (1 + mPhase), svgPath.length, svgPath.renderPath, true);
            }
            // Required only for Android 4.4 and earlier
            svgPath.renderPath.rLineTo(0.0f, 0.0f);
        }
    }

    @Override
    public void start() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, PHASE, 0.0f, 1.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, PHASE, -1.0f, 0f);
        animator1.setInterpolator(new AccelerateInterpolator());
        animator2.setInterpolator(new DecelerateInterpolator());
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1, animator2);
        animatorSet.setDuration(4000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
    //</editor-fold>
}
