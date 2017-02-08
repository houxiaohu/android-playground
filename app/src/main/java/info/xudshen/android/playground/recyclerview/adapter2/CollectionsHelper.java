package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author xudong
 * @since 2017/2/7
 */

public class CollectionsHelper {
    @Nullable
    public static <T> T get(@Nullable List<T> list, int index, @Nullable T defaultValue) {
        return (list != null && index >= 0 && index < list.size()) ? list.get(index) : defaultValue;
    }

    @Nullable
    public static <T> T getOrNull(@Nullable List<T> list, int index) {
        return get(list, index, null);
    }
}
