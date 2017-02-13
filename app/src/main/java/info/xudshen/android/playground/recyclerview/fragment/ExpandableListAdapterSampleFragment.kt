package info.xudshen.android.playground.recyclerview.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import info.xudshen.android.playground.R
import info.xudshen.android.playground.recyclerview.adapter2.ExpandableList
import info.xudshen.android.playground.recyclerview.adapter2.ExpandableListAdapter
import kotlinx.android.synthetic.main.fragment_simple_list_adapter_sample.*
import java.util.*

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


        val random = Random()
        insert_data.setOnClickListener {
            val gid = random.nextInt(10) * 10000
            val data = ExpandableList(UUniversalAdapterSampleFragment.TextModel(gid))
            (1..random.nextInt(5)).map {
                data.childModels.add(UUniversalAdapterSampleFragment.TextModel(gid + it))
            }
            adapter.addData(data)
        }

        reload_data.setOnClickListener {
            val data = adapter.dataList.firstOrNull()
            data?.let {
                data.childModels.clear()
                val gid = data.headerModel?.id()
                gid?.let {
                    val randomStart = random.nextInt(10)
                    val randomEnd = randomStart + random.nextInt(5)
                    (randomStart..randomEnd).map {
                        data.childModels.add(UUniversalAdapterSampleFragment.TextModel(gid.toInt() + it))
                    }
                }
                adapter.notifyDataChanged(data)
            }
        }
        get_data.setOnClickListener {
            Toast.makeText(context,
                    if (adapter.dataList.isEmpty()) "" else
                        adapter.dataList.map { it.flatten().map { it.id().toString() }.reduce { id1, id2 -> "$id1,$id2" } }
                                .reduce { s1, s2 -> "$s1\n$s2" },
                    Toast.LENGTH_LONG).show()
        }
    }
}
