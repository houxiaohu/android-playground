package info.xudshen.android.playground.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.xudshen.android.playground.R;
import info.xudshen.android.playground.data.SampleItemDataSource;
import info.xudshen.android.playground.model.SampleItemModel;

/**
 * Created by xudong on 2017/1/11.
 */

public class SampleItemAdapter extends RecyclerView.Adapter<SampleItemAdapter.ViewHolder> {
    private OnClickListener onClickListener;

    public SampleItemAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SampleItemModel itemModel = SampleItemDataSource.DATA.get(position);
        holder.title.setText(itemModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return SampleItemDataSource.DATA.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.layout_sample_item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.section_title)
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(view, ViewHolder.this, getAdapterPosition(),
                                SampleItemDataSource.DATA.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnClickListener {
        void onClick(View view, ViewHolder viewHolder, int position, SampleItemModel itemModel);
    }

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
