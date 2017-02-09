package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import info.xudshen.android.playground.R;

/**
 * @author xudong
 * @since 2017/2/9
 */

public class SimpleListAdapter extends UUniversalAdapter {
    private final OrderedMap<Long, AbstractModel<?>> headers = new OrderedMap<>(),
            footers = new OrderedMap<>();

    private boolean hasMore = false;
    @NonNull
    private AbstractLoadMoreModel<?> loadMoreModel = new LoadMoreModel();

    @Nullable
    private AbstractModel<?> getLoadMoreOrFirstFooter() {
        return hasMore ? loadMoreModel : footers.getFirst();
    }

    //<editor-fold desc="Headers">
    public Collection<? extends AbstractModel<?>> getHeaders() {
        return headers.values();
    }

    public <M extends AbstractModel> boolean addHeader(@NonNull M model) {
        if (headers.get(model.id()) != null) {
            return false;
        }

        addModel(headers.size(), model);
        headers.put(model.id(), model);
        return true;
    }

    public <M extends AbstractModel> boolean removeHeader(@NonNull M model) {
        if (headers.get(model.id()) == null) {
            return false;
        }

        removeModel(model);
        headers.remove(model.id());
        return true;
    }
    //</editor-fold>

    //<editor-fold desc="Footers">
    public Collection<? extends AbstractModel<?>> getFooters() {
        return footers.values();
    }

    public <M extends AbstractModel> boolean addFooter(@NonNull M model) {
        if (footers.get(model.id()) != null) {
            return false;
        }

        addModel(getItemCount(), model);
        footers.put(model.id(), model);
        return true;
    }

    public <M extends AbstractModel> boolean removeFooter(@NonNull M model) {
        if (footers.get(model.id()) == null) {
            return false;
        }

        removeModel(model);
        footers.remove(model.id());
        return true;
    }
    //</editor-fold>

    //<editor-fold desc="Data Models">
    public Collection<? extends AbstractModel<?>> getDataModels() {
        return getAllModelListBetween(headers.getLast(), getLoadMoreOrFirstFooter());
    }

    public <M extends AbstractModel> void addDataModel(@NonNull M model) {
        AbstractModel lastModel = getLoadMoreOrFirstFooter();
        if (lastModel == null) {
            addModel(model);
        } else {
            insertModelBefore(model, lastModel);
        }
    }

    public void addDataModels(@NonNull AbstractModel<?>... modelsToAdd) {
        addDataModels(Arrays.asList(modelsToAdd), hasMore);
    }

    public void addDataModels(@NonNull Collection<? extends AbstractModel<?>> modelsToAdd) {
        addDataModels(modelsToAdd, hasMore);
    }

    public void addDataModels(@NonNull Collection<? extends AbstractModel<?>> modelsToAdd,
                              boolean hasMore) {
        setHasMore(hasMore);
        AbstractModel lastModel = getLoadMoreOrFirstFooter();
        if (lastModel == null) {
            addModels(modelsToAdd);
        } else {
            insertModelsBefore(modelsToAdd, lastModel);
        }
    }

    public void updateDataModels(@NonNull Collection<? extends AbstractModel<?>> newDataModels) {
        updateDataModels(newDataModels, hasMore);
    }

    public void updateDataModels(@NonNull Collection<? extends AbstractModel<?>> newDataModels,
                                 boolean hasMore) {
        List<AbstractModel<?>> newModels = new ArrayList<>();
        newModels.addAll(headers.values());
        newModels.addAll(newDataModels);
        if (hasMore) newModels.add(loadMoreModel);
        newModels.addAll(footers.values());

        replaceAllModels(newModels);
    }
    //</editor-fold>

    //<editor-fold desc="Load More">
    public final void setHasMore(boolean hasMore) {
        if (this.hasMore == hasMore) return;

        this.hasMore = hasMore;
        if (hasMore) {
            //show
            if (footers.size() == 0) {
                addModels(loadMoreModel);
            } else {
                insertModelBefore(loadMoreModel, footers.getFirst());
            }
        } else {
            //hide
            loadMoreModel.setState(AbstractLoadMoreModel.COMPLETE);
            removeModel(loadMoreModel);
        }
    }

    public final void setLoadMoreModel(@NonNull AbstractLoadMoreModel<?> loadMoreModel) {
        this.loadMoreModel = loadMoreModel;
    }

    public final void setLoadMoreState(@AbstractLoadMoreModel.LoadMoreState int state) {
        if (!hasMore) return;

        loadMoreModel.setState(state);
        notifyModelChanged(loadMoreModel);
    }
    //</editor-fold>

    private static class LoadMoreModel extends AbstractLoadMoreModel<LoadMoreModel.ViewHolder> {
        @Override
        public int getLayoutRes() {
            return R.layout.layout_load_more_item;
        }

        @Override
        public void onLoadMoreStart(@NonNull ViewHolder holder) {
            holder.title.setText("loading...");
        }

        @Override
        public void onLoadMoreComplete(@NonNull ViewHolder holder) {
            holder.title.setText("click to load");
        }

        @Override
        public void onLoadMoreFailed(@NonNull ViewHolder holder) {
            holder.title.setText("click to retry");
        }

        @NonNull
        @Override
        public IViewHolderCreator<ViewHolder> getViewHolderCreator() {
            return new IViewHolderCreator<ViewHolder>() {
                @NonNull
                @Override
                public ViewHolder create(@NonNull View view) {
                    return new ViewHolder(view);
                }
            };
        }

        public class ViewHolder extends UUniversalAdapter.ViewHolder {
            private TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                title = ((TextView) itemView.findViewById(R.id.section_title));
            }
        }
    }

    private class OrderedMap<K, V> implements Iterable<V> {
        private HashMap<K, V> map = new HashMap<>();
        private List<K> orderList = new ArrayList<>();

        boolean checkExistAndConsistency(@Nullable K key) {
            boolean inMap = map.containsKey(key),
                    inOrder = orderList.contains(key);
            if (inMap ^ inOrder) {
                throw new IllegalStateException("inconsistent key=" + key);
            }
            return inMap & inOrder;
        }

        @Nullable
        public synchronized V get(@NonNull K key) {
            return checkExistAndConsistency(key) ? map.get(key) : null;
        }

        @Nullable
        public synchronized V put(@NonNull K key, @NonNull V value) {
            if (!checkExistAndConsistency(key)) {
                map.put(key, value);
                orderList.add(key);
            }
            return null;
        }

        @Nullable
        public synchronized V remove(@NonNull K key) {
            if (checkExistAndConsistency(key)) {
                map.remove(key);
                orderList.remove(key);
            }
            return null;
        }

        public int size() {
            return orderList.size();
        }

        public Collection<V> values() {
            Collection<V> values = new ArrayList<>();
            for (K key : orderList) {
                values.add(map.get(key));
            }
            return values;
        }

        public V getFirst() {
            return orderList.size() == 0 ? null : map.get(orderList.get(0));
        }

        public V getLast() {
            return orderList.size() == 0 ? null : map.get(orderList.get(orderList.size() - 1));
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {
                private int index = 0;

                @Override
                public boolean hasNext() {
                    return index < orderList.size();
                }

                @Override
                public V next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return map.get(orderList.get(index++));
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
}
