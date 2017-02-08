package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xudong
 * @since 2017/2/6
 */

class EventHookHelper<VH extends UUniversalAdapter.ViewHolder> {
    private boolean isAfterBind = false;

    @NonNull
    private final UUniversalAdapter uUniversalAdapter;
    private final List<EventHook<VH>> eventHooks = new ArrayList<>();

    EventHookHelper(@NonNull UUniversalAdapter uUniversalAdapter) {
        this.uUniversalAdapter = uUniversalAdapter;
    }

    void add(@NonNull EventHook<VH> eventHook) {
        if (isAfterBind) {
            throw new IllegalStateException("can not add event hook after bind");
        }
        eventHooks.add(eventHook);
    }

    void bind(@NonNull UUniversalAdapter.ViewHolder viewHolder) {
        for (final EventHook<VH> eventHook : eventHooks) {
            if (!eventHook.clazz.isInstance(viewHolder)) continue;
            final VH vh = eventHook.clazz.cast(viewHolder);

            View view = eventHook.onBind(vh);
            if (view != null) {
                attachToView(eventHook, vh, view);
            }

            List<? extends View> views = eventHook.onBindMany(vh);
            if (views != null) {
                for (View v : views) {
                    attachToView(eventHook, vh, v);
                }
            }
        }
    }

    /**
     * bind {@param eventHook} to {@param view}
     */
    private void attachToView(@NonNull EventHook<VH> eventHook, @NonNull VH viewHolder,
                              @Nullable View view) {
        if (view == null) return;
        eventHook.onEvent(view, viewHolder, uUniversalAdapter);

        //set true once having one success bind
        isAfterBind = true;
    }
}
