package cn.easydone.androidmvppractice.presenter;

import cn.easydone.androidmvppractice.bean.User;
import cn.easydone.androidmvppractice.view.MainView;
import io.realm.RealmResults;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2016-01-25
 * Time: 13:24
 */
public interface MainPresenter extends Presenter<MainView> {

    void loadDataFromRealmSuccess(RealmResults<User> users);
    void loadDataSuccess(RealmResults<User> users);
    void loadDataFailure();
}
