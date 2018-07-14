package info.xudshen.android.playground

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.nostra13.universalimageloader.core.ImageLoader
import info.xudshen.android.playground.cement.*
import info.xudshen.android.playground.recyclerview.itemdecoration.GridPaddingItemDecoration
import info.xudshen.android.playground.utils.AppJobs
import info.xudshen.android.playground.utils.dp2px
import info.xudshen.android.playground.utils.md5
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_model_sample.view.*
import java.io.File

/**
 * Created by xudong on 2017/1/10.
 */

class MainActivity : AppCompatActivity() {
    private val adapter = ExpandableCementAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expandableMap = mutableMapOf<SampleCategory, ExpandableList>()
        SampleDataSource.DATA.forEach {
            if (it.category !in expandableMap) {
                expandableMap[it.category] = ExpandableList(SampleHeaderItemModel(it.category))
            }
            expandableMap[it.category]!!.childModels.add(SampleItemModel(it))
        }
        adapter.updateDataList(SampleCategory.values()
                .filter { it in expandableMap }.map { expandableMap[it] })

        initViews()
        initEvents()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        collapsing_toolbar.title = "My Playground"

        adapter.setSpanCount(2)
        val lm = GridLayoutManager(this, 2)
        lm.spanSizeLookup = adapter.spanSizeLookup
        sample_list.layoutManager = lm
        sample_list.itemAnimator = DefaultItemAnimator()
        val dp8 = this.dp2px(8)
        sample_list.addItemDecoration(GridPaddingItemDecoration(dp8, dp8, dp8))
    }

    private fun initEvents() {
        adapter.setOnItemClickListener { _, _, _, model ->
            val intent = Intent(this@MainActivity, SampleContainerActivity::class.java)
            intent.putExtra(SampleContainerActivity.KEY_SAMPLE_ITEM,
                    (model as SampleItemModel).data)
            startActivity(intent)
        }
        sample_list.adapter = adapter
    }

    override fun onDestroy() {
        AppJobs.mainapp.cancel()

        super.onDestroy()
    }

    class SampleItemModel(val data: SampleItem) : CementModel<SampleItemModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.item_model_sample

        override fun bindData(holder: ViewHolder) {
            holder.itemView.section_title.text = data.title

            val imageFile = File(holder.itemView.context.cacheDir, data.cacheImgName)
            if (imageFile.exists()) {
                holder.itemView.section_cover.visibility = View.VISIBLE
                holder.itemView.section_divider.visibility = View.VISIBLE
                ImageLoader.getInstance().displayImage(Uri.fromFile(imageFile).toString(),
                        holder.itemView.section_cover)
            } else {
                holder.itemView.section_cover.visibility = View.GONE
                holder.itemView.section_divider.visibility = View.GONE
            }
        }

        override fun getViewHolderCreator(): CementAdapter.IViewHolderCreator<ViewHolder> =
                CementAdapter.IViewHolderCreator { ViewHolder(it) }

        class ViewHolder(itemView: View) : CementViewHolder(itemView)
    }

    class SampleHeaderItemModel(private val category: SampleCategory) :
            CementModel<SampleHeaderItemModel.ViewHolder>() {
        override fun getLayoutRes(): Int = R.layout.item_model_sample_header

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int =
                totalSpanCount

        override fun bindData(holder: ViewHolder) {
            (holder.itemView as TextView).text = category.title
        }

        override fun getViewHolderCreator(): CementAdapter.IViewHolderCreator<ViewHolder> =
                CementAdapter.IViewHolderCreator { ViewHolder(it) }

        class ViewHolder(itemView: View) : CementViewHolder(itemView)
    }
}
