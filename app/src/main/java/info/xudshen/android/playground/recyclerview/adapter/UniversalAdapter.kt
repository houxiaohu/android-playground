package info.xudshen.android.playground.recyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import info.xudshen.android.playground.R

/**
 * @author xudshen@hotmail.com
 * *
 * @since 2017/1/24
 */
abstract class UniversalAdapter : RecyclerView.Adapter<UniversalAdapter.ViewHolder>() {
    val models: MutableList<AbstractModel<*>> = mutableListOf()
    val defaultModel = DefaultModel()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(viewType, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) =
            onBindViewHolder(holder, position, mutableListOf())

    override fun onBindViewHolder(holder: ViewHolder?, position: Int, payloads: MutableList<Any>?) {
        @Suppress("UNCHECKED_CAST")
        val model = models.getOrElse(position, { defaultModel }) as AbstractModel<Any>

        holder?.let {
            if (model is AbstractModelWithHolder<*>) {
                holder.modelViewHolder = holder.modelViewHolder ?: model.createModelViewHolder(holder.itemView)
                holder.modelViewHolder?.bindView(holder.itemView)
            }
            model.bindModelViewHolder(holder.modelViewHolder ?: holder.itemView, position, payloads)
        }
    }

    override fun getItemCount(): Int = models.size

    override fun getItemViewType(position: Int): Int = models.getOrElse(position, { defaultModel }).viewType

    fun <T : AbstractModel<*>> add(model: T) = models.add(model)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var modelViewHolder: IModelViewHolder? = null
    }

    interface IModelViewHolder {
        fun bindView(itemView: View)
    }

    class SimpleModelViewHolder : IModelViewHolder {
        override fun bindView(itemView: View) {
        }
    }

    abstract class AbstractModel<in V>(val viewType: Int = NO_ID) {
        abstract fun bindModelViewHolder(view: V, position: Int, payloads: MutableList<Any>?)
    }

    abstract class AbstractModelWithHolder<MVH : IModelViewHolder>(viewType: Int = NO_ID)
        : AbstractModel<MVH>(viewType) {
        abstract fun createModelViewHolder(view: View): MVH
    }


    class DefaultModel : AbstractModel<View>(R.layout.layout_default_empty_item) {
        override fun bindModelViewHolder(view: View, position: Int, payloads: MutableList<Any>?) {
        }
    }

    class DefaultModelWithViewHolder : AbstractModelWithHolder<SimpleModelViewHolder>(R.layout.layout_default_empty_item) {
        override fun createModelViewHolder(view: View): SimpleModelViewHolder = SimpleModelViewHolder()

        override fun bindModelViewHolder(view: SimpleModelViewHolder, position: Int, payloads: MutableList<Any>?) {
        }
    }
}
