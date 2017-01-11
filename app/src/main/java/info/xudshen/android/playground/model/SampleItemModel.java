package info.xudshen.android.playground.model;

/**
 * Created by xudong on 2017/1/11.
 */

public class SampleItemModel {
    private String title;
    private Class clazz;

    public SampleItemModel(String title, Class clazz) {
        this.title = title;
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
