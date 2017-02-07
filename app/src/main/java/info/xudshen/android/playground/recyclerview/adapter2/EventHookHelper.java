package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xudong
 * @since 2017/2/6
 */

class EventHookHelper {
    @NonNull
    private final UUniversalAdapter uUniversalAdapter;
    private final List<EventHook<RecyclerView.ViewHolder>> eventHooks = new ArrayList<>();

    EventHookHelper(@NonNull UUniversalAdapter uUniversalAdapter) {
        this.uUniversalAdapter = uUniversalAdapter;
    }

    void add(@NonNull EventHook<RecyclerView.ViewHolder> eventHook) {
        eventHooks.add(eventHook);
    }

    void bind(@NonNull RecyclerView.ViewHolder viewHolder) {
        for (final EventHook<RecyclerView.ViewHolder> eventHook : eventHooks) {
            View view = eventHook.onBind(viewHolder);
            if (view != null) {
                attachToView(eventHook, viewHolder, view);
            }

            List<? extends View> views = eventHook.onBindMany(viewHolder);
            if (views != null) {
                for (View v : views) {
                    attachToView(eventHook, viewHolder, v);
                }
            }
        }
    }

    /**
     * bind {@param eventHook} to {@param view}
     */
    void attachToView(@NonNull EventHook<RecyclerView.ViewHolder> eventHook,
                      @NonNull RecyclerView.ViewHolder viewHolder, @Nullable View view) {
        if (view == null) return;
        eventHook.onEvent(view, viewHolder, uUniversalAdapter);
    }
}
