package info.xudshen.android.playground.view.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    public void bindData(ViewHolder holder) {
        //do something
        holder.title.setText(data.getTitle());
    }

    @Override
    public UUniversalAdapter.IViewHolderCreator<ViewHolder> getViewHolderCreator() {
        return new UUniversalAdapter.IViewHolderCreator<ViewHolder>() {
            @Override
            public ViewHolder create(View view) {
                return new ViewHolder(view);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = ((TextView) itemView.findViewById(R.id.section_title));
        }
    }
}
