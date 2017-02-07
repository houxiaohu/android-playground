package info.xudshen.android.playground.recyclerview.adapter2;

/**
 * @author xudong
 * @since 2017/2/7
 */

public interface IDiffUtilHelper<T> {
    /**
     * see {@link android.support.v7.util.DiffUtil.Callback#areItemsTheSame(int, int)}
     */
    boolean isItemTheSame(T item);

    /**
     * see {@link android.support.v7.util.DiffUtil.Callback#areContentsTheSame(int, int)}
     */
    boolean isContentTheSame(T item);
}