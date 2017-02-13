package info.xudshen.android.playground.recyclerview.adapter2.eventhook;

import android.support.annotation.NonNull;
import android.view.View;

import info.xudshen.android.playground.recyclerview.adapter2.UniversalAdapter;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * @author xudong
 * @since 2017/2/8
 */

public abstract class OnLongClickEventHook<VH extends UniversalAdapter.ViewHolder> extends
        EventHook<VH> {
    public OnLongClickEventHook(@NonNull Class<VH> clazz) {
        super(clazz);
    }

    public abstract boolean onLongClick(@NonNull View view, @NonNull VH viewHolder, int position,
                                        @NonNull UniversalAdapter.AbstractModel rawModel);

    @Override
    public void onEvent(@NonNull View view, @NonNull final VH viewHolder,
                        @NonNull final UniversalAdapter adapter) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                UniversalAdapter.AbstractModel rawModel = adapter.getModel(position);
                return position != NO_POSITION && rawModel != null
                        && OnLongClickEventHook.this.onLongClick(v, viewHolder, position, rawModel);
            }
        });
    }
}
