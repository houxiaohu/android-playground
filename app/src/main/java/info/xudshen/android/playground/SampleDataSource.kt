package info.xudshen.android.playground

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.Fragment
import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.CementAdapterSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.ExpandableListAdapterSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.ItemDecorationSampleFragment
import info.xudshen.android.playground.recyclerview.fragment.SimpleListAdapterSampleFragment
import info.xudshen.android.playground.thirdpartylib.fragment.UILSampleFragment
import info.xudshen.android.playground.utils.md5

/**
 * Created by xudong on 2017/1/11.
 */

enum class SampleCategory(val title: String) {
    UNKNOWN("UnCategorized"), KOTLIN("Kotlin"),
    ANIMATION("Animation"), RECYCLER_VIEW("RecyclerView"), THIRD_PARTY("ThirdParty"),
}

data class SampleItem(val category: SampleCategory = SampleCategory.UNKNOWN,
                      val title: String,
                      val clazz: Class<out Fragment>) : Parcelable {

    val cacheImgName = "${clazz.name}$title".md5()

    constructor(source: Parcel) : this(
            SampleCategory.values()[source.readInt()],
            source.readString(),
            source.readSerializable() as Class<out Fragment>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(category.ordinal)
        writeString(title)
        writeSerializable(clazz)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SampleItem> = object : Parcelable.Creator<SampleItem> {
            override fun createFromParcel(source: Parcel): SampleItem = SampleItem(source)
            override fun newArray(size: Int): Array<SampleItem?> = arrayOfNulls(size)
        }
    }
}

object SampleDataSource {
    val DATA: List<SampleItem> = listOf(
            SampleItem(SampleCategory.RECYCLER_VIEW,
                    title = "ExpandableListAdapter",
                    clazz = ExpandableListAdapterSampleFragment::class.java),
            SampleItem(SampleCategory.RECYCLER_VIEW,
                    title = "SimpleListAdapter",
                    clazz = SimpleListAdapterSampleFragment::class.java),
            SampleItem(SampleCategory.RECYCLER_VIEW,
                    title = "UniversalAdapter",
                    clazz = CementAdapterSampleFragment::class.java),
            SampleItem(SampleCategory.RECYCLER_VIEW,
                    title = "RecyclerView ItemPadding Decoration",
                    clazz = ItemDecorationSampleFragment::class.java),
            SampleItem(SampleCategory.THIRD_PARTY,
                    title = "UIL Usage with MATCH_PARENT and RecyclerView",
                    clazz = UILSampleFragment::class.java),
            SampleItem(SampleCategory.ANIMATION,
                    title = "Svg Sample",
                    clazz = SvgSampleFragment::class.java)
    )

    val IMAGE_DATA: List<String> = listOf(
            "http://www.bing.com/az/hprichbg/rb/MacaquesWulingyuan_ZH-CN8705472129_1920x1080.jpg",
            "http://www.bing.com/az/hprichbg/rb/TempleOfValadier_ZH-CN13184904528_1920x1080.jpg",
            "https://cdn.wallpaper.com/main/styles/background/s3/2017/01/ralphs-coffee-and-bar-london-1.jpg",
            "https://cdn.wallpaper.com/main/styles/travel_medium/s3/2016/11/yswara-tea-room-12.jpg"
    )
}
