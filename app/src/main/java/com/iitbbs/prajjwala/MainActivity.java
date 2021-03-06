package com.iitbbs.prajjwala;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public TextView navHeaderTextView;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, new BlankFragment());
        ft.addToBackStack(null);
        ft.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView navHeaderText = (TextView)hView.findViewById(R.id.nav_user_name);
        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        navHeaderText.setText(preferences.getString("UserName", "Customer"));
        navigationView.setNavigationItemSelectedListener(this);

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
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            final String BACK_STACK_ROOT_TAG = "root_fragment";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_profile) {
            final String BACK_STACK_ROOT_TAG = "root_fragment2";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment2())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_gas) {
            final String BACK_STACK_ROOT_TAG = "root_fragment3";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment3())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }else if (id == R.id.nav_subs) {
            final String BACK_STACK_ROOT_TAG = "root_fragment4";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment4())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }  else if (id == R.id.nav_tamper) {
            final String BACK_STACK_ROOT_TAG = "root_fragment5";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment5())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_dealer) {
            final String BACK_STACK_ROOT_TAG = "root_fragment6";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment6())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_customercare) {

            final String BACK_STACK_ROOT_TAG = "root_fragment7";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new BlankFragment7())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_maps) {

            final String BACK_STACK_ROOT_TAG = "root_fragment8";
            //android.app.FragmentManager fragmentManager = getFragmentManager();
            //fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            //fragmentManager.beginTransaction()
                //    .replace(R.id.fragment, new BlankFragment8())
                 //   .addToBackStack(BACK_STACK_ROOT_TAG)
                  //  .commit();
        } else if(id == R.id.nav_log_out){
            Intent intent = new Intent(this, Main3Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(intent);
        } else if (id == R.id.nav_recharge) {
            final String BACK_STACK_ROOT_TAG = "root_fragment9";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new rechargeFragment())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        } else if (id == R.id.nav_usage) {
            final String BACK_STACK_ROOT_TAG = "root_fragment9";
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            // Add the new tab fragment
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, new usageFragment())
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        return;
                    }
                }
                // all permissions were granted
                break;
        }
    }



}
