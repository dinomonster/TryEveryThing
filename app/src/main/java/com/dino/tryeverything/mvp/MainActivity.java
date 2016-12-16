package com.dino.tryeverything.mvp;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;
import com.dino.tryeverything.base.BaseCustomTabLayoutAdapter;
import com.dino.tryeverything.base.BaseFragment;
import com.dino.tryeverything.mvp.classify.ClassifyFragment;
import com.dino.tryeverything.mvp.recently.RecentlyFragment;
import com.dino.tryeverything.mvp.loading.LeafLoadingActivity;
import com.dino.tryeverything.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    FrameLayout frameLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

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
        tab_fragments = new HashMap<>();

        mCurrentFragment = createFragment(RecentlyFragment.class);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mCurrentFragment , R.id.main_content);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, mTitles[0]))
                .addItem(new BottomNavigationItem(R.drawable.ic_fenlei, mTitles[1]))
                .addItem(new BottomNavigationItem(R.drawable.ic_face, mTitles[2]))
                .addItem(new BottomNavigationItem(R.drawable.ic_image, mTitles[3]))
                .addItem(new BottomNavigationItem(R.drawable.ic_me, mTitles[4]))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
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


    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                setTopTitle(0);
                switchFragment(RecentlyFragment.class);
                break;
            case 1:
                setTopTitle(1);
                switchFragment(ClassifyFragment.class);
                break;
            case 2:
                setTopTitle(2);
                switchFragment(ClassifyFragment.class);
                break;
            case 3:
                setTopTitle(3);
                switchFragment(ClassifyFragment.class);
                break;
            case 4:
                setTopTitle(4);
                switchFragment(ClassifyFragment.class);
                break;
        }
    }

    private void setTopTitle(int position) {
//        setToolbarCentel(false, mTitles[position]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
