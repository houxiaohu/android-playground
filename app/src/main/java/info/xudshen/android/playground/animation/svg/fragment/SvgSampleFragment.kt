package info.xudshen.android.playground.animation.svg.fragment

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import info.xudshen.android.playground.R

/**
 * Created by xudong on 2017/1/11.
 */

class SvgSampleFragment : Fragment() {
    var animImageView: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_svg_sample, container, false)
        animImageView = view?.findViewById(R.id.anim_iv) as ImageView
        view?.findViewById(R.id.anim_trigger)?.setOnClickListener {
            (animImageView?.background as Animatable).start()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        animImageView?.setBackgroundResource(R.drawable.clock_animate)
//        animImageView?.background =  PathTracingDrawable(context, R.raw.helloworld)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
