package info.xudshen.android.playground.recyclerview.adapter2;

import java.util.List;

/**
 * @author xudong
 * @since 2017/2/7
 */

public class CollectionsHelper {
    public static <T> T get(List<T> list, int index, T defaultValue) {
        return (list != null && index >= 0 && index < list.size()) ? list.get(index) : defaultValue;
    }

    public static <T> T getOrNull(List<T> list, int index) {
        return get(list, index, null);
    }
}
