package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * @author xudong
 * @since 2017/2/8
 */

public abstract class OnClickEventHook<VH extends UUniversalAdapter.ViewHolder> extends EventHook<VH> {
    public OnClickEventHook(@NonNull Class<VH> clazz) {
        super(clazz);
    }

    public abstract void onClick(@NonNull View view, @NonNull VH viewHolder, int position,
                                 @NonNull UUniversalAdapter.AbstractModel rawModel);

    @Override
    public void onEvent(@NonNull View view, @NonNull final VH viewHolder,
                        @NonNull final UUniversalAdapter adapter) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                UUniversalAdapter.AbstractModel rawModel = adapter.getModel(position);
                if (position != NO_POSITION && rawModel != null) {
                    OnClickEventHook.this.onClick(v, viewHolder, position, rawModel);
                }
            }
        });
    }
}
