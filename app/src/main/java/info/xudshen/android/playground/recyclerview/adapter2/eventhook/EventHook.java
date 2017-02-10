package info.xudshen.android.playground.recyclerview.adapter2.eventhook;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import info.xudshen.android.playground.recyclerview.adapter2.UUniversalAdapter;

/**
 * @author xudong
 * @since 2017/2/6
 */

public abstract class EventHook<VH extends UUniversalAdapter.ViewHolder> {
    @NonNull
    final Class<VH> clazz;

    public EventHook(@NonNull Class<VH> clazz) {
        this.clazz = clazz;
    }

    public abstract void onEvent(@NonNull View view, @NonNull VH viewHolder,
                                 @NonNull UUniversalAdapter adapter);

    @Nullable
    public View onBind(@NonNull VH viewHolder) {
        return null;
    }

    @Nullable
    public List<? extends View> onBindMany(@NonNull VH viewHolder) {
        return null;
    }
}