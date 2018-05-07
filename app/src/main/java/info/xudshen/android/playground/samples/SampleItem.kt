package info.xudshen.android.playground.samples

import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment

/**
 * Created by xudong on 2017/1/11.
 */

class SampleItem(val title: String, @DrawableRes val cover: Int = 0, val clazz: Class<out Fragment>)
