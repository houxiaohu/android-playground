package info.xudshen.android.playground.data

import android.support.v4.app.Fragment
import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment
import info.xudshen.android.playground.model.SampleItemModel

/**
 * Created by xudong on 2017/1/11.
 */

object SampleItemDataSource {
    val DATA: List<SampleItemModel> = listOf(
            SampleItemModel("Svg Sample", SvgSampleFragment::class.java),
            SampleItemModel("title2", Fragment::class.java),
            SampleItemModel("title3", Fragment::class.java),
            SampleItemModel("title4", Fragment::class.java),
            SampleItemModel("title5", Fragment::class.java),
            SampleItemModel("title6", Fragment::class.java)
    )
}
