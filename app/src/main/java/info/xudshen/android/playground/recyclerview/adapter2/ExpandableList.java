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
    private final UniversalAdapter.AbstractModel headerModel, emptyViewModel;
    @NonNull
    private final List<UniversalAdapter.AbstractModel<?>> childModels = new ArrayList<>();

    public ExpandableList(@Nullable UniversalAdapter.AbstractModel headerModel) {
        this(headerModel, null);
    }

    public ExpandableList(@Nullable UniversalAdapter.AbstractModel headerModel,
                          @Nullable UniversalAdapter.AbstractModel emptyViewModel) {
        this.headerModel = headerModel;
        this.emptyViewModel = emptyViewModel;
    }

    @Nullable
    public UniversalAdapter.AbstractModel getHeaderModel() {
        return headerModel;
    }

    @Nullable
    public UniversalAdapter.AbstractModel getEmptyViewModel() {
        return emptyViewModel;
    }

    @NonNull
    public List<UniversalAdapter.AbstractModel<?>> getChildModels() {
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
    public Collection<? extends UniversalAdapter.AbstractModel<?>> flatten() {
        List<UniversalAdapter.AbstractModel<?>> all = new ArrayList<>();
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
