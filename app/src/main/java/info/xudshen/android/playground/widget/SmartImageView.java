package info.xudshen.android.playground.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;

import info.xudshen.android.playground.R;

/**
 * Created by xudong on 2017/1/17.
 */
public class SmartImageView extends AppCompatImageView {
    private int fadeInDuration = 0;

    private LoadImageDelegate loadImageDelegate = null;
    private boolean waitingForMeasure = true;

    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmartImageView);
        fadeInDuration = a.getInteger(R.styleable.SmartImageView_fadeInDuration, 0);
        a.recycle();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable != null && fadeInDuration > 0) {
            animate(this, fadeInDuration);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (bm != null && fadeInDuration > 0) {
            animate(this, fadeInDuration);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed && waitingForMeasure) {
            waitingForMeasure = false;

            if (loadImageDelegate != null) {
                loadImageDelegate.onDelegate(getWidth(), getHeight());
                loadImageDelegate = null;
            }
        }
    }

    public synchronized void loadImage(LoadImageDelegate loadImageDelegate) {
        if (loadImageDelegate == null) return;

        if (waitingForMeasure) {
            this.loadImageDelegate = loadImageDelegate;
        } else {
            this.loadImageDelegate = null;
            loadImageDelegate.onDelegate(getWidth(), getHeight());
        }
    }

    public interface LoadImageDelegate {
        void onDelegate(int width, int height);
    }

    /**
     * from {@link com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer#animate(View, int)}
     */
    public static void animate(View imageView, int durationMillis) {
        if (imageView != null) {
            AlphaAnimation fadeImage = new AlphaAnimation(0.0F, 1.0F);
            fadeImage.setDuration((long) durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            imageView.startAnimation(fadeImage);
        }
    }
}
