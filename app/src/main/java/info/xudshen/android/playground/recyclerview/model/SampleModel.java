package info.xudshen.android.playground.recyclerview.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import info.xudshen.android.playground.R;
import info.xudshen.android.playground.cement.*;

@CementModelClass(layout = R.layout.layout_empty_view_model)
public abstract class SampleModel extends CementModel<SampleModel.ViewHoldXer> {
    @CementProperty(id = true)
    @NonNull
    abstract CementPropertyCache<String> title();

    @CementProperty(id = true)
    @NonNull
    abstract CementPropertyCache<Integer> loc();

    @CementProperty(noHash = true)
    @NonNull
    abstract CementPropertyCache<String> desc();

    @CementProperty
    @Nullable
    //worked as read only
    abstract String icon();

    @CementProperty(id = true)
    @NonNull
    //todo: must implement HasUniqueId
    abstract CementPropertyCache<SomeObject> object();

    @Override
    public void bindPartialData(@NonNull ViewHoldXer holder) {
        if (title().isChanged()) {
            //do something.
            title().get().codePointAt(9);
            title().set("fd");
            //setTitle inside this
        }

        new SampleModel_.Builder().title("fd").build();
    }

    public class ViewHoldXer extends CementViewHolder {
        public ViewHoldXer(View itemView) {
            super(itemView);
        }
    }
}

