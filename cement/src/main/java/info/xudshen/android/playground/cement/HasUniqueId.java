package info.xudshen.android.playground.cement;

public interface HasUniqueId<T> {
    long uniqueId();

    Class<T> getClazz();
}
