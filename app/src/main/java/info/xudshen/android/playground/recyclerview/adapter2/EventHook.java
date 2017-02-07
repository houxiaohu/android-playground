package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * @author xudong
 * @since 2017/2/6
 */

public abstract class EventHook<VH extends RecyclerView.ViewHolder> {
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
