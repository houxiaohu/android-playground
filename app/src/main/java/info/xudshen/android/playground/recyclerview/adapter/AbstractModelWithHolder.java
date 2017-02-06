package info.xudshen.android.playground.recyclerview.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

/**
 * @author xudshen@hotmail.com
 * @since 2017/2/6
 */
public abstract class AbstractModelWithHolder<BH extends AbstractBindableHolder>
        extends AbstractModel<BH> {
    public AbstractModelWithHolder(@LayoutRes int viewType) {
        super(viewType);
    }

    @NonNull
    public abstract BH createBindableHolder(View view);

    @Override
    public void bindData(@NonNull BH view) {
        super.bindData(view);
    }

    @Override
    public void bindData(@NonNull BH view, @NonNull List<Object> payloads) {
        super.bindData(view, payloads);
    }
}
