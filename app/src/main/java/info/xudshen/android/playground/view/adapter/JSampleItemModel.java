package info.xudshen.android.playground.view.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import info.xudshen.android.playground.R;
import info.xudshen.android.playground.data.SampleItemDataSource;
import info.xudshen.android.playground.model.SampleItemModel;
import info.xudshen.android.playground.recyclerview.adapter.AbstractModelWithHolder;
import info.xudshen.android.playground.recyclerview.adapter.ClickableItemHolder;
import info.xudshen.android.playground.recyclerview.adapter.JUniversalAdapter;
import info.xudshen.android.playground.view.activity.SampleActivity;

/**
 * @author xudshen@hotmail.com
 * @since 2017/2/6
 */
public class JSampleItemModel extends AbstractModelWithHolder<JSampleItemModel.SampleItemHolder> {
    @Nullable
    private SampleItemModel data;

    public JSampleItemModel(@NonNull SampleItemModel data) {
        super(R.layout.layout_sample_item);
        this.data = data;
    }

    @NonNull
    @Override
    public SampleItemHolder createBindableHolder(View view) {
        return new SampleItemHolder();
    }

    @Override
    public void bindData(@NonNull SampleItemHolder view) {
        if (data == null) return;

        view.title.setText(data.getTitle());
    }

    public class SampleItemHolder extends ClickableItemHolder<JSampleItemModel> {
        private TextView title;

        @Override
        public void onItemClick(View view, JSampleItemModel itemModel) {
            //do jump
            Intent intent = new Intent(view.getContext(), SampleActivity.class);
            intent.putExtra(SampleActivity.KEY_INDEX, SampleItemDataSource.INSTANCE.getDATA().indexOf(itemModel.data));
            view.getContext().startActivity(intent);
        }

        @Override
        public void bindView(@NonNull View itemView, @NonNull JUniversalAdapter.ViewHolder viewHolder) {
            super.bindView(itemView, viewHolder);
            title = ((TextView) itemView.findViewById(R.id.section_title));
        }
    }
}
