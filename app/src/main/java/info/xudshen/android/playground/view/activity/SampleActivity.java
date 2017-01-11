package info.xudshen.android.playground.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import info.xudshen.android.playground.R;
import info.xudshen.android.playground.data.SampleItemDataSource;
import info.xudshen.android.playground.model.SampleItemModel;

/**
 * Created by xudong on 2017/1/11.
 */

public class SampleActivity extends AppCompatActivity {
    public static final String KEY_INDEX = "KEY_INDEX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        int index = getIntent().getIntExtra(KEY_INDEX, 0);
        SampleItemModel itemModel = SampleItemDataSource.DATA.get(index);

        Fragment fragment = null;
        try {
            fragment = ((Fragment) itemModel.getClazz().newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }
}
