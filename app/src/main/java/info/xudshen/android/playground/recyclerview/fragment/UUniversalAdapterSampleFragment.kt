package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.adapter2.EventHook
import info.xudshen.android.playground.recyclerview.adapter2.UUniversalAdapter
import kotlinx.android.synthetic.main.fragment_uuniversal_adapter_sample.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_text_item.view.*
import java.util.*

/**
 * @author xudong
 * *
 * @since 2017/2/7
 */

class UUniversalAdapterSampleFragment : Fragment() {
    val adapter = UUniversalAdapter()

    val add1Btn = ButtonModel("add 1")
    val add10Btn = ButtonModel("add 10")
    val addFirstBtn = ButtonModel("add first")
    val insertBefore2Btn = ButtonModel("insert at pos=2")
    val insertAfter2Btn = ButtonModel("insert after pos=2")
    val removeFirstBtn = ButtonModel("remove first")
    val removeAllBtn = ButtonModel("remove all")
    val shuffleBtn = ButtonModel("shuffle")
    val btnList = listOf(add1Btn, add10Btn, addFirstBtn, insertBefore2Btn, insertAfter2Btn, removeFirstBtn, removeAllBtn, shuffleBtn)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_uuniversal_adapter_sample, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.title = "UUniversalAdapter"

        adapter.setSpanCount(4)
        list.layoutManager = GridLayoutManager(context, 4)
        (list.layoutManager as GridLayoutManager).spanSizeLookup = adapter.spanSizeLookup

        list.adapter = adapter

        adapter.setOnItemClickListener { view, pos, absModel ->
            when (absModel) {
                is ButtonModel -> {

                }
                is TextModel -> {
                    absModel.clicked = !absModel.clicked
                    adapter.notifyModelChanged(absModel)
                }
            }
        }

        var insertId = 10000

        adapter.addEventHook(object : EventHook<ButtonModel.ViewHolder>(ButtonModel.ViewHolder::class.java) {
            override fun onEvent(view: View, viewHolder: ButtonModel.ViewHolder, adapter: UUniversalAdapter) {
                view.setOnClickListener {
                    val absModel = adapter.getModel(viewHolder.adapterPosition)
                    if (absModel is ButtonModel) {
                        if (absModel === add1Btn) {
                            adapter.addModel(TextModel(adapter.itemCount - btnList.size))
                        }
                        if (absModel === add10Btn) {
                            val pos = adapter.itemCount - btnList.size
                            adapter.addModels((0..9).map { TextModel(it + pos) })
                        }
                        if (absModel === addFirstBtn) {
                            adapter.insertModelBefore(TextModel(insertId--), adapter.getModel(btnList.size))
                        }
                        if (absModel === insertBefore2Btn) {
                            val model = adapter.getModel(btnList.size + 3 - 1)
                            adapter.insertModelBefore(TextModel(insertId--), model)
                        }
                        if (absModel === insertAfter2Btn) {
                            val model = adapter.getModel(btnList.size + 3 - 1)
                            insertId -= 1
                            adapter.insertModelAfter(TextModel(insertId--), model)
                        }
                        if (absModel === removeFirstBtn) {
                            adapter.removeModel(adapter.getModel(btnList.size))
                        }
                        if (absModel === removeAllBtn) {
                            adapter.removeAllAfterModel(btnList.last())
                        }
                        if (absModel === shuffleBtn) {
                            val list = adapter.getAllModelListAfter(btnList.last())
                            Collections.shuffle(list)
                            btnList.reversed().map { list.add(0, it) }
                            adapter.replaceAllModels(list)
                        }
                    }
                }
            }

            override fun onBind(viewHolder: ButtonModel.ViewHolder): View? {
                return viewHolder.itemView.section_title
            }
        })
    }

    override fun onResume() {
        super.onResume()

        adapter.addModels(btnList)
    }

    class ButtonModel(var title: String) : UUniversalAdapter.AbstractModel<ButtonModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.layout_button_item

        override fun getViewHolderCreator(): UUniversalAdapter.IViewHolderCreator<ViewHolder> =
                UUniversalAdapter.IViewHolderCreator { view -> ViewHolder(view) }

        override fun bindData(holder: ViewHolder) {
            holder.itemView.section_title.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int = 2

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }


    class TextModel(val index: Int, var clicked: Boolean = false) : UUniversalAdapter.AbstractModel<TextModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.layout_text_item

        override fun getViewHolderCreator(): UUniversalAdapter.IViewHolderCreator<ViewHolder> =
                UUniversalAdapter.IViewHolderCreator { view -> ViewHolder(view) }

        override fun bindData(holder: ViewHolder) {
            holder.itemView.section_title.text = if (clicked) "$index clicked" else "$index"
        }

        override fun isItemTheSame(item: UUniversalAdapter.AbstractModel<*>): Boolean
                = item is TextModel && index == item.index

        override fun isContentTheSame(item: UUniversalAdapter.AbstractModel<*>): Boolean
                = item is TextModel && clicked == item.clicked

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
