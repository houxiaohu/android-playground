package info.xudshen.android.playground.data;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.android.playground.animation.svg.fragment.SvgSampleFragment;
import info.xudshen.android.playground.model.SampleItemModel;

/**
 * Created by xudong on 2017/1/11.
 */

public class SampleItemDataSource {
    public static final List<SampleItemModel> DATA = new ArrayList<SampleItemModel>() {{
        add(new SampleItemModel("Svg Sample", SvgSampleFragment.class));
        add(new SampleItemModel("title2", Fragment.class));
        add(new SampleItemModel("title3", Fragment.class));
        add(new SampleItemModel("title4", Fragment.class));
        add(new SampleItemModel("title5", Fragment.class));
        add(new SampleItemModel("title6", Fragment.class));
    }};
}
