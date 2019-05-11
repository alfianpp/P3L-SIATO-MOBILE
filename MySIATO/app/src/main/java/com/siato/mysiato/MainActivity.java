package com.siato.mysiato;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.siato.mysiato.Fragment.HomeFragment;
import com.siato.mysiato.Fragment.KelolaPenjualanFragment;
import com.siato.mysiato.Fragment.LoginFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {


    private NavigationView navigationView;

    private FragmentManager fragmentManager;

    private int currentMenuItem;
    private Fragment defaultFragment;
    private Fragment currentFragment;
    private String defaultFragmentTAG;
    private String currentFragmentTAG;

    private Map<String, Integer> fragmentMenuItemMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        currentMenuItem = R.id.nav_dashboard;

        defaultFragment = new HomeFragment();
        defaultFragmentTAG = HomeFragment.TAG;

        currentFragment = defaultFragment;
        currentFragmentTAG = defaultFragmentTAG;
        addToFragmentMenuItemMap(currentFragmentTAG, currentMenuItem);

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, new HomeFragment(), HomeFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_login:
                showFragment(new LoginFragment(), LoginFragment.TAG);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {

    }

    public void showFragment(Fragment fragment, String fragmentTAG) {
        addToFragmentMenuItemMap(fragmentTAG, currentMenuItem);

        currentFragment = fragment;
        currentFragmentTAG = fragmentTAG;

        /*
        if(currentFragmentTAG.equals(KelolaSparepartsFragment.TAG) ||
                currentFragmentTAG.equals(KelolaSupplierFragment.TAG) ||
                currentFragmentTAG.equals(KelolaKonsumenFragment.TAG) ||
                currentFragmentTAG.equals(KelolaKendaraanFragment.TAG) ||
                currentFragmentTAG.equals(KelolaPengadaanBarangFragment.TAG) ||
                currentFragmentTAG.equals(KelolaPenjualanFragment.TAG)) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        */

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG)
                .addToBackStack(fragmentTAG)
                .commit();
    }

    private void addToFragmentMenuItemMap(String fragmentTag, Integer menuItemID) {
        if(!fragmentMenuItemMap.containsKey(fragmentTag)) {
            fragmentMenuItemMap.put(fragmentTag, menuItemID);
        }
    }
}
