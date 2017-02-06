package info.xudshen.android.playground.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * delay the {@link View#findViewById(int)} part in {@link JUniversalAdapter.ViewHolder}
 * to {@link #bindView(View, JUniversalAdapter.ViewHolder)}
 *
 * @author xudshen@hotmail.com
 * @since 2017/2/6
 */
public abstract class AbstractBindableHolder {
    /**
     * bind view to variables by {@link View#findViewById(int)},
     * also deal with the {@link View#setOnClickListener(View.OnClickListener)}
     * <p>
     * only called once with {@link JUniversalAdapter.ViewHolder},
     * see {@link JUniversalAdapter.ViewHolder#checkModelViewHolder(AbstractModel)}}
     */
    public abstract void bindView(@NonNull View itemView, @NonNull JUniversalAdapter.ViewHolder viewHolder);
}
