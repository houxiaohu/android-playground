package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;

import java.util.Collection;

/**
 * @author xudong
 * @since 2017/2/10
 */

public class ExpandableListAdapter extends HeaderFooterListAdapter<ExpandableList> {
    private int dataListSize = 0;

    @Override
    protected boolean isDataListEmpty() {
        int size = 0;
        for (ExpandableList item : dataList) {
            size += item.size();
        }
        return size == 0;
    }

    @NonNull
    @Override
    Collection<? extends AbstractModel<?>> transData(@NonNull ExpandableList data) {
        return data.flatten();
    }

    @Override
    public void notifyDataChanged(@NonNull ExpandableList data) {
        replaceAllDataModels();
    }
}
