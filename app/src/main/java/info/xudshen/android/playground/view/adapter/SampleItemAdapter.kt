package info.xudshen.android.playground.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import info.xudshen.android.playground.R
import info.xudshen.android.playground.data.SampleItemDataSource
import info.xudshen.android.playground.model.SampleItemModel
import kotlinx.android.synthetic.main.layout_sample_item.view.*

/**
 * Created by xudong on 2017/1/11.
 */
class SampleItemAdapter : RecyclerView.Adapter<SampleItemAdapter.ViewHolder>() {
    var onClickListener: ((view: View, viewHolder: ViewHolder,
                           position: Int, itemModel: SampleItemModel?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = SampleItemDataSource.DATA[position]
        holder.itemView.section_title.text = itemModel.title
        ImageLoader.getInstance().displayImage("drawable://${itemModel.cover}", holder.itemView.section_cover)
    }

    override fun getItemCount(): Int = SampleItemDataSource.DATA.size

    override fun getItemViewType(position: Int): Int = R.layout.layout_sample_item

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClickListener?.invoke(it, this@ViewHolder, adapterPosition,
                        SampleItemDataSource.DATA.elementAtOrNull(adapterPosition))
            }
        }
    }
}
