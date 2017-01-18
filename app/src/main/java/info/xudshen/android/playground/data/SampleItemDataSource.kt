package info.xudshen.android.playground.data

import android.support.v4.app.Fragment
import info.xudshen.android.playground.R
import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment
import info.xudshen.android.playground.imageloader.fragment.UILSampleFragment
import info.xudshen.android.playground.model.SampleItemModel
import info.xudshen.android.playground.recyclerview.fragment.ItemDecorationSampleFragment

/**
 * Created by xudong on 2017/1/11.
 */

object SampleItemDataSource {
    val DATA: List<SampleItemModel> = listOf(
            SampleItemModel(title = "Svg Sample", clazz = SvgSampleFragment::class.java),
            SampleItemModel(title = "UIL Usage with MATCH_PARENT and RecyclerView", cover = R.drawable.uil_sample_cover, clazz = UILSampleFragment::class.java),
            SampleItemModel(title = "RecyclerView ItemPadding Decoration", cover = R.drawable.item_padding_decoration_cover, clazz = ItemDecorationSampleFragment::class.java),
            SampleItemModel(title = "title4", clazz = Fragment::class.java),
            SampleItemModel(title = "title5", clazz = Fragment::class.java),
            SampleItemModel(title = "title6", clazz = Fragment::class.java)
    )

    val IMAGE_DATA: List<String> = listOf(
            "http://www.bing.com/az/hprichbg/rb/MacaquesWulingyuan_ZH-CN8705472129_1920x1080.jpg",
            "http://www.bing.com/az/hprichbg/rb/TempleOfValadier_ZH-CN13184904528_1920x1080.jpg",
            "https://cdn.wallpaper.com/main/styles/background/s3/2017/01/ralphs-coffee-and-bar-london-1.jpg",
            "https://cdn.wallpaper.com/main/styles/travel_medium/s3/2016/11/yswara-tea-room-12.jpg"
    )
}
