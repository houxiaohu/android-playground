package info.xudshen.android.playground.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.xudshen.android.playground.R;
import info.xudshen.android.playground.view.adapter.SampleItemAdapter;

/**
 * Created by xudong on 2017/1/10.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sample_list)
    RecyclerView sampleRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
        initEvents();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        collapsingToolbarLayout.setTitle("Playground");

        sampleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sampleRecyclerView.setAdapter(new SampleItemAdapter());
    }

    private void initEvents() {
//        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        ((SampleItemAdapter) sampleRecyclerView.getAdapter()).setOnClickListener(
                (view, viewHolder, position, itemModel) -> {
                    Intent intent = new Intent(MainActivity.this, SampleActivity.class);
                    intent.putExtra(SampleActivity.KEY_INDEX, position);
                    startActivity(intent);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
