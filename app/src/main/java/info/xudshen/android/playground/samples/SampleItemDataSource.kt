package info.xudshen.android.playground.samples

import info.xudshen.android.playground.R
import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment
import info.xudshen.android.playground.imageloader.fragment.UILSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.ExpandableListAdapterSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.ItemDecorationSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.SimpleListAdapterSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.CementAdapterSampleFragment

/**
 * Created by xudong on 2017/1/11.
 */

object SampleItemDataSource {
    val DATA: List<SampleItem> = listOf(
            SampleItem(title = "ExpandableListAdapter",
                    clazz = ExpandableListAdapterSampleFragment::class.java),
            SampleItem(title = "SimpleListAdapter",
                    clazz = SimpleListAdapterSampleFragment::class.java),
            SampleItem(title = "UniversalAdapter",
                    clazz = CementAdapterSampleFragment::class.java),
            SampleItem(title = "RecyclerView ItemPadding Decoration",
                    cover = R.drawable.item_padding_decoration_cover,
                    clazz = ItemDecorationSampleFragment::class.java),
            SampleItem(title = "UIL Usage with MATCH_PARENT and RecyclerView",
                    cover = R.drawable.uil_sample_cover,
                    clazz = UILSampleFragment::class.java),
            SampleItem(title = "Svg Sample",
                    clazz = SvgSampleFragment::class.java)
    )

    val IMAGE_DATA: List<String> = listOf(
            "http://www.bing.com/az/hprichbg/rb/MacaquesWulingyuan_ZH-CN8705472129_1920x1080.jpg",
            "http://www.bing.com/az/hprichbg/rb/TempleOfValadier_ZH-CN13184904528_1920x1080.jpg",
            "https://cdn.wallpaper.com/main/styles/background/s3/2017/01/ralphs-coffee-and-bar-london-1.jpg",
            "https://cdn.wallpaper.com/main/styles/travel_medium/s3/2016/11/yswara-tea-room-12.jpg"
    )
}