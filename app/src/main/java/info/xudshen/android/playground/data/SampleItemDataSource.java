package info.xudshen.android.playground.data;

import java.util.ArrayList;
import java.util.List;

import info.xudshen.android.playground.model.SampleItemModel;
import info.xudshen.android.playground.view.activity.MainActivity;

/**
 * Created by xudong on 2017/1/11.
 */

public class SampleItemDataSource {
    public static final List<SampleItemModel> DATA = new ArrayList<SampleItemModel>() {{
        add(new SampleItemModel("title1", MainActivity.class));
        add(new SampleItemModel("title2", MainActivity.class));
        add(new SampleItemModel("title3", MainActivity.class));
        add(new SampleItemModel("title4", MainActivity.class));
        add(new SampleItemModel("title5", MainActivity.class));
        add(new SampleItemModel("title6", MainActivity.class));
    }};
}
