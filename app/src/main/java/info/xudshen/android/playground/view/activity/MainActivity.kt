package info.xudshen.android.playground.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import info.xudshen.android.playground.R
import info.xudshen.android.playground.model.SampleItemModel
import info.xudshen.android.playground.view.adapter.SampleItemAdapter

/**
 * Created by xudong on 2017/1/10.
 */

class MainActivity : AppCompatActivity() {
    internal var collapsingToolbarLayout: CollapsingToolbarLayout? = null
    internal var toolbar: Toolbar? = null
    internal var sampleRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        toolbar = findViewById(R.id.toolbar) as Toolbar
        sampleRecyclerView = findViewById(R.id.sample_list) as RecyclerView

        initViews()
        initEvents()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingToolbarLayout?.title = "Playground"

        sampleRecyclerView?.layoutManager = LinearLayoutManager(this)
        sampleRecyclerView?.adapter = SampleItemAdapter()
    }

    private fun initEvents() {
//        toolbar?.setNavigationOnClickListener { onBackPressed() }
        (sampleRecyclerView?.adapter as SampleItemAdapter).onClickListener =
                { view: View, viewHolder: SampleItemAdapter.ViewHolder,
                  position: Int, itemModel: SampleItemModel? ->
                    val intent = Intent(this@MainActivity, SampleActivity::class.java)
                    intent.putExtra(SampleActivity.KEY_INDEX, position)
                    startActivity(intent)
                }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
