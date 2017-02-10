package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Collections;

/**
 * @author xudong
 * @since 2017/2/10
 */

public class SimpleListAdapter extends HeaderFooterListAdapter<UUniversalAdapter.AbstractModel<?>> {
    @NonNull
    @Override
    Collection<? extends AbstractModel<?>> transData(@NonNull AbstractModel<?> data) {
        return Collections.singletonList(data);
    }

    @NonNull
    @Override
    protected Collection<AbstractModel<?>> transDataList(
            @NonNull Collection<AbstractModel<?>> dataList) {
        return dataList;
    }

    @Override
    public void notifyDataChanged(@NonNull AbstractModel<?> data) {
        notifyModelChanged(data);
    }

    @Override
    public void removeData(@NonNull AbstractModel<?> data) {
        if (dataList.remove(data)) {
            removeModel(data);
        }
    }
}
