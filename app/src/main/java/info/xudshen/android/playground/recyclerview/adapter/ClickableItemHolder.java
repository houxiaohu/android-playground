package info.xudshen.android.playground.recyclerview.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * any class overriding the {@link #bindView(View, JUniversalAdapter.ViewHolder)} should call the super method
 *
 * @author xudong
 * @since 2017/2/6
 */
public abstract class ClickableItemHolder<M extends AbstractModelWithHolder> extends AbstractBindableHolder {
    public void onItemClick(View view, M itemModel) {
    }

    public boolean onItemLongClick(View view, M itemModel) {
        return false;
    }

    @CallSuper
    @Override
    public void bindView(@NonNull final View itemView, @NonNull final JUniversalAdapter.ViewHolder
            viewHolder) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                M model = getModelInAdapterPosition(viewHolder);
                if (model != null) {
                    onItemClick(itemView, model);
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                M model = getModelInAdapterPosition(viewHolder);
                if (model != null) {
                    return onItemLongClick(itemView, model);
                }
                return false;
            }
        });
    }

    @Nullable
    private M getModelInAdapterPosition(@NonNull JUniversalAdapter.ViewHolder viewHolder) {
        AbstractModel model = viewHolder.getModelAtAdapterPosition();
        M castModel = null;
        try {
            // noinspection unchecked
            castModel = (M) model;
        } catch (Exception e) {
        }
        return castModel;
    }
}


