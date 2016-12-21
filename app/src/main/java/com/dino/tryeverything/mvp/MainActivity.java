package com.dino.tryeverything.mvp;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.mvp.classify.ClassifyFragment;
import com.dino.tryeverything.mvp.recently.RecentlyFragment;
import com.dino.tryeverything.mvp.loading.LeafLoadingActivity;
import com.dino.tryeverything.utils.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;

    private Map<String, BaseFragment> tab_fragments;
    private String mTitles[];
    private BaseFragment mCurrentFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            nextActivity(LeafLoadingActivity.class);
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        return true;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        navView.setItemIconTintList(null);//使图标显示原生颜色


        mTitles = getResources().getStringArray(R.array.main_tab_arrays);
        toolbar.setTitle(mTitles[0]);

        tab_fragments = new HashMap<>();

        mCurrentFragment = createFragment(RecentlyFragment.class);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mCurrentFragment , R.id.main_content);

        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        setTopTitle(0);
                        switchFragment(RecentlyFragment.class);
                        break;
                    case R.id.bottom_fenlei:
                        setTopTitle(1);
                        switchFragment(ClassifyFragment.class);
                        break;
                    case R.id.bottom_image:
                        setTopTitle(2);
                        switchFragment(ClassifyFragment.class);
                        break;
                    case R.id.bottom_face:
                        setTopTitle(3);
                        switchFragment(ClassifyFragment.class);
                        break;
                    case R.id.bottom_me:
                        setTopTitle(4);
                        switchFragment(ClassifyFragment.class);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    private void switchFragment(Class<?> clazz) {
        BaseFragment to = createFragment(clazz);
        if (to.isAdded()) {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().hide(mCurrentFragment).add(R.id.main_content, to).commitAllowingStateLoss();
        }

        mCurrentFragment = to;
    }

    private BaseFragment createFragment(Class<?> clazz) {
        BaseFragment resultFragment = null;
        String className = clazz.getName();
        try {
            if (tab_fragments.containsKey(className)) {
                resultFragment = tab_fragments.get(className);
            } else {
                resultFragment = (BaseFragment) Class.forName(clazz.getName()).newInstance();
                tab_fragments.put(className, resultFragment);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultFragment;
    }


    private void setTopTitle(int position) {
        toolbar.setTitle(mTitles[position]);
    }

}
