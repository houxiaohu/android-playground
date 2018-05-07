package info.xudshen.android.playground.cement;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class CementViewHolder extends RecyclerView.ViewHolder {
    @Nullable
    CementModel model;
    boolean supportPropertyStage = false;

    public CementViewHolder(View itemView) {
        super(itemView);
    }

    void bind(@NonNull CementModel model, @Nullable List<Object> payloads) {
        if (supportPropertyStage) {
            //only start stage property after first bind
            model.shouldStageProperty(true);
        }
        if (model.isPropertiesChanged()) {
            // noinspection unchecked
            model.bindPartialData(this);
            model.doPropertiesCleanUp();
        } else if (payloads != null && !payloads.isEmpty()) {
            // noinspection unchecked
            model.bindData(this, payloads);
        } else {
            // noinspection unchecked
            model.bindData(this);
        }

        this.model = model;
    }

    void unbind() {
        if (model == null) return;

        if (supportPropertyStage) {
            //disable stage property after unbind
            model.shouldStageProperty(false);
        }
        if (model.isPropertiesChanged()) {
            model.doPropertiesCleanUp();
        }
        // noinspection unchecked
        model.unbind(this);
        model = null;
    }

    boolean shouldSaveViewState() {
        return model != null && model.shouldSaveViewState();
    }
}