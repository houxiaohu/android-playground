package info.xudshen.android.playground.utils

import android.content.Context

/**
 * @author xudshen@hotmail.com
 * @since 2017/1/18
 */

/**
 * @see android.util.TypedValue.applyDimension
 */


fun Context.dp2px(dp: Float): Float = dp * this.resources.displayMetrics.density

fun Context.px2dp(px: Float): Float = px / this.resources.displayMetrics.density

fun Context.dp2px(dp: Int): Int = (dp2px(dp.toFloat()) + 0.5).toInt()

fun Context.px2dp(px: Int): Int = (px2dp(px.toFloat()) + 0.5).toInt()