package cn.easydone.androidmvppractice.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.androidmvppractice.bean.User;
import cn.easydone.androidmvppractice.model.UserModel;
import cn.easydone.androidmvppractice.view.MainView;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscription;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2016-01-25
 * Time: 13:26
 */
public class MainPresenterImp implements MainPresenter {

    private MainView mainView;
    private UserModel userModel;
    private List<String> users;
    private Subscription subscription;
    private Realm realm;

    public MainPresenterImp(MainView mainView) {
        onResume(mainView);
        this.realm = Realm.getDefaultInstance();
        userModel = new UserModel(this);
        List<String> users = new ArrayList<>();
        users.add("liangzhitao");
        users.add("AlanCheen");
        users.add("yongjhih");
        users.add("zzz40500");
        users.add("greenrobot");
        users.add("nimengbo");
        this.users = users;
    }

    @Override
    public void onResume(MainView view) {
        this.mainView = view;
    }

    @Override
    public void onDestory() {
        mainView = null;
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        realm.close();
    }

    public void loadDataFromRealm() {
        userModel.loadDataFromRealm(realm);
    }

    public void loadData() {
        mainView.showProgress();
        subscription = userModel.loadData(users, realm);
    }

    public void removeDataFromRealm(int position) {
        RealmResults<User> realmResults = realm.where(User.class).findAll();
        realm.executeTransaction(realm1 -> realmResults.remove(position));
    }

    @Override
    public void loadDataFromRealmSuccess(RealmResults<User> users) {
        mainView.setupRecyclerView(users);
    }

    @Override
    public void loadDataSuccess(RealmResults<User> users) {
        mainView.notifyDataSetChanged(users);
        mainView.hideProgress();
    }

    @Override
    public void loadDataFailure() {
        mainView.hideProgress();
    }

}
