package info.xudshen.android.playground.recyclerview.adapter

import android.view.View
import android.widget.TextView
import info.xudshen.android.playground.R
import info.xudshen.android.playground.data.SampleItemDataSource
import info.xudshen.android.playground.model.SampleItemModel

/**
 * @author xudshen@hotmail.com
 * *
 * @since 2017/1/24
 */
class SimpleUniversalAdapter : UniversalAdapter() {
    fun update() {
        SampleItemDataSource.DATA.map {
            add(SampleItemViewModel(it))
        }
        notifyDataSetChanged()
    }

    class SampleItemViewModel(val data: SampleItemModel) : AbstractModelWithHolder<SampleItemViewModel.SampleItemModelViewHolder>(R.layout.layout_sample_item) {
        override fun createModelViewHolder(view: View): SampleItemModelViewHolder =
                SampleItemModelViewHolder()

        override fun bindModelViewHolder(view: SampleItemModelViewHolder, position: Int, payloads: MutableList<Any>?) {
            view.title?.text = data.title + "yeah"
        }

        class SampleItemModelViewHolder : IModelViewHolder {
            var title: TextView? = null
            override fun bindView(itemView: View) {
                title = itemView.findViewById(R.id.section_title) as TextView
            }
        }
    }
}
