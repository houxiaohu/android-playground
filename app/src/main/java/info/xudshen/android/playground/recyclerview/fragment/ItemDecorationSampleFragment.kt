package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.itemdecoration.GridPaddingItemDecoration
import info.xudshen.android.playground.recyclerview.itemdecoration.LinearPaddingItemDecoration
import info.xudshen.android.playground.utils.dp2px
import kotlinx.android.synthetic.main.fragment_item_decoration_sample.*
import kotlinx.android.synthetic.main.layout_simple_item.view.*


/**
 * Created by xudong on 2017/1/18.
 */

class ItemDecorationSampleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_item_decoration_sample, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item_padding_lm_horizontal.isNestedScrollingEnabled = false
        item_padding_lm_horizontal.layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
        item_padding_lm_horizontal.addItemDecoration(LinearPaddingItemDecoration(
                context!!.dp2px(5), context!!.dp2px(5), context!!.dp2px(15)))
        item_padding_lm_horizontal.adapter = SimpleItemAdapter(count = 10, autoWidth = false)

        item_padding_lm_vertical.isNestedScrollingEnabled = false
        item_padding_lm_vertical.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        item_padding_lm_vertical.addItemDecoration(LinearPaddingItemDecoration(
                context!!.dp2px(2), context!!.dp2px(2), context!!.dp2px(4)))
        item_padding_lm_vertical.adapter = SimpleItemAdapter(count = 4, autoWidth = false)

        item_padding_gm_horizontal.isNestedScrollingEnabled = false
        item_padding_gm_horizontal.layoutManager = GridLayoutManager(context, 5, OrientationHelper.HORIZONTAL, false)
        item_padding_gm_horizontal.addItemDecoration(GridPaddingItemDecoration(
                context!!.dp2px(2), context!!.dp2px(2), context!!.dp2px(4)))
        item_padding_gm_horizontal.adapter = SimpleItemAdapter(count = 40, autoWidth = false)

        item_padding_gm_vertical.isNestedScrollingEnabled = false
        item_padding_gm_vertical.layoutManager = GridLayoutManager(context, 3, OrientationHelper.VERTICAL, false)
        item_padding_gm_vertical.addItemDecoration(GridPaddingItemDecoration(
                context!!.dp2px(5), context!!.dp2px(5), context!!.dp2px(15)))
        item_padding_gm_vertical.adapter = SimpleItemAdapter(count = 15)
    }

    class SimpleItemAdapter(val count: Int, val autoWidth: Boolean = true)
        : RecyclerView.Adapter<SimpleItemAdapter.SimpleItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleItemViewHolder =
                SimpleItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))

        override fun onBindViewHolder(holder: SimpleItemViewHolder, position: Int) {
            holder.itemView.section_title.text = position.toString()
            holder.itemView.post {
                holder.itemView.section_desc.text = "width:${holder.itemView.width}\n" +
                        "height:${holder.itemView.height}\n" +
                        "autoWidth:$autoWidth"
            }
        }

        override fun getItemCount(): Int = count

        override fun getItemViewType(position: Int): Int = R.layout.layout_simple_item

        inner class SimpleItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            init {
                if (autoWidth) {
                    itemView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            }
        }
    }
}
