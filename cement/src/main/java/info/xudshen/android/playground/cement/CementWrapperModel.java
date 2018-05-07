package info.xudshen.android.playground.cement;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public abstract class CementWrapperModel<VH extends CementWrapperViewHolder<MVH>,
        M extends CementModel<MVH>, MVH extends CementViewHolder> extends CementModel<VH> {
    //todo: not allow attached model as childModel
    @NonNull
    protected final M childModel;

    @NonNull
    public M getChildModel() {
        return childModel;
    }

    public CementWrapperModel(@NonNull M childModel) {
        super();
        this.childModel = childModel;
    }

    @Override
    int getViewType() {
        return 31 * super.getViewType() + childModel.getViewType();
    }

    @Override
    public boolean shouldSaveViewState() {
        return childModel.shouldSaveViewState();
    }

    @CallSuper
    @Override
    public void bindData(@NonNull VH holder) {
        childModel.bindData(holder.getChildViewHolder());
    }

    @CallSuper
    @Override
    public void bindData(@NonNull VH holder, @Nullable List<Object> payloads) {
        childModel.bindData(holder.getChildViewHolder(), payloads);
    }

    @CallSuper
    @Override
    public void unbind(@NonNull VH holder) {
        childModel.unbind(holder.getChildViewHolder());
    }

    @CallSuper
    @Override
    public void attachedToWindow(@NonNull VH holder) {
        childModel.attachedToWindow(holder.getChildViewHolder());
    }

    @CallSuper
    @Override
    public void detachedFromWindow(@NonNull VH holder) {
        childModel.detachedFromWindow(holder.getChildViewHolder());
    }

    @NonNull
    @Override
    public abstract CementAdapter.WrapperViewHolderCreator<VH, MVH> getViewHolderCreator();

    @Override
    public boolean isContentTheSame(@NonNull CementModel<?> item) {
        return super.isContentTheSame(item)
                && childModel.isContentTheSame(((CementWrapperModel) item).childModel);
    }
}