package markoidzan.mojtvzandroid;


import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;



public class GlavnoSucelje extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_weba);



        setToolbar();

        initView();





        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mSelectedId = savedInstanceState ==null ? R.id.mojvij : savedInstanceState.getInt("SELECTED_ID");
        selectItem(mSelectedId);

    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void initView() {
        NavigationView mDrawer = (NavigationView) findViewById(R.id.navigation_view);
        mDrawer.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

    }








    private void selectItem(int mSelectedId) {
        Fragment fragement = null;
        Class fragmentClass = null;
        switch (mSelectedId) {
            case R.id.mojvij:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = MojeVijesti.class;
                break;
            case R.id.mojpre:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = MojiPredmeti.class;
                break;
            case R.id.obavst:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = ObavijestiStudentima.class;
                break;
            case R.id.studrefer:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = ObavijestiStudReferade.class;
                break;
            case R.id.rspst:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = RasporedSati.class;
                break;
            case R.id.odv:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = Odjava.class;
                break;
            case R.id.aplabt:
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = AboutApp.class;
                break;

        }

        try {
            fragement = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragement).commit();
    }


    @Override
    public void onConfigurationChanged(Configuration novaKonfiguracija) {
        super.onConfigurationChanged(novaKonfiguracija);
        drawerToggle.onConfigurationChanged(novaKonfiguracija);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId=menuItem.getItemId();
        selectItem(mSelectedId);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //save selected item so it will remains same even after orientation change
        outState.putInt("SELECTED_ID", mSelectedId);
    }
}


