package info.xudshen.android.playground.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import info.xudshen.android.playground.R
import info.xudshen.android.playground.view.adapter.JSampleItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

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
        sample_list.adapter = JSampleItemAdapter()
    }

    private fun initEvents() {
//        toolbar?.setNavigationOnClickListener { onBackPressed() }
        (sample_list.adapter as JSampleItemAdapter).update()
//        (sample_list.adapter as SampleItemAdapter).onClickListener =
//                { view: View, viewHolder: SampleItemAdapter.ViewHolder,
//                  position: Int, itemModel: SampleItemModel? ->
//                    val intent = Intent(this@MainActivity, SampleActivity::class.java)
//                    intent.putExtra(SampleActivity.KEY_INDEX, position)
//                    startActivity(intent)
//                }
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
