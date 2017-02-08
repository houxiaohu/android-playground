package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * @author xudong
 * @since 2017/2/8
 */

public abstract class OnLongClickEventHook<VH extends RecyclerView.ViewHolder> extends
        EventHook<VH> {
    public OnLongClickEventHook(@NonNull Class<VH> clazz) {
        super(clazz);
    }

    public abstract boolean onLongClick(@NonNull View view, @NonNull VH viewHolder, int position,
                                        @NonNull UUniversalAdapter.AbstractModel rawModel);

    @Override
    public void onEvent(@NonNull View view, @NonNull final VH viewHolder,
                        @NonNull final UUniversalAdapter adapter) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                UUniversalAdapter.AbstractModel rawModel = adapter.getModel(position);
                return position != NO_POSITION && rawModel != null
                        && OnLongClickEventHook.this.onLongClick(v, viewHolder, position, rawModel);
            }
        });
    }
}
