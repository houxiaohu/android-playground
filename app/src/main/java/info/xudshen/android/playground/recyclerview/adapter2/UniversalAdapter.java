package info.xudshen.android.playground.recyclerview.adapter2;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import info.xudshen.android.playground.recyclerview.adapter2.eventhook.EventHook;
import info.xudshen.android.playground.recyclerview.adapter2.eventhook.EventHookHelper;
import info.xudshen.android.playground.recyclerview.adapter2.eventhook.OnClickEventHook;
import info.xudshen.android.playground.recyclerview.adapter2.eventhook.OnLongClickEventHook;

import static android.view.View.NO_ID;

/**
 * @author xudong
 * @since 2017/2/6
 */

public class UniversalAdapter extends RecyclerView.Adapter<UniversalAdapter.ViewHolder> {
    private static final String LOG_TAG = UniversalAdapter.class.getSimpleName();
    private static final String SAVED_STATE_ARG_VIEW_HOLDERS = "saved_state_view_holders";

    private final ModelList models = new ModelList();

    private final EventHookHelper eventHookHelper = new EventHookHelper(this);
    private boolean isAttached = false;

    private final LongSparseArray<ViewHolder> boundViewHolders = new LongSparseArray<>();
    private ViewHolderState viewHolderState = new ViewHolderState();

    //<editor-fold desc="GridLayout support">
    private final GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager
            .SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            AbstractModel<?> model = getModel(position);
            return model != null ? model.getSpanSize(spanCount, position, getItemCount()) : 1;
        }
    };

    private int spanCount = 1;

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }
    //</editor-fold>

    public UniversalAdapter() {
        setHasStableIds(true);
        spanSizeLookup.setSpanIndexCacheEnabled(true);
    }

    //<editor-fold desc="CRUD Method">
    @Nullable
    public AbstractModel<?> getModel(int position) {
        return position >= 0 && position < models.size() ? models.get(position) : null;
    }

    protected boolean containsModel(AbstractModel<?> model) {
        return models.indexOf(model) >= 0;
    }

    @NonNull
    protected List<AbstractModel<?>> getAllModelSubListAfter(@Nullable AbstractModel<?> model) {
        int index = models.indexOf(model);
        if (index == -1) return Collections.emptyList();
        return models.subList(index + 1, models.size());
    }

    @NonNull
    public List<AbstractModel<?>> getAllModelListAfter(@Nullable AbstractModel<?> model) {
        int index = models.indexOf(model);
        if (index == -1) return Collections.emptyList();

        return new ArrayList<>(models.subList(index + 1, models.size()));
    }

    @NonNull
    public List<AbstractModel<?>> getAllModelListBetween(@Nullable AbstractModel<?> start,
                                                         @Nullable AbstractModel<?> end) {
        int startIdx = models.indexOf(start),
                endIdx = models.indexOf(end);
        startIdx = startIdx == -1 ? 0 : startIdx + 1;
        endIdx = endIdx == -1 ? models.size() : endIdx;
        if (startIdx > endIdx) return Collections.emptyList();

        return new ArrayList<>(models.subList(startIdx, endIdx));
    }

    public void addModel(@NonNull AbstractModel<?> modelToAdd) {
        final int initialSize = models.size();

        models.add(modelToAdd);
        notifyItemInserted(initialSize);
    }

    public void addModel(int index, @NonNull AbstractModel<?> modelToAdd) {
        if (index > models.size() || index < 0) return;

        models.add(index, modelToAdd);
        notifyItemInserted(index);
    }

    public void addModels(@NonNull AbstractModel<?>... modelsToAdd) {
        addModels(Arrays.asList(modelsToAdd));
    }

    public void addModels(@NonNull Collection<? extends AbstractModel<?>> modelsToAdd) {
        final int initialSize = models.size();

        models.addAll(modelsToAdd);
        notifyItemRangeInserted(initialSize, modelsToAdd.size());
    }

    public void insertModelBefore(@NonNull AbstractModel<?> modelToInsert,
                                  @Nullable AbstractModel<?> modelToInsertBefore) {
        int targetIndex = models.indexOf(modelToInsertBefore);
        if (targetIndex == -1) return;

        models.add(targetIndex, modelToInsert);
        notifyItemInserted(targetIndex);
    }

    public void insertModelsBefore(@NonNull Collection<? extends AbstractModel<?>> modelsToInsert,
                                   @Nullable AbstractModel<?> modelToInsertBefore) {
        int targetIndex = models.indexOf(modelToInsertBefore);
        if (targetIndex == -1) return;

        models.addAll(targetIndex, modelsToInsert);
        notifyItemRangeInserted(targetIndex, modelsToInsert.size());
    }

    public void insertModelAfter(@NonNull AbstractModel<?> modelToInsert,
                                 @Nullable AbstractModel<?> modelToInsertAfter) {
        int modelIndex = models.indexOf(modelToInsertAfter);
        if (modelIndex == -1) return;

        int targetIndex = modelIndex + 1;

        models.add(targetIndex, modelToInsert);
        notifyItemInserted(targetIndex);
    }

    public void insertModelsAfter(@NonNull Collection<? extends AbstractModel<?>> modelsToInsert,
                                  @Nullable AbstractModel<?> modelToInsertAfter) {
        int modelIndex = models.indexOf(modelToInsertAfter);
        if (modelIndex == -1) return;

        int targetIndex = modelIndex + 1;

        models.addAll(targetIndex, modelsToInsert);
        notifyItemRangeInserted(targetIndex, modelsToInsert.size());
    }

    public void notifyModelChanged(@NonNull AbstractModel<?> model) {
        notifyModelChanged(model, null);
    }

    public void notifyModelChanged(@NonNull AbstractModel<?> model, @Nullable Object payload) {
        int index = models.indexOf(model);
        if (index != -1) {
            notifyItemChanged(index, payload);
        }
    }

    public void removeModel(@Nullable AbstractModel<?> modelToRemove) {
        int index = models.indexOf(modelToRemove);
        if (index >= 0 && index < models.size()) {
            models.remove(index);

            notifyItemRemoved(index);
        }
    }

    public void removeAllModels() {
        final int initialSize = models.size();

        models.clear();
        notifyItemRangeRemoved(0, initialSize);
    }

    public void removeAllAfterModel(@Nullable AbstractModel<?> model) {
        final int initialSize = models.size();

        List<AbstractModel<?>> modelsToRemove = getAllModelSubListAfter(model);
        int numModelsRemoved = modelsToRemove.size();
        if (numModelsRemoved == 0) return;

        //clear the sublist
        modelsToRemove.clear();
        notifyItemRangeRemoved(initialSize - numModelsRemoved, numModelsRemoved);
    }

    public void replaceAllModels(@NonNull final List<? extends AbstractModel<?>> modelsToReplace) {
        if (models.size() == 0) {
            addModels(modelsToReplace);
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return models.size();
                }

                @Override
                public int getNewListSize() {
                    return modelsToReplace.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    AbstractModel<?> oldModel = CollectionsHelper.getOrNull(models,
                            oldItemPosition),
                            newModel = CollectionsHelper.getOrNull(modelsToReplace,
                                    newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.isItemTheSame(newModel);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    AbstractModel<?> oldModel = CollectionsHelper.getOrNull(models,
                            oldItemPosition),
                            newModel = CollectionsHelper.getOrNull(modelsToReplace,
                                    newItemPosition);
                    return oldModel != null && newModel != null
                            && oldModel.isContentTheSame(newModel);
                }
            });
            models.clear();
            models.addAll(modelsToReplace);
            result.dispatchUpdatesTo(this);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Core">
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = models.viewHolderFactory.create(viewType,
                LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));

        eventHookHelper.bind(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@Nullable ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(@Nullable ViewHolder holder, int position,
                                 @Nullable List<Object> payloads) {
        final AbstractModel model = getModel(position);
        if (holder == null || model == null) return;

        // A ViewHolder can be bound again even it is already bound and showing, like when it is on
        // screen and is changed. In this case we need
        // to carry the state of the previous view over to the new view. This may not be necessary if
        // the viewholder is reused (see RecyclerView.ItemAnimator#canReuseUpdatedViewHolder)
        // but we don't rely on that to be safe and to simplify
        // ??????????
        if (boundViewHolders.get(holder.getItemId()) != null) {
            viewHolderState.save(boundViewHolders.get(holder.getItemId()));
        }

        holder.bind(model, payloads);

        viewHolderState.restore(holder);
        boundViewHolders.put(holder.getItemId(), holder);
    }

    @Override
    public void onViewRecycled(@Nullable ViewHolder holder) {
        if (holder == null) return;

        viewHolderState.save(holder);
        boundViewHolders.remove(holder.getItemId());

        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        AbstractModel model = getModel(position);
        return model == null ? NO_ID : model.getLayoutRes();
    }

    @Override
    public long getItemId(int position) {
        AbstractModel model = getModel(position);
        return model == null ? NO_ID : model.id();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        isAttached = true;
    }

    public void onSaveInstanceState(Bundle outState) {
        for (int i = 0; i < boundViewHolders.size(); i++) {
            long key = boundViewHolders.keyAt(i);
            viewHolderState.save(boundViewHolders.get(key));
        }

        if (viewHolderState.size() > 0 && !hasStableIds()) {
            throw new IllegalStateException("Must have stable ids when saving view holder state");
        }

        outState.putParcelable(SAVED_STATE_ARG_VIEW_HOLDERS, viewHolderState);
    }

    public void onRestoreInstanceState(@Nullable Bundle inState) {
        // To simplify things we enforce that state is restored before views are bound, otherwise it
        // is more difficult to update view state once they are bound
        if (boundViewHolders.size() > 0) {
            throw new IllegalStateException(
                    "State cannot be restored once views have been bound. It should be done before adding "
                            + "the adapter to the recycler view.");
        }

        if (inState != null) {
            ViewHolderState savedState = inState.getParcelable(SAVED_STATE_ARG_VIEW_HOLDERS);
            if (savedState != null) {
                viewHolderState = savedState;
            } else {
                Log.w(LOG_TAG, "can not get save viewholder state");
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="Model">
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private AbstractModel model;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        private void bind(@NonNull AbstractModel model, @Nullable List<Object> payloads) {
            if (payloads != null && !payloads.isEmpty()) {
                // noinspection unchecked
                model.bindData(this, payloads);
            } else {
                // noinspection unchecked
                model.bindData(this);
            }

            this.model = model;
        }

        private void unbind() {
            if (model == null) return;
            // noinspection unchecked
            model.unbind(this);
            model = null;
        }

        boolean shouldSaveViewState() {
            return model != null && model.shouldSaveViewState();
        }
    }

    private static class ViewHolderFactory {
        private final SparseArray<IViewHolderCreator<?>> creatorSparseArray = new SparseArray<>();

        void register(@NonNull AbstractModel<?> model) {
            if (creatorSparseArray.get(model.getLayoutRes()) == null) {
                creatorSparseArray.put(model.getLayoutRes(), model.getViewHolderCreator());
            }
        }

        void register(@NonNull Collection<? extends AbstractModel<?>> models) {
            for (final AbstractModel model : models) {
                if (model == null) continue;
                if (creatorSparseArray.get(model.getLayoutRes()) == null) {
                    creatorSparseArray.put(model.getLayoutRes(), model.getViewHolderCreator());
                }
            }
        }

        ViewHolder create(@LayoutRes int viewType, @NonNull View view) {
            IViewHolderCreator<?> viewHolderCreator = creatorSparseArray.get(viewType);
            if (viewHolderCreator == null) {
                throw new RuntimeException("cannot find viewHolderCreator for viewType="
                        + viewType);
            }
            return viewHolderCreator.create(view);
        }
    }

    private static class ModelList extends ArrayList<AbstractModel<?>> {
        private final ViewHolderFactory viewHolderFactory = new ViewHolderFactory();

        @Override
        public boolean add(@NonNull AbstractModel<?> model) {
            viewHolderFactory.register(model);
            return super.add(model);
        }

        @Override
        public void add(int index, @NonNull AbstractModel<?> element) {
            viewHolderFactory.register(element);
            super.add(index, element);
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends AbstractModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends AbstractModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(index, c);
        }
    }

    /**
     * every model should have its own identity, defaultValue {@link #id}
     * otherwise may cause the {@link DiffUtil#calculateDiff(DiffUtil.Callback)} failed,
     * even "Inconsistency detected" exception
     */
    public static abstract class AbstractModel<T extends ViewHolder>
            implements IDiffUtilHelper<AbstractModel<?>> {
        private static long idCounter = NO_ID - 1;
        private long id;

        protected AbstractModel(long id) {
            this.id = id;
        }

        public AbstractModel() {
            this(idCounter--);
        }

        public long id() {
            return id;
        }

        public int getSpanSize(int totalSpanCount, int position, int itemCount) {
            return 1;
        }

        public boolean shouldSaveViewState() {
            return false;
        }

        @LayoutRes
        public abstract int getLayoutRes();

        public void bindData(@NonNull T holder) {
        }

        public void bindData(@NonNull T holder, @Nullable List<Object> payloads) {
            bindData(holder);
        }

        public void unbind(@NonNull T holder) {
        }

        @NonNull
        public abstract IViewHolderCreator<T> getViewHolderCreator();

        @Override
        public boolean isItemTheSame(@NonNull AbstractModel<?> item) {
            return id == item.id;
        }

        @Override
        public boolean isContentTheSame(@NonNull AbstractModel<?> item) {
            return true;
        }
    }

    public interface IViewHolderCreator<VH extends ViewHolder> {
        @NonNull
        VH create(@NonNull View view);
    }
    //</editor-fold>

    //<editor-fold desc="Event Hook">

    /**
     * MUST be called before the ViewHolder is created
     */
    public <VH extends ViewHolder> void addEventHook(
            @NonNull EventHook<VH> eventHook) {
        if (isAttached) {
            Log.w(LOG_TAG, "addEventHook is called after adapter attached");
        }
        // noinspection unchecked
        eventHookHelper.add(eventHook);
    }
    //</editor-fold>

    //<editor-fold desc="OnClickListener">
    @Nullable
    private OnItemClickListener onItemClickListener;
    @Nullable
    private EventHook<ViewHolder> onItemClickEventHook;

    private void addOnItemClickEventHook() {
        onItemClickEventHook = new OnClickEventHook<ViewHolder>(
                ViewHolder.class) {
            @Override
            public void onClick(@NonNull View view, @NonNull ViewHolder viewHolder,
                                int position, @NonNull AbstractModel rawModel) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, viewHolder, position, rawModel);
                }
            }

            @Nullable
            @Override
            public View onBind(@NonNull ViewHolder viewHolder) {
                return viewHolder.itemView;
            }
        };
        addEventHook(onItemClickEventHook);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        if (isAttached && onItemClickEventHook == null && onItemClickListener != null) {
            throw new IllegalStateException("setOnItemClickListener " +
                    "must be called before the RecyclerView#setAdapter");
        } else if (!isAttached && onItemClickEventHook == null) {
            addOnItemClickEventHook();
        }
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(@NonNull View itemView, @NonNull ViewHolder viewHolder,
                     int position, @NonNull AbstractModel<?> model);
    }
    //</editor-fold>

    //<editor-fold desc="OnLongClickListener">
    @Nullable
    private OnItemLongClickListener onItemLongClickListener;
    @Nullable
    private EventHook<ViewHolder> onItemLongClickEventHook;

    private void addOnItemLongClickEventHook() {
        onItemLongClickEventHook = new OnLongClickEventHook<ViewHolder>(ViewHolder.class) {
            @Override
            public boolean onLongClick(@NonNull View view, @NonNull ViewHolder viewHolder,
                                       int position, @NonNull AbstractModel rawModel) {
                return onItemLongClickListener != null && onItemLongClickListener.onLongClick(
                        view, viewHolder, position, rawModel);
            }

            @Nullable
            @Override
            public View onBind(@NonNull ViewHolder viewHolder) {
                return viewHolder.itemView;
            }
        };
        addEventHook(onItemLongClickEventHook);
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener
                                                   onItemLongClickListener) {
        if (isAttached && onItemLongClickEventHook == null && onItemLongClickListener != null) {
            throw new IllegalStateException("setOnItemLongClickListener " +
                    "must be called before the RecyclerView#setAdapter");
        } else if (!isAttached && onItemLongClickEventHook == null) {
            addOnItemLongClickEventHook();
        }
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(@NonNull View itemView, @NonNull ViewHolder viewHolder,
                            int position, @NonNull AbstractModel<?> model);
    }
    //</editor-fold>
}
