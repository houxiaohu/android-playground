package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.adapter2.AbstractLoadMoreModel
import info.xudshen.android.playground.recyclerview.adapter2.SimpleListAdapter
import kotlinx.android.synthetic.main.fragment_simple_list_adapter_sample.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @author xudong
 * *
 * @since 2017/2/9
 */

class SimpleListAdapterSampleFragment : Fragment() {
    var adapter = SimpleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_simple_list_adapter_sample, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.title = "SimpleListAdapter"

        adapter = SimpleListAdapter()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        var headerCount = 100001
        var footerCount = 200001
        var dataCount = 0

        var hasMore = false
        var loadMoreState = AbstractLoadMoreModel.COMPLETE

        add_header.setOnClickListener {
            adapter.addHeader(UUniversalAdapterSampleFragment.TextModel(headerCount++))
        }
        add_footer.setOnClickListener {
            adapter.addFooter(UUniversalAdapterSampleFragment.TextModel(footerCount++))
        }
        insert_data.setOnClickListener {
            adapter.addDataModels((0..5).map {
                UUniversalAdapterSampleFragment.TextModel(dataCount + it)
            }, hasMore)
            adapter.setLoadMoreState(loadMoreState)
            dataCount += 6
        }
        insert_data_1.setOnClickListener {
            adapter.addDataModel(UUniversalAdapterSampleFragment.TextModel(dataCount++))
        }
        reload_data.setOnClickListener {
            adapter.updateDataModels((0..3).map {
                UUniversalAdapterSampleFragment.TextModel(dataCount + it)
            }, hasMore)
            adapter.setLoadMoreState(loadMoreState)
            dataCount += 4
        }
        remove_header.setOnClickListener {
            adapter.headers.firstOrNull()?.let {
                adapter.removeHeader(adapter.headers.first())
            }
        }
        remove_footer.setOnClickListener {
            adapter.footers.lastOrNull()?.let {
                adapter.removeFooter(adapter.footers.last())
            }
        }
        get_data.setOnClickListener {
            Toast.makeText(context,
                    if (adapter.dataModels.isEmpty()) "" else
                        adapter.dataModels.map { it.id().toString() }.reduce { id1, id2 -> "$id1,$id2" },
                    Toast.LENGTH_LONG).show()
        }

        var checkLoadMoreState = fun(view: View) {
            if (view == load_no_more) {
                hasMore = !hasMore
                load_no_more.isChecked = hasMore
                if (!hasMore) {
                    load_start.isChecked = false
                    load_complete.isChecked = false
                    load_failed.isChecked = false
                }
                adapter.setHasMore(hasMore)
            } else if (view == load_start) {
                if (hasMore) {
                    loadMoreState = AbstractLoadMoreModel.START
                    load_start.isChecked = true
                    load_complete.isChecked = false
                    load_failed.isChecked = false
                }
            } else if (view == load_complete) {
                if (hasMore) {
                    loadMoreState = AbstractLoadMoreModel.COMPLETE
                    load_start.isChecked = false
                    load_complete.isChecked = true
                    load_failed.isChecked = false
                }
            } else if (view == load_failed) {
                if (hasMore) {
                    loadMoreState = AbstractLoadMoreModel.FAILED
                    load_start.isChecked = false
                    load_complete.isChecked = false
                    load_failed.isChecked = true
                }
            }
        }

        load_no_more.setOnClickListener { checkLoadMoreState(it) }
        load_start.setOnClickListener { checkLoadMoreState(it) }
        load_complete.setOnClickListener { checkLoadMoreState(it) }
        load_failed.setOnClickListener { checkLoadMoreState(it) }
    }
}
