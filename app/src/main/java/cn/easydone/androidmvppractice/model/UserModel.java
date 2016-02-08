package cn.easydone.androidmvppractice.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.androidmvppractice.bean.GitHubUser;
import cn.easydone.androidmvppractice.bean.User;
import cn.easydone.androidmvppractice.presenter.MainPresenterImp;
import cn.easydone.androidmvppractice.request.GitHubApiUtils;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio
 * User: Ailurus(ailurus@foxmail.com)
 * Date: 2016-02-01
 * Time: 23:06
 */
public class UserModel {

    private MainPresenterImp mainPresenterImp;

    public UserModel(MainPresenterImp mainPresenterImp) {
        this.mainPresenterImp = mainPresenterImp;
    }

    public void loadDataFromRealm(Realm realm) {
        RealmResults<User> users = realm.where(User.class).findAll();
        mainPresenterImp.loadDataFromRealmSuccess(users);
    }

    public Subscription loadData(List<String> users, Realm realm) {
        return Observable.merge(getObservables(users))
                .buffer(users.size())
                .map(this::getUserList)
                .doOnNext(this::storeUserList)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(users1 -> realm.where(User.class).findAll())
                .subscribe(users2 -> mainPresenterImp.loadDataSuccess(users2),
                        throwable -> {
                            throwable.printStackTrace();
                            mainPresenterImp.loadDataFailure();
                        });
    }

    private void storeUserList(List<User> users) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(users));
        realm.close();
    }

    @NonNull
    private List<User> getUserList(List<GitHubUser> gitHubUsers) {
        List<User> userList = new ArrayList<>();
        for (GitHubUser gitHubUser : gitHubUsers) {
            userList.add(getUser(gitHubUser));
        }
        return userList;
    }

    @NonNull
    private User getUser(GitHubUser gitHubUser) {
        User user = new User();
        user.setUrl(gitHubUser.url);
        user.setAvatarUrl(gitHubUser.avatarUrl);
        user.setName(gitHubUser.name);
        user.setBlog(gitHubUser.blog);
        user.setEmail(gitHubUser.email);
        user.setFollowers(gitHubUser.followers);
        user.setFollowing(gitHubUser.following);
        user.setPublicGists(gitHubUser.publicGists);
        user.setPublicRepos(gitHubUser.publicRepos);
        return user;
    }

    @NonNull
    private List<Observable<GitHubUser>> getObservables(List<String> users) {
        List<Observable<GitHubUser>> observableList = new ArrayList<>();
        for (String user : users) {
            Observable<GitHubUser> observable = GitHubApiUtils.getInstance().getGitHubApi().user(user);
            observableList.add(observable);
        }
        return observableList;
    }
}
