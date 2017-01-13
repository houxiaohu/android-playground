package info.xudshen.android.playground.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.xudshen.android.playground.R
import info.xudshen.android.playground.data.SampleItemDataSource

/**
 * Created by xudong on 2017/1/11.
 */
class SampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val index = intent.getIntExtra(KEY_INDEX, 0)
        val itemModel = SampleItemDataSource.DATA.elementAtOrNull(index)

        val fragment = itemModel?.clazz?.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment).commit()
    }

    companion object {
        @JvmField
        val KEY_INDEX = "KEY_INDEX"
    }
}
