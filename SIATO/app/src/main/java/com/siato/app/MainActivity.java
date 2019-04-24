package com.siato.app;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Pegawai logged_in_user;

    private Menu drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerMenu = navigationView.getMenu();

        logged_inDrawer(false);

        backToDashboard();
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
        changeFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(int id) {
        Fragment fragment = null;
        String title = null;

        switch (id) {
            case R.id.nav_dashboard:
                fragment = new DashboardFragment();
                title = "SIATO";
                break;

            case R.id.nav_data_spareparts:
                fragment = new KelolaSparepartsFragment();
                title = "Kelola Data Spareparts";
                break;

            case R.id.nav_data_supplier:
                fragment = new KelolaSupplierFragment();
                title = "Kelola Data Supplier";
                break;

            case R.id.nav_data_pengadaan_barang:
                fragment = new KelolaPengadaanBarangFragment();
                title = "Kelola Data Supplier";
                break;

            case 1:
                fragment = new TambahUbahSparepartsFragment();
                title = "Tambah Spareparts";
                break;
            case 2:
                fragment = new TambahUbahSupplierFragment();
                title = "Tambah Supplier";
                break;
            case 3:
                fragment = new TambahUbahPengadaanBarangFragment();
                title = "Tambah Supplier";
                break;

            case R.id.nav_login:
                fragment = new LoginFragment();
                title = "Login";
                break;

            case R.id.nav_logout:
                fragment = new DashboardFragment();
                title = "SIATO";
                logged_inDrawer(false);
                break;
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        getSupportActionBar().setTitle(title);
    }

    public void backToDashboard() {
        changeFragment(R.id.nav_dashboard);
        drawerMenu.findItem(R.id.nav_dashboard).setChecked(true);
    }

    public void logged_inDrawer(Boolean b) {
        drawerMenu.findItem(R.id.coba).setVisible(b);
        drawerMenu.findItem(R.id.nav_login).setVisible(!b);
        drawerMenu.findItem(R.id.nav_logout).setVisible(b);
    }
}
