package info.xudshen.android.playground.recyclerview.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * @author xudong
 * @since 2017/2/6
 */

public abstract class JUniversalAdapter extends RecyclerView.Adapter<JUniversalAdapter.ViewHolder> {
    private final List<AbstractModel<?>> models = new ArrayList<>();

    @Nullable
    public AbstractModel<?> getModel(int position) {
        return position >= 0 && position < models.size() ? models.get(position) : null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position, List<Object> payloads) {
        final AbstractModel model = getModel(position);
        if (holder == null || model == null) return;

        holder.checkModelViewHolder(model);
        if (payloads != null && !payloads.isEmpty()) {
            // noinspection unchecked
            model.bindData(holder.bindableHolder != null ? holder.bindableHolder : holder.itemView,
                    payloads);
        } else {
            // noinspection unchecked
            model.bindData(holder.bindableHolder != null ? holder.bindableHolder : holder.itemView
            );
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        AbstractModel model = getModel(position);
        return model == null ? NO_ID : model.getViewType();
    }

    public void add(AbstractModel<?> model) {
        models.add(model);
        notifyItemInserted(models.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AbstractBindableHolder bindableHolder;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        private void checkModelViewHolder(AbstractModel model) {
            if (bindableHolder == null && AbstractModelWithHolder.class.isInstance(model)) {
                bindableHolder = ((AbstractModelWithHolder) model).createBindableHolder(itemView);
                bindableHolder.bindView(itemView, this);
            }
        }

        public AbstractModel<?> getModelAtAdapterPosition() {
            return getModel(getAdapterPosition());
        }

        public AbstractModel<?> getModelAtLayoutPosition() {
            return getModel(getLayoutPosition());
        }
    }

}
