package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xudong
 * @since 2017/2/10
 */

public class ExpandableList {
    @Nullable
    private final UUniversalAdapter.AbstractModel headerModel, emptyViewModel;
    @NonNull
    private final List<UUniversalAdapter.AbstractModel<?>> childModels = new ArrayList<>();

    public ExpandableList(@Nullable UUniversalAdapter.AbstractModel headerModel) {
        this(headerModel, null);
    }

    public ExpandableList(@Nullable UUniversalAdapter.AbstractModel headerModel,
                          @Nullable UUniversalAdapter.AbstractModel emptyViewModel) {
        this.headerModel = headerModel;
        this.emptyViewModel = emptyViewModel;
    }

    @Nullable
    public UUniversalAdapter.AbstractModel getHeaderModel() {
        return headerModel;
    }

    @Nullable
    public UUniversalAdapter.AbstractModel getEmptyViewModel() {
        return emptyViewModel;
    }

    @NonNull
    public List<UUniversalAdapter.AbstractModel<?>> getChildModels() {
        return childModels;
    }

    public int size() {
        int size = 0;
        if (headerModel != null) {
            size++;
        }
        if (childModels.size() == 0) {
            if (emptyViewModel != null) {
                size++;
            }
        } else {
            size += childModels.size();
        }
        return size;
    }

    @NonNull
    public Collection<? extends UUniversalAdapter.AbstractModel<?>> flatten() {
        List<UUniversalAdapter.AbstractModel<?>> all = new ArrayList<>();
        if (headerModel != null) {
            all.add(headerModel);
        }
        if (childModels.size() == 0) {
            if (emptyViewModel != null) {
                all.add(emptyViewModel);
            }
        } else {
            all.addAll(childModels);
        }
        return all;
    }
}
