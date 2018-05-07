package info.xudshen.android.playground.cement;

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
    private final CementModel headerModel, emptyViewModel, footerModel;
    @NonNull
    private final List<CementModel<?>> childModels = new ArrayList<>();

    public ExpandableList() {
        this(null, null, null);
    }

    public ExpandableList(@Nullable CementModel headerModel) {
        this(headerModel, null, null);
    }

    public ExpandableList(@Nullable CementModel headerModel,
                          @Nullable CementModel emptyViewModel,
                          @Nullable CementModel footerModel) {
        this.headerModel = headerModel;
        this.emptyViewModel = emptyViewModel;
        this.footerModel = footerModel;
    }

    @Nullable
    public CementModel getHeaderModel() {
        return headerModel;
    }

    @Nullable
    public CementModel getEmptyViewModel() {
        return emptyViewModel;
    }

    @Nullable
    public CementModel getFooterModel() {
        return footerModel;
    }

    @NonNull
    public List<CementModel<?>> getChildModels() {
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
        if (footerModel != null) {
            size++;
        }
        return size;
    }

    @NonNull
    public Collection<? extends CementModel<?>> flatten() {
        List<CementModel<?>> all = new ArrayList<>();
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
        if (footerModel != null) {
            all.add(footerModel);
        }
        return all;
    }
}
