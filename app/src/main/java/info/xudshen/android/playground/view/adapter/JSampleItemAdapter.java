package info.xudshen.android.playground.view.adapter;

import info.xudshen.android.playground.data.SampleItemDataSource;
import info.xudshen.android.playground.model.SampleItemModel;
import info.xudshen.android.playground.recyclerview.adapter.JUniversalAdapter;

/**
 * @author xudshen@hotmail.com
 * @since 2017/2/6
 */
public class JSampleItemAdapter extends JUniversalAdapter {
    public void update() {
        for (SampleItemModel data : SampleItemDataSource.INSTANCE.getDATA()) {
            JSampleItemModel model = new JSampleItemModel(data);
            add(model);
        }
    }
}
