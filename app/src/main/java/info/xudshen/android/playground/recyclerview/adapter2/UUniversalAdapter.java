package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;
import static android.view.View.NO_ID;

/**
 * @author xudong
 * @since 2017/2/6
 */

public class UUniversalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<AbstractModel<?>> models = new ArrayList<>();
    private SparseArray<IViewHolderCreator<?>> creatorSparseArray = new SparseArray<>();
    private EventHookHelper eventHookHelper = new EventHookHelper(this);

    public UUniversalAdapter() {
        eventHookHelper.add(onItemClickEventHook);
    }

    //<editor-fold desc="CURD Method">
    @Nullable
    public AbstractModel<?> getModel(int position) {
        return position >= 0 && position < models.size() ? models.get(position) : null;
    }

    public void add(AbstractModel<?> model) {
        models.add(model);
        if (creatorSparseArray.get(model.getLayoutRes()) == null) {
            creatorSparseArray.put(model.getLayoutRes(), model.getViewHolderCreator());
        }
        notifyItemInserted(models.size());
    }
    //</editor-fold>

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IViewHolderCreator viewHolderCreator = creatorSparseArray.get(viewType);
        if (viewHolderCreator == null) {
            throw new RuntimeException("cannot find viewHolderCreator for viewType=" + viewType);
        }

        RecyclerView.ViewHolder viewHolder = viewHolderCreator.create(LayoutInflater.
                from(parent.getContext()).inflate(viewType, parent, false));

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

    //<editor-fold desc="Model">
    public static abstract class AbstractModel<T extends RecyclerView.ViewHolder> {
        @LayoutRes
        public abstract int getLayoutRes();

        public void bindData(T holder) {
        }

        public void bindData(T holder, List<Object> payloads) {
            bindData(holder);
        }

        public abstract IViewHolderCreator<T> getViewHolderCreator();
    }

    public interface IViewHolderCreator<T extends RecyclerView.ViewHolder> {
        T create(View view);
    }
    //</editor-fold>

    //<editor-fold desc="OnClickListener">
    private OnItemClickListener onItemClickListener;
    private EventHook<RecyclerView.ViewHolder> onItemClickEventHook = new EventHook<
            RecyclerView.ViewHolder>() {
        @Override
        public void onEvent(@NonNull View view, @NonNull final RecyclerView.ViewHolder viewHolder,
                            @NonNull final UUniversalAdapter adapter) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        if (position != NO_POSITION) {
                            onItemClickListener.onClick(v, position, adapter.getModel(position));
                        }
                    }
                }
            });
        }

        @Nullable
        @Override
        public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
            return viewHolder.itemView;
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View itemView, int position, AbstractModel<?> model);
    }
    //</editor-fold>
}
