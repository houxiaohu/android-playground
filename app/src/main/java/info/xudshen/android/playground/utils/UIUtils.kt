package info.xudshen.android.playground.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest


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

suspend fun View.takeScreenShot(file: File) {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(toByteArray())
    return digested.joinToString("") {
        String.format("%02x", it)
    }
}