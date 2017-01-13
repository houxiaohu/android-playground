package info.xudshen.android.playground.imageloader.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import info.xudshen.android.playground.R
import info.xudshen.android.playground.data.SampleItemDataSource
import kotlinx.android.synthetic.main.fragment_uil_sample.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.layout_uil_sample_item.view.*

/**
 * Created by xudong on 2017/1/13.
 */

class UILSampleFragment : Fragment() {
    var options: DisplayImageOptions? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_uil_sample, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.title = "UIL Sample"

        uil_list.layoutManager = LinearLayoutManager(context)
        uil_list.adapter = UILSampleItemAdapter()

        options = DisplayImageOptions.Builder().displayer(FadeInBitmapDisplayer(3000)).build()
    }

    override fun onResume() {
        super.onResume()
    }

    inner class UILSampleItemAdapter : RecyclerView.Adapter<UILSampleItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder
                = ViewHolder(LayoutInflater.from(parent?.context).inflate(viewType, parent, false))

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.itemView?.uil_iv_left?.loadImage {
                ImageLoader.getInstance().displayImage(SampleItemDataSource.IMAGE_DATA.elementAtOrNull(position)?.first,
                        holder.itemView.uil_iv_left, options)
            }
            holder?.itemView?.uil_iv_right?.loadImage {
                ImageLoader.getInstance().displayImage(SampleItemDataSource.IMAGE_DATA.elementAtOrNull(position)?.second,
                        holder.itemView.uil_iv_right, options)
            }
        }

        override fun getItemCount(): Int = SampleItemDataSource.IMAGE_DATA.size

        override fun getItemViewType(position: Int): Int = R.layout.layout_uil_sample_item

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
