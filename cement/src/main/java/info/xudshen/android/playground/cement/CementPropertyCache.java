package info.xudshen.android.playground.cement;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CementPropertyCache<T> {
    @IntDef({TYPE_NORMAL, TYPE_ID})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ID = 1;

    @Type
    private final int type;

    @Nullable
    private T preVal;
    @Nullable
    private T curVal;

    private boolean isChanged = false;

    public CementPropertyCache() {
        this(TYPE_NORMAL);
    }

    public CementPropertyCache(@Type int type) {
        this.type = type;
    }

    @Nullable
    public T get() {
        return curVal;
    }

    @Nullable
    public T getPre() {
        return preVal;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void set(@Nullable T val) {
        __set(val, false);
    }

    /**
     * (null, null) -> true
     * (A, null) -> false
     * (null, B) -> false
     * (A, B) -> A == B || A.id == B.id || A.equals(B)
     */
    private static <T> boolean idEquals(@Nullable T valA, @Nullable T valB) {
        if (valA == valB) return true;

        if (valA instanceof HasUniqueId && valB instanceof HasUniqueId) {
            return ((HasUniqueId) valA).uniqueId() == ((HasUniqueId) valB).uniqueId();
        } else {
            return valA != null && valA.equals(valB);
        }
    }

    public void __set(@Nullable T val, boolean stageChange) {
        if (curVal == val) return;

        if (type == TYPE_ID && curVal != null && !idEquals(curVal, val)) {
            throw new IllegalArgumentException("Cannot Update Property with Different Id, " +
                    "otherwise the Cement#id() will has unexpected behavior");
        }

        //Primitive Class, equal-> not changed;
        //Custom Class, equal-> not changed, even the two reference are the same;
        if (stageChange && (curVal == null || !curVal.equals(val))) {
            isChanged = true;
            preVal = curVal;
        } else {
            isChanged = false;
            preVal = null;
        }
        curVal = val;
    }

    public void __stage(@Nullable T val) {
        if (curVal == val) return;

        if (type == TYPE_ID) {
            if (curVal instanceof HasUniqueId && val instanceof HasUniqueId
                    && ((HasUniqueId) curVal).uniqueId() == ((HasUniqueId) val).uniqueId()
                    && !curVal.equals(val)) {
                isChanged = true;
                preVal = val;
            } else {
                Log.w("CementPropertyCache", "Ignore this property stage");
            }
        } else {
            if (curVal == null || !curVal.equals(val)) {
                isChanged = true;
                preVal = val;
            }
        }
    }

    public void __cleanUp() {
        if (isChanged) {
            isChanged = false;
            preVal = null;
        }
    }
}
