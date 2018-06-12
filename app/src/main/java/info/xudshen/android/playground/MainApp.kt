package info.xudshen.android.playground

import android.support.multidex.MultiDexApplication
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * Created by xudong on 2017/1/10.
 */

class MainApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        val config = ImageLoaderConfiguration.Builder(applicationContext)
                .writeDebugLogs()
                .build()
        ImageLoader.getInstance().init(config)
        System.setProperty("kotlinx.coroutines.debug", "on")
    }
}
