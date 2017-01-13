package info.xudshen.android.playground.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by xudong on 2017/1/13.
 */

class SmartImageView : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var loadTask: (() -> Unit)? = null
    var waitingForMeasure: Boolean = true

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed && waitingForMeasure) {
            waitingForMeasure = false
            loadTask?.invoke()
        }
    }

    fun loadImage(loadTask: () -> Unit) {
        if (waitingForMeasure) this.loadTask = loadTask
        else loadTask.invoke()
    }
}
