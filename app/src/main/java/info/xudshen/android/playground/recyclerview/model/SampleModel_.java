package info.xudshen.android.playground.recyclerview.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import info.xudshen.android.playground.cement.CementAdapter;
import info.xudshen.android.playground.cement.CementModel;
import info.xudshen.android.playground.cement.CementPropertyCache;
import info.xudshen.android.playground.cement.HasUniqueId;

public class SampleModel_ extends SampleModel {
    @NonNull
    private CementPropertyCache<String> title =
            new CementPropertyCache<>(CementPropertyCache.TYPE_ID);
    @NonNull
    private CementPropertyCache<Integer> loc =
            new CementPropertyCache<>(CementPropertyCache.TYPE_ID);
    @NonNull
    private CementPropertyCache<String> desc =
            new CementPropertyCache<>(CementPropertyCache.TYPE_NORMAL);
    @Nullable
    private String icon = null;
    @NonNull
    private CementPropertyCache<SomeObject> object =
            new CementPropertyCache<>(CementPropertyCache.TYPE_ID);

    private SampleModel_() {
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    //<editor-fold desc="Properties">
    @Override
    @NonNull
    protected CementPropertyCache<String> title() {
        return title;
    }

    @NonNull
    public String getTitle() {
        String val = title().get();
        if (val == null) {
            throw new NullPointerException("Property title is null, which should not happen");
        }
        return val;
    }

    public void setTitle(@NonNull String title) {
        title().__set(title, shouldStageProperty());
    }

    @NonNull
    @Override
    CementPropertyCache<Integer> loc() {
        return loc;
    }

    @NonNull
    public Integer getLoc() {
        Integer val = loc().get();
        if (val == null) {
            throw new NullPointerException("Property title is null, which should not happen");
        }
        return val;
    }

    public void setLoc(@NonNull Integer loc) {
        loc().__set(loc, shouldStageProperty());
    }

    @Override
    @NonNull
    protected CementPropertyCache<String> desc() {
        return desc;
    }

    @Nullable
    public String getDesc() {
        return desc().get();
    }

    public void setDesc(@NonNull String title) {
        desc().__set(title, shouldStageProperty());
    }

    @Override
    @Nullable
    protected String icon() {
        return icon;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    @Override
    protected CementPropertyCache<SomeObject> object() {
        return object;
    }

    @NonNull
    public SomeObject getObject() {
        SomeObject val = object().get();
        if (val == null) {
            throw new NullPointerException("Property object is null, which should not happen");
        }
        return val;
    }

    public void setObject(@NonNull SomeObject object) {
        object().__set(object, shouldStageProperty());
    }

    @Override
    public boolean isPropertiesChanged() {
        return title().isChanged()
                || desc().isChanged()
                || object().isChanged();
    }

    @Override
    public void doPropertiesCleanUp() {
        title().__cleanUp();
        desc().__cleanUp();
        object().__cleanUp();
    }

    @Override
    public void doPropertiesStage(@NonNull CementModel model) {
        if (SampleModel_.class.isInstance(model)) {
            SampleModel_ preModel = (SampleModel_) model;
            title().__stage(preModel.title.get());
            desc().__stage(preModel.desc.get());
            object().__stage(preModel.object.get());
        }
    }

    //</editor-fold>
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SampleModel_)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SampleModel_ that = (SampleModel_) o;
        if (!equals(title().get(), that.title().get())) {
            return false;
        }
        if (!equals(loc().get(), that.loc().get())) {
            return false;
        }
        if (!equals(icon, that.icon)) {
            return false;
        }
        if (!equals(object().get(), that.object().get())) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + hashCode(title().get());
        result = 31 * result + hashCode(loc().get());
        result = 31 * result + (icon != null ? 1 : 0);
        result = 31 * result + hashCode(object().get());
        return result;
    }

    @NonNull
    @Override
    public CementAdapter.IViewHolderCreator<ViewHoldXer> getViewHolderCreator() {
        return new CementAdapter.IViewHolderCreator<ViewHoldXer>() {
            @NonNull
            @Override
            public ViewHoldXer create(@NonNull View view) {
                return new ViewHoldXer(view);
            }
        };
    }

    public static class Builder {
        @Nullable
        private String title;
        @Nullable
        private Integer loc;
        @Nullable
        private String desc;
        @Nullable
        private String icon;
        @Nullable
        private SomeObject object;

        public Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder loc(Integer loc) {
            this.loc = loc;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder object(SomeObject object) {
            this.object = object;
            return this;
        }

        private long checkId() {
            if (title == null) {
                throw new IllegalArgumentException("PropertyId is not set");
            }
            if (loc == null) {
                throw new IllegalArgumentException("PropertyId is not set");
            }
            if (object == null) {
                throw new IllegalArgumentException("PropertyId is not set");
            }

            long result = 0;
            result = 31 * result + hashString64Bit(title);
            result = 31 * result + hashInt(loc);
            if (object instanceof HasUniqueId) {
                result = 31 * result + hashLong64Bit(((HasUniqueId) object).uniqueId());
            }
            return result;
        }

        public SampleModel_ build() {
            SampleModel_ model = new SampleModel_();
            model.id(checkId());
            model.title.set(title);
            model.loc.set(loc);
            model.desc.set(desc);
            model.icon = icon;
            model.object.set(object);
            model.setAfterModelBuild();
            return model;
        }
    }
}
