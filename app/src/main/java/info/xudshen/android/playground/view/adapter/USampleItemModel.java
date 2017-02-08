package info.xudshen.android.playground.view.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import info.xudshen.android.playground.R;
import info.xudshen.android.playground.model.SampleItemModel;
import info.xudshen.android.playground.recyclerview.adapter2.UUniversalAdapter;

/**
 * @author xudong
 * @since 2017/2/6
 */

public class USampleItemModel extends UUniversalAdapter.AbstractModel<USampleItemModel.ViewHolder> {
    @NonNull
    private final SampleItemModel data;

    public USampleItemModel(@NonNull SampleItemModel data) {
        this.data = data;
    }

    @LayoutRes
    @Override
    public int getLayoutRes() {
        return R.layout.layout_sample_item;
    }

    @Override
    public void bindData(@NonNull ViewHolder holder) {
        //do something
        holder.title.setText(data.getTitle());
    }

    @NonNull
    @Override
    public UUniversalAdapter.IViewHolderCreator<ViewHolder> getViewHolderCreator() {
        return new UUniversalAdapter.IViewHolderCreator<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder create(@NonNull View view) {
                return new ViewHolder(view);
            }
        };
    }

    public static class ViewHolder extends UUniversalAdapter.ViewHolder {
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = ((TextView) itemView.findViewById(R.id.section_title));
        }
    }
}
