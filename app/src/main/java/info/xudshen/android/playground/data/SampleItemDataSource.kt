package info.xudshen.android.playground.data

import android.support.v4.app.Fragment
import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment
import info.xudshen.android.playground.imageloader.fragment.UILSampleFragment
import info.xudshen.android.playground.model.SampleItemModel

/**
 * Created by xudong on 2017/1/11.
 */

object SampleItemDataSource {
    val DATA: List<SampleItemModel> = listOf(
            SampleItemModel("Svg Sample", SvgSampleFragment::class.java),
            SampleItemModel("UIL Sample", UILSampleFragment::class.java),
            SampleItemModel("title3", Fragment::class.java),
            SampleItemModel("title4", Fragment::class.java),
            SampleItemModel("title5", Fragment::class.java),
            SampleItemModel("title6", Fragment::class.java)
    )

    val IMAGE_DATA: List<String> = listOf(
            "http://www.bing.com/az/hprichbg/rb/MacaquesWulingyuan_ZH-CN8705472129_1920x1080.jpg",
            "http://www.bing.com/az/hprichbg/rb/TempleOfValadier_ZH-CN13184904528_1920x1080.jpg",
            "https://cdn.wallpaper.com/main/styles/background/s3/2017/01/ralphs-coffee-and-bar-london-1.jpg",
            "https://cdn.wallpaper.com/main/styles/travel_medium/s3/2016/11/yswara-tea-room-12.jpg"
    )
}
