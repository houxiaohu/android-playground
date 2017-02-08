package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * @author xudong
 * @since 2017/2/6
 */

public class UUniversalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = UUniversalAdapter.class.getSimpleName();
    private final ModelList models = new ModelList();
    private boolean isAttached = false;

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

    private EventHookHelper eventHookHelper = new EventHookHelper(this);

    public UUniversalAdapter() {
        setHasStableIds(true);
        spanSizeLookup.setSpanIndexCacheEnabled(true);
    }

    //<editor-fold desc="CRUD Method">
    @Nullable
    public AbstractModel<?> getModel(int position) {
        return position >= 0 && position < models.size() ? models.get(position) : null;
    }

    protected List<AbstractModel<?>> getAllModelSubListAfter(AbstractModel<?> model) {
        int index = models.indexOf(model);
        if (index == -1) return Collections.emptyList();
        return models.subList(index + 1, models.size());
    }

    public List<AbstractModel<?>> getAllModelListAfter(AbstractModel<?> model) {
        int index = models.indexOf(model);
        if (index == -1) return Collections.emptyList();

        return new ArrayList<>(models.subList(index + 1, models.size()));
    }

    public void addModel(AbstractModel<?> modelToAdd) {
        final int initialSize = models.size();

        models.add(modelToAdd);
        notifyItemInserted(initialSize);
    }

    public void addModels(AbstractModel<?>... modelsToAdd) {
        addModels(Arrays.asList(modelsToAdd));
    }

    public void addModels(Collection<? extends AbstractModel<?>> modelsToAdd) {
        final int initialSize = models.size();

        models.addAll(modelsToAdd);
        notifyItemRangeInserted(initialSize, modelsToAdd.size());
    }

    public void insertModelBefore(AbstractModel<?> modelToInsert, AbstractModel<?> modelToInsertBefore) {
        int targetIndex = models.indexOf(modelToInsertBefore);
        if (targetIndex == -1) return;

        models.add(targetIndex, modelToInsert);
        notifyItemInserted(targetIndex);
    }

    public void insertModelAfter(AbstractModel<?> modelToInsert, AbstractModel<?> modelToInsertAfter) {
        int modelIndex = models.indexOf(modelToInsertAfter);
        if (modelIndex == -1) return;

        int targetIndex = modelIndex + 1;

        models.add(targetIndex, modelToInsert);
        notifyItemInserted(targetIndex);
    }

    public void notifyModelChanged(AbstractModel<?> model) {
        notifyModelChanged(model, null);
    }

    public void notifyModelChanged(AbstractModel<?> model, @Nullable Object payload) {
        int index = models.indexOf(model);
        if (index != -1) {
            notifyItemChanged(index, payload);
        }
    }

    public void removeModel(AbstractModel<?> modelToRemove) {
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

    public void removeAllAfterModel(AbstractModel<?> model) {
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
                    AbstractModel<?> oldModel = CollectionsHelper.getOrNull(models, oldItemPosition),
                            newModel = CollectionsHelper.getOrNull(modelsToReplace, newItemPosition);
                    return oldModel != null && newModel != null && oldModel.isItemTheSame(newModel);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    AbstractModel<?> oldModel = CollectionsHelper.getOrNull(models, oldItemPosition),
                            newModel = CollectionsHelper.getOrNull(modelsToReplace, newItemPosition);
                    return oldModel != null && newModel != null && oldModel.isContentTheSame(newModel);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = models.viewHolderFactory.create(viewType,
                LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));

        eventHookHelper.bind(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        final AbstractModel model = getModel(position);
        if (holder == null || model == null) return;

        if (payloads != null && !payloads.isEmpty()) {
            // noinspection unchecked
            model.bindData(holder, payloads);
        } else {
            // noinspection unchecked
            model.bindData(holder);
        }
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
    //</editor-fold>

    //<editor-fold desc="Model">
    private static class ViewHolderFactory {
        private SparseArray<IViewHolderCreator<?>> creatorSparseArray = new SparseArray<>();

        void register(AbstractModel<?> model) {
            if (creatorSparseArray.get(model.getLayoutRes()) == null) {
                creatorSparseArray.put(model.getLayoutRes(), model.getViewHolderCreator());
            }
        }

        void register(Collection<? extends AbstractModel<?>> models) {
            for (final AbstractModel model : models) {
                if (creatorSparseArray.get(model.getLayoutRes()) == null) {
                    creatorSparseArray.put(model.getLayoutRes(), model.getViewHolderCreator());
                }
            }
        }

        RecyclerView.ViewHolder create(@LayoutRes int viewType, @NonNull View view) {
            IViewHolderCreator<?> viewHolderCreator = creatorSparseArray.get(viewType);
            if (viewHolderCreator == null) {
                throw new RuntimeException("cannot find viewHolderCreator for viewType=" + viewType);
            }
            return viewHolderCreator.create(view);
        }
    }

    private static class ModelList extends ArrayList<AbstractModel<?>> {
        private ViewHolderFactory viewHolderFactory = new ViewHolderFactory();

        @Override
        public boolean add(AbstractModel<?> model) {
            viewHolderFactory.register(model);
            return super.add(model);
        }

        @Override
        public void add(int index, AbstractModel<?> element) {
            viewHolderFactory.register(element);
            super.add(index, element);
        }

        @Override
        public boolean addAll(Collection<? extends AbstractModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends AbstractModel<?>> c) {
            viewHolderFactory.register(c);
            return super.addAll(index, c);
        }
    }

    /**
     * every model should have its own identity, defaultValue {@link #id}
     * otherwise may cause the {@link DiffUtil#calculateDiff(DiffUtil.Callback)} failed,
     * even "Inconsistency detected" exception
     */
    public static abstract class AbstractModel<T extends RecyclerView.ViewHolder>
            implements IDiffUtilHelper<AbstractModel<?>> {
        private static long idCounter = -1;
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

        @LayoutRes
        public abstract int getLayoutRes();

        public void bindData(@NonNull T holder) {
        }

        public void bindData(@NonNull T holder, @Nullable List<Object> payloads) {
            bindData(holder);
        }

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

    public interface IViewHolderCreator<VH extends RecyclerView.ViewHolder> {
        VH create(@NonNull View view);
    }
    //</editor-fold>

    //<editor-fold desc="Event Hook">

    /**
     * MUST be called before the ViewHolder is created
     */
    public <VH extends RecyclerView.ViewHolder> void addEventHook(EventHook<VH> eventHook) {
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
    private EventHook<RecyclerView.ViewHolder> onItemClickEventHook;

    private void addOnItemClickEventHook() {
        onItemClickEventHook = new OnClickEventHook<RecyclerView.ViewHolder>(
                RecyclerView.ViewHolder.class) {
            @Override
            public void onClick(@NonNull View view, @NonNull RecyclerView.ViewHolder viewHolder,
                                int position, @NonNull AbstractModel rawModel) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, viewHolder, position, rawModel);
                }
            }

            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
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
        void onClick(@NonNull View itemView, @NonNull RecyclerView.ViewHolder viewHolder,
                     int position, @NonNull AbstractModel<?> model);
    }
    //</editor-fold>

    //<editor-fold desc="OnLongClickListener">
    @Nullable
    private OnItemLongClickListener onItemLongClickListener;
    @Nullable
    private EventHook<RecyclerView.ViewHolder> onItemLongClickEventHook;

    private void addOnItemLongClickEventHook() {
        onItemLongClickEventHook = new OnLongClickEventHook<
                RecyclerView.ViewHolder>(RecyclerView.ViewHolder.class) {
            @Override
            public boolean onLongClick(@NonNull View view, @NonNull RecyclerView.ViewHolder
                    viewHolder, int position, @NonNull AbstractModel rawModel) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onLongClick(view, viewHolder, position, rawModel);
                }
                return false;
            }

            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                return viewHolder.itemView;
            }
        };
        addEventHook(onItemLongClickEventHook);
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener onItemLongClickListener) {
        if (isAttached && onItemLongClickEventHook == null && onItemLongClickListener != null) {
            throw new IllegalStateException("setOnItemLongClickListener " +
                    "must be called before the RecyclerView#setAdapter");
        } else if (!isAttached && onItemLongClickEventHook == null) {
            addOnItemLongClickEventHook();
        }
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onLongClick(@NonNull View itemView, @NonNull RecyclerView.ViewHolder viewHolder,
                            int position, @NonNull AbstractModel<?> model);
    }
    //</editor-fold>
}
