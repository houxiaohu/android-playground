package info.xudshen.android.playground

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader
import info.xudshen.android.playground.samples.SampleItemDataSource
import info.xudshen.android.playground.samples.SampleItem
import info.xudshen.android.playground.samples.SampleItemActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_sample_item.view.*

/**
 * Created by xudong on 2017/1/10.
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initEvents()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsing_toolbar.title = "Playground"

        sample_list.layoutManager = LinearLayoutManager(this)
        sample_list.itemAnimator = DefaultItemAnimator()
        sample_list.adapter = SampleItemAdapter()
    }

    private fun initEvents() {
//        toolbar?.setNavigationOnClickListener { onBackPressed() }
        (sample_list.adapter as SampleItemAdapter).onClickListener = { _, _, pos, _ ->
            val intent = Intent(this@MainActivity, SampleItemActivity::class.java)
            intent.putExtra(SampleItemActivity.KEY_INDEX, pos)
            startActivity(intent)
        }
    }

    class SampleItemAdapter : RecyclerView.Adapter<SampleItemAdapter.ViewHolder>() {
        var onClickListener: ((view: View, viewHolder: ViewHolder,
                               position: Int, item: SampleItem?) -> Unit)? = null

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
}
