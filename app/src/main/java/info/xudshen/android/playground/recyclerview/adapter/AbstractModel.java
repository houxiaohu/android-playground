package info.xudshen.android.playground.recyclerview.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

/**
 * @author xudshen@hotmail.com
 * @since 2017/2/6
 */
public abstract class AbstractModel<V> {
    @LayoutRes
    private final int viewType;

    public int getViewType() {
        return viewType;
    }

    public AbstractModel(@LayoutRes int viewType) {
        this.viewType = viewType;
    }

    /**
     * bind data to view
     * <p>
     * do NOT call {@link View#setOnClickListener(View.OnClickListener)} here which may create
     * lots of instance of {@link android.view.View.OnClickListener}
     */
    public void bindData(@NonNull V view) {
    }

    /**
     * default call {@link #bindData(Object)}
     */
    public void bindData(@NonNull V view, @NonNull List<Object> payloads) {
        bindData(view);
    }
}
