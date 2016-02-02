package cn.easydone.androidmvppractice.presenter;

import java.util.List;

import cn.easydone.androidmvppractice.bean.User;
import cn.easydone.androidmvppractice.model.UserModel;
import cn.easydone.androidmvppractice.view.MainView;
import io.realm.RealmResults;

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

    public MainPresenterImp(MainView mainView, List<String> users) {
        onResume(mainView);
        userModel = new UserModel(this);
        this.users = users;
    }

    @Override
    public void onResume(MainView view) {
        this.mainView = view;
    }

    @Override
    public void onDestory() {
        mainView = null;
    }

    public void loadData() {
        mainView.showProgress();
        userModel.loadData(users);
    }

    @Override
    public void loadDataSuccess(RealmResults<User> users) {
        mainView.setupRecyclerView(users);
        mainView.hideProgress();
    }

    @Override
    public void loadDataFailure() {
        mainView.hideProgress();
    }
}
