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

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dino.tryeverything.R;
import com.dino.tryeverything.base.BaseActivity;
import com.dino.tryeverything.base.BaseCustomTabLayoutAdapter;
import com.dino.tryeverything.mvp.recently.RecentlyFragment;
import com.dino.tryeverything.mvp.loading.LeafLoadingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_content)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    private List<Fragment> tab_fragments;
    private BaseCustomTabLayoutAdapter adapter;
    private String mTitles[];

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
        tab_fragments = new ArrayList<>();
        tab_fragments.add(new RecentlyFragment());
        tab_fragments.add(new RecentlyFragment());
        tab_fragments.add(new RecentlyFragment());
        tab_fragments.add(new RecentlyFragment());
        tab_fragments.add(new RecentlyFragment());

        adapter = new BaseCustomTabLayoutAdapter(getSupportFragmentManager(), tab_fragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bottomNavigationBar.getCurrentSelectedPosition() != position) {
                    // only set item when scroll view pager by hand
                    bottomNavigationBar.selectTab(position);
                    setTopTitle(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_beer, mTitles[0]))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_bellpepper, mTitles[1]))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_bellpepper, mTitles[2]))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_bellpepper, mTitles[3]))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_bellpepper, mTitles[4]))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onTabSelected(int position) {
        switch (position){
            case 0:
                viewPager.setCurrentItem(0);
                setTopTitle(0);
                break;
            case 1:
                viewPager.setCurrentItem(1);
                setTopTitle(1);
                break;
            case 2:
                viewPager.setCurrentItem(2);
                setTopTitle(2);
                break;
            case 3:
                viewPager.setCurrentItem(3);
                setTopTitle(3);
                break;
            case 4:
                viewPager.setCurrentItem(4);
                setTopTitle(4);
                break;
        }
    }

    private void setTopTitle(int position){
//        setToolbarCentel(false, mTitles[position]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
