package info.xudshen.android.playground.recyclerview.model;

import android.support.annotation.NonNull;
import android.view.View;
import info.xudshen.android.playground.R;
import info.xudshen.android.playground.cement.*;

@CementModelClass(layout = R.layout.layout_button_item)
public abstract class SecondModel extends CementModel<SecondModel.ViewHoldXer> {
    @CementProperty(id = true)
    @NonNull
    abstract CementPropertyCache<String> second();

    public class ViewHoldXer extends CementViewHolder {
        public ViewHoldXer(View itemView) {
            super(itemView);
        }
    }
}
