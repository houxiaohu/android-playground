package info.xudshen.android.playground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.xudshen.android.playground.utils.*
import kotlinx.coroutines.experimental.launch
import java.io.File

/**
 * Created by xudong on 2017/1/11.
 */
class SampleContainerActivity : AppCompatActivity() {
    private var sampleItem: SampleItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_container)

        sampleItem = intent.getParcelableExtra(KEY_SAMPLE_ITEM) as SampleItem

        var fragment = supportFragmentManager.findFragmentByTag(sampleItem!!.clazz.name)
        if (fragment == null) {
            fragment = sampleItem!!.clazz.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, sampleItem!!.clazz.name).commit()
        }
    }

    override fun onPause() {
        super.onPause()

        launch(AppCoroutineDispatchers.disk, parent = AppJobs.mainapp) {
            log("saving screenshot")
            window.decorView.rootView.takeScreenShot(File(this@SampleContainerActivity.cacheDir,
                    sampleItem?.cacheImgName))
            log("saving screenshot complete")
        }
    }

    companion object {
        const val KEY_SAMPLE_ITEM = "KEY_SAMPLE_ITEM"
    }
}