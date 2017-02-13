package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.adapter2.eventhook.OnLongClickEventHook
import info.xudshen.android.playground.recyclerview.adapter2.UniversalAdapter
import kotlinx.android.synthetic.main.fragment_uuniversal_adapter_sample.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_horizontal_list_item.view.*
import kotlinx.android.synthetic.main.layout_text_item.view.*
import java.util.*

/**
 * @author xudong
 * *
 * @since 2017/2/7
 */

class UUniversalAdapterSampleFragment : Fragment() {
    //not right, just for no more error
    var adapter: UniversalAdapter = UniversalAdapter()

    val add1Btn = ButtonModel("add 1")
    val add10Btn = ButtonModel("add 10")
    val addFirstBtn = ButtonModel("add first")
    val insertBefore50Btn = ButtonModel("insert at pos=50")
    val insertAfter2Btn = ButtonModel("insert after pos=2")
    val removeFirstBtn = ButtonModel("remove first")
    val removeAllBtn = ButtonModel("remove all")
    val shuffleBtn = ButtonModel("shuffle")
    val btnList = listOf(add1Btn, add10Btn, addFirstBtn, insertBefore50Btn, insertAfter2Btn, removeFirstBtn, removeAllBtn, shuffleBtn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_uuniversal_adapter_sample, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.title = "UniversalAdapter"

        adapter = UniversalAdapter()
        adapter.setSpanCount(4)
        list.layoutManager = GridLayoutManager(context, 4)
        (list.layoutManager as GridLayoutManager).spanSizeLookup = adapter.spanSizeLookup

        adapter.setOnItemClickListener { view, vh, pos, absModel ->
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

        adapter.addEventHook(object : OnLongClickEventHook<ButtonModel.ViewHolder>(ButtonModel.ViewHolder::class.java) {
            override fun onLongClick(view: View, viewHolder: ButtonModel.ViewHolder, position: Int, rawModel: UniversalAdapter.AbstractModel<*>): Boolean {
                if (rawModel is ButtonModel) {
                    if (rawModel === add1Btn) {
                        adapter.addModel(TextModel(adapter.itemCount - btnList.size))
                    }
                    if (rawModel === add10Btn) {
                        val pos = adapter.itemCount - btnList.size
                        adapter.addModels((0..9).map { TextModel(it + pos) })
                    }
                    if (rawModel === addFirstBtn) {
                        adapter.insertModelBefore(TextModel(insertId--), adapter.getModel(btnList.size))
                    }
                    if (rawModel === insertBefore50Btn) {
                        val model = adapter.getModel(btnList.size + 50)
                        insertId -= 1
                        adapter.insertModelBefore(HorizontalListModel(insertId.toLong()), model)
                    }
                    if (rawModel === insertAfter2Btn) {
                        val model = adapter.getModel(btnList.size + 3 - 1)
                        insertId -= 1
                        adapter.insertModelAfter(HorizontalListModel(insertId.toLong()), model)
                    }
                    if (rawModel === removeFirstBtn) {
                        adapter.removeModel(adapter.getModel(btnList.size))
                    }
                    if (rawModel === removeAllBtn) {
                        adapter.removeAllAfterModel(btnList.last())
                    }
                    if (rawModel === shuffleBtn) {
                        val list = adapter.getAllModelListAfter(btnList.last())
                        Collections.shuffle(list)
                        btnList.reversed().map { list.add(0, it) }
                        adapter.replaceAllModels(list)
                    }
                }
                return true
            }

            override fun onBind(viewHolder: ButtonModel.ViewHolder): View? {
                return viewHolder.itemView.section_title
            }
        })

        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        adapter.addModels(btnList)
        val pos = adapter.itemCount - btnList.size
        adapter.addModels((0..60).map { TextModel(it + pos) })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        adapter.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.onRestoreInstanceState(savedInstanceState)
    }

    class ButtonModel(var title: String) : UniversalAdapter.AbstractModel<ButtonModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.layout_button_item

        override fun getViewHolderCreator(): UniversalAdapter.IViewHolderCreator<ViewHolder> =
                UniversalAdapter.IViewHolderCreator { view -> ViewHolder(view) }

        override fun bindData(holder: ViewHolder) {
            holder.itemView.section_title.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int = 2

        class ViewHolder(itemView: View) : UniversalAdapter.ViewHolder(itemView)
    }


    class TextModel(val index: Int, var clicked: Boolean = false) : UniversalAdapter.AbstractModel<TextModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.layout_text_item

        override fun getViewHolderCreator(): UniversalAdapter.IViewHolderCreator<ViewHolder> =
                UniversalAdapter.IViewHolderCreator { view -> ViewHolder(view) }

        override fun bindData(holder: ViewHolder) {
            holder.itemView.section_title.text = if (clicked) "$index clicked" else "$index"
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int = totalSpanCount

        override fun shouldSaveViewState(): Boolean = true

        override fun unbind(holder: ViewHolder) {
            holder.itemView.input.setText("")
        }

        override fun id(): Long = index.toLong()

        override fun isItemTheSame(item: UniversalAdapter.AbstractModel<*>): Boolean
                = item is TextModel && index == item.index

        override fun isContentTheSame(item: UniversalAdapter.AbstractModel<*>): Boolean
                = item is TextModel && clicked == item.clicked

        class ViewHolder(itemView: View) : UniversalAdapter.ViewHolder(itemView)
    }

    class HorizontalListModel(id: Long) : UniversalAdapter.AbstractModel<HorizontalListModel.ViewHolder>(id) {

        override fun getLayoutRes(): Int = R.layout.layout_horizontal_list_item

        override fun bindData(holder: ViewHolder) {
            (holder.itemView.rv.adapter as ViewHolder.HAdapter).update(id())
        }

        override fun shouldSaveViewState(): Boolean = true

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int = totalSpanCount

        override fun getViewHolderCreator(): UniversalAdapter.IViewHolderCreator<ViewHolder> =
                UniversalAdapter.IViewHolderCreator { ViewHolder(it) }

        class ViewHolder(itemView: View) : UniversalAdapter.ViewHolder(itemView) {
            init {
                itemView.rv.layoutManager = LinearLayoutManager(itemView.context, OrientationHelper.HORIZONTAL, false)
                itemView.rv.adapter = HAdapter(0)
            }

            class HAdapter(var id: Long) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                fun update(id: Long) {
                    this.id = id
                    notifyDataSetChanged()
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
                        object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false)) {

                        }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    holder.itemView.section_title.text = "$id:$position"
                }

                override fun getItemCount(): Int = 10

                override fun getItemViewType(position: Int): Int = R.layout.layout_simple_item
            }
        }
    }
}
