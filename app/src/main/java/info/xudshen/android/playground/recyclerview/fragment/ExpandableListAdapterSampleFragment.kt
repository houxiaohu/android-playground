package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.xudshen.android.playground.recyclerview.adapter2.ExpandableListAdapter

import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.adapter2.ExpandableList
import kotlinx.android.synthetic.main.fragment_simple_list_adapter_sample.*

/**
 * Created by Shen on 2017/2/12.
 */
class ExpandableListAdapterSampleFragment : Fragment() {
    var adapter = ExpandableListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater?.inflate(R.layout.fragment_simple_list_adapter_sample, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ExpandableListAdapter()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        val group1 = ExpandableList(UUniversalAdapterSampleFragment.TextModel(10000))
        (1..5).map {
            group1.childModels.add(UUniversalAdapterSampleFragment.TextModel(10000 + it))
        }
        adapter.addData(group1)

        reload_data.setOnClickListener {
            group1.childModels.clear()
            (1..5).map {
                group1.childModels.add(UUniversalAdapterSampleFragment.TextModel(20000 + it))
            }
            adapter.notifyDataChanged(group1)
        }
    }
}
