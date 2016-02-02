package cn.easydone.androidmvppractice.view;

import cn.easydone.androidmvppractice.bean.User;
import io.realm.RealmResults;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2016-01-25
 * Time: 13:23
 */
public interface MainView extends BaseView {

    void setupRecyclerView(RealmResults<User> users);
    void notifyDataSetChanged(RealmResults<User> users);
}
