package com.siato.app;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.siato.app.Fragment.DashboardFragment;
import com.siato.app.Fragment.KelolaKendaraanFragment;
import com.siato.app.Fragment.KelolaKonsumenFragment;
import com.siato.app.Fragment.KelolaPengadaanBarangFragment;
import com.siato.app.Fragment.KelolaPenjualanFragment;
import com.siato.app.Fragment.KelolaSparepartsFragment;
import com.siato.app.Fragment.KelolaSupplierFragment;
import com.siato.app.Fragment.LoginFragment;
import com.siato.app.Fragment.TambahUbahKendaraanFragment;
import com.siato.app.Fragment.TambahUbahKonsumenFragment;
import com.siato.app.Fragment.TambahUbahPengadaanBarangFragment;
import com.siato.app.Fragment.TambahUbahPenjualanFragment;
import com.siato.app.Fragment.TambahUbahSparepartsFragment;
import com.siato.app.Fragment.TambahUbahSupplierFragment;
import com.siato.app.POJO.Pegawai;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawer;
    private FragmentManager fragmentManager;

    public Pegawai logged_in_user = null;

    private NavigationView navigationView;
    private Menu drawerMenu;

    private int currentMenuItem;
    private Fragment defaultFragment;
    private Fragment currentFragment;
    private String defaultFragmentTAG;
    private String currentFragmentTAG;

    private Map<String, Integer> fragmentMenuItemMap = new HashMap<>();

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentMenuItem = R.id.nav_dashboard;

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerMenu = navigationView.getMenu();

        logged_inDrawer(false);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        defaultFragment = new DashboardFragment();
        currentFragment = defaultFragment;

        defaultFragmentTAG = DashboardFragment.TAG;
        currentFragmentTAG = defaultFragmentTAG;

        addToFragmentMenuItemMap(currentFragmentTAG, currentMenuItem);

        navigationView.setCheckedItem(currentMenuItem);
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, currentFragment, currentFragmentTAG)
                .commit();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        String token = task.getResult().getToken();
                        String msg = "App token: " + token;
                        Log.d(TAG, msg);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(fragmentManager.getBackStackEntryCount() == 1) {
                currentFragment = defaultFragment;
                currentFragmentTAG = defaultFragmentTAG;
                currentMenuItem = R.id.nav_dashboard;
                navigationView.setCheckedItem(currentMenuItem);
            }

            if (doubleBackToExitPressedOnce || fragmentManager.getBackStackEntryCount() != 0) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tekan tombol 'Kembali' untuk keluar.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int selectedMenuItem = item.getItemId();

        if(currentMenuItem == selectedMenuItem) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        currentMenuItem = selectedMenuItem;

        changeFragment(selectedMenuItem);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        if(fragmentManager.getBackStackEntryCount() > 0) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
            currentFragmentTAG = fragmentTag;
            currentMenuItem = fragmentMenuItemMap.get(fragmentTag);
            navigationView.setCheckedItem(currentMenuItem);

            if(currentFragmentTAG.equals(defaultFragmentTAG)) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public void changeFragment(int id) {
        Fragment fragment = null;
        String fragmentTAG = null;

        switch (id) {
            case R.id.nav_dashboard:
                fragment = new DashboardFragment();
                fragmentTAG = DashboardFragment.TAG;
                break;

            case R.id.nav_data_spareparts:
                fragment = new KelolaSparepartsFragment();
                fragmentTAG = KelolaSparepartsFragment.TAG;
                break;

            case R.id.nav_data_supplier:
                fragment = new KelolaSupplierFragment();
                fragmentTAG = KelolaSupplierFragment.TAG;
                break;

            case R.id.nav_data_konsumen:
                fragment = new KelolaKonsumenFragment();
                fragmentTAG = KelolaKonsumenFragment.TAG;
                break;

            case R.id.nav_data_kendaraan:
                fragment = new KelolaKendaraanFragment();
                fragmentTAG = KelolaKendaraanFragment.TAG;
                break;

            case R.id.nav_transaksi_pengadaan_barang:
                fragment = new KelolaPengadaanBarangFragment();
                fragmentTAG = KelolaPengadaanBarangFragment.TAG;
                break;

            case R.id.nav_transaksi_penjualan:
                fragment = new KelolaPenjualanFragment();
                fragmentTAG = KelolaPenjualanFragment.TAG;
                break;

            case R.id.nav_login:
                fragment = new LoginFragment();
                fragmentTAG = LoginFragment.TAG;
                break;

            case R.id.nav_logout:
                changeFragment(R.id.nav_dashboard);
                logged_inDrawer(false);
                return;

            case 1:
                fragment = new TambahUbahSparepartsFragment();
                fragmentTAG = TambahUbahSparepartsFragment.TAG;
                break;

            case 2:
                fragment = new TambahUbahSupplierFragment();
                fragmentTAG = TambahUbahSupplierFragment.TAG;
                break;

            case 3:
                fragment = new TambahUbahKonsumenFragment();
                fragmentTAG = TambahUbahKonsumenFragment.TAG;
                break;

            case 4:
                fragment = new TambahUbahKendaraanFragment();
                fragmentTAG = TambahUbahKendaraanFragment.TAG;
                break;

            case 5:
                fragment = new TambahUbahPengadaanBarangFragment();
                fragmentTAG = TambahUbahPengadaanBarangFragment.TAG;
                break;

            case 6:
                fragment = new TambahUbahPenjualanFragment();
                fragmentTAG = TambahUbahPenjualanFragment.TAG;
                break;
        }

        showFragment(fragment, fragmentTAG);
    }

    public void showFragment(Fragment fragment, String fragmentTAG) {
        addToFragmentMenuItemMap(fragmentTAG, currentMenuItem);

        currentFragment = fragment;
        currentFragmentTAG = fragmentTAG;

        if(currentFragmentTAG.equals(KelolaSparepartsFragment.TAG) ||
                currentFragmentTAG.equals(KelolaSupplierFragment.TAG) ||
                currentFragmentTAG.equals(KelolaKonsumenFragment.TAG) ||
                currentFragmentTAG.equals(KelolaKendaraanFragment.TAG) ||
                currentFragmentTAG.equals(KelolaPengadaanBarangFragment.TAG) ||
                currentFragmentTAG.equals(KelolaPenjualanFragment.TAG)) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTAG)
                .addToBackStack(fragmentTAG)
                .commit();
    }

    public void logged_inDrawer(Boolean b) {
        if(logged_in_user != null) {
            if(logged_in_user.getRole().equals(0) || logged_in_user.getRole().equals(1)) {
                drawerMenu.findItem(R.id.pengelolaan_data_menu).setVisible(b);

                if(logged_in_user.getRole().equals(1)) {
                    drawerMenu.findItem(R.id.nav_data_spareparts).setVisible(false);
                    drawerMenu.findItem(R.id.nav_data_supplier).setVisible(false);
                }
            }
            else {
                drawerMenu.findItem(R.id.pengelolaan_data_menu).setVisible(false);
            }

            if(logged_in_user.getRole().equals(0) || logged_in_user.getRole().equals(1)) {
                drawerMenu.findItem(R.id.transaksi_menu).setVisible(b);

                if(logged_in_user.getRole().equals(1) || logged_in_user.getRole().equals(2)) {
                    drawerMenu.findItem(R.id.nav_stok_spareparts).setVisible(false);
                    drawerMenu.findItem(R.id.nav_transaksi_pengadaan_barang).setVisible(false);
                }

                if(logged_in_user.getRole().equals(2)) {
                    drawerMenu.findItem(R.id.nav_transaksi_penjualan).setVisible(false);
                }
            }
            else {
                drawerMenu.findItem(R.id.transaksi_menu).setVisible(false);
            }
        }
        else {
            drawerMenu.findItem(R.id.pengelolaan_data_menu).setVisible(b);
            drawerMenu.findItem(R.id.transaksi_menu).setVisible(b);
        }

        drawerMenu.findItem(R.id.nav_login).setVisible(!b);
        drawerMenu.findItem(R.id.nav_logout).setVisible(b);
    }

    private void addToFragmentMenuItemMap(String fragmentTag, Integer menuItemID) {
        if(!fragmentMenuItemMap.containsKey(fragmentTag)) {
            fragmentMenuItemMap.put(fragmentTag, menuItemID);
        }
    }
}
