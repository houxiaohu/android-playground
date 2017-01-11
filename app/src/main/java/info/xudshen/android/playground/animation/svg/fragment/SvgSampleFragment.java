package info.xudshen.android.playground.animation.svg.fragment;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.xudshen.android.playground.R;
import info.xudshen.android.playground.animation.svg.PathTracingDrawable;

/**
 * Created by xudong on 2017/1/11.
 */

public class SvgSampleFragment extends Fragment {
    @BindView(R.id.anim_iv)
    ImageView animImageView;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_svg_sample, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        animImageView.setBackground(new PathTracingDrawable(getContext(), R.raw.helloworld));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.anim_trigger)
    public void startAnimation() {
        ((Animatable) animImageView.getBackground()).start();
    }
}
