package info.xudshen.android.playground.animation.path;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by xudong on 16/8/17.
 */

public class CircularDrawable extends Drawable implements Animatable {
    private final int paintColor = 0xFF20B7AF;
    private Paint drawPaint;
    private Path path;
    private Path renderPath;
    private PathMeasure pathMeasure;

    private float animatePhase = 0f;
    private AnimatorSet animatorSet;

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void setupPath() {
        path = new Path();
        path.moveTo(300, 300);
        path.arcTo(300, 300, 600, 700, 0, 270, true);
        path.close();

        renderPath = new Path();
        pathMeasure = new PathMeasure(path, false);
    }

    public CircularDrawable() {
        setupPaint();
        setupPath();
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
        return 0;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(renderPath, drawPaint);
    }
    //</editor-fold>

    //<editor-fold desc="Animatable">
    private void updatePath() {
        renderPath.reset();
        pathMeasure.getSegment(animatePhase >= 0 ? 0.0f : pathMeasure.getLength() * (1 + animatePhase),
                animatePhase >= 0 ? pathMeasure.getLength() * animatePhase : pathMeasure.getLength(),
                renderPath, true);

        invalidateSelf();
    }

    public float getAnimatePhase() {
        return animatePhase;
    }

    public void setAnimatePhase(float animatePhase) {
        this.animatePhase = animatePhase;
        updatePath();
    }

    @Override
    public void start() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "animatePhase", 0.0f, 1.0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "animatePhase", -1.0f, 0f);
        animator1.setInterpolator(new AccelerateInterpolator());
        animator2.setInterpolator(new DecelerateInterpolator());
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1, animator2);
        animatorSet.setDuration(2000);
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
        animatorSet.pause();
    }

    @Override
    public boolean isRunning() {
        return animatorSet != null && animatorSet.isRunning();
    }
    //</editor-fold>
}
