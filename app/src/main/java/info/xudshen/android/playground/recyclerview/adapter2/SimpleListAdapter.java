package info.xudshen.android.playground.recyclerview.adapter2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author xudong
 * @since 2017/2/9
 */

public class SimpleListAdapter extends UUniversalAdapter {
    private OrderedMap<Long, AbstractModel<?>> headers = new OrderedMap<>(),
            footers = new OrderedMap<>();

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

    public Collection<? extends AbstractModel<?>> getDataModels() {
        return getAllModelListBetween(headers.getLast(), footers.getFirst());
    }

    public <M extends AbstractModel> void addDataModel(@NonNull M model) {
        if (footers.size() == 0) {
            addModel(model);
        } else {
            insertModelBefore(model, footers.getFirst());
        }
    }

    public void addDataModels(@NonNull AbstractModel<?>... modelsToAdd) {
        addDataModels(Arrays.asList(modelsToAdd));
    }

    public void addDataModels(@NonNull Collection<? extends AbstractModel<?>> modelsToAdd) {
        if (footers.size() == 0) {
            addModels(modelsToAdd);
        } else {
            insertModelsBefore(modelsToAdd, footers.getFirst());
        }
    }

    public void updateDataModels(@NonNull Collection<? extends AbstractModel<?>> newDataModels) {
        List<AbstractModel<?>> newModels = new ArrayList<>();
        newModels.addAll(headers.values());
        newModels.addAll(newDataModels);
        newModels.addAll(footers.values());

        replaceAllModels(newModels);
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
