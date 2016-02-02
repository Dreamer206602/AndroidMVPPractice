package cn.easydone.androidmvppractice.presenter;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2016-01-26
 * Time: 08:55
 */
public interface Presenter<T> {
    void onResume(T view);
    void onDestory();
}
