package cn.easydone.androidmvppractice.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.easydone.androidmvppractice.R;
import cn.easydone.androidmvppractice.bean.User;
import cn.easydone.androidmvppractice.adapter.UserAdapter;
import cn.easydone.androidmvppractice.presenter.MainPresenterImp;
import cn.easydone.androidmvppractice.view.MainView;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import io.realm.RealmResults;

public class MainActivity extends RxAppCompatActivity implements MainView {

    private MainPresenterImp mainPresenterImp;
    private CircularProgressBar circularProgressBar;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> users = new ArrayList<>();
        users.add("liangzhitao");
        users.add("AlanCheen");
        users.add("yongjhih");
        users.add("zzz40500");
        users.add("greenrobot");
        users.add("nimengbo");

        mainPresenterImp = new MainPresenterImp(this, users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mainPresenterImp.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        circularProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        circularProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setupRecyclerView(RealmResults<User> userList) {
        userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void notifyDataSetChanged(RealmResults<User> users) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenterImp.onDestory();
    }
}
