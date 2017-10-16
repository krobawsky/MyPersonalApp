package com.tecsup.ricardobq.mypersonalapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tecsup.ricardobq.mypersonalapp.R;
import com.tecsup.ricardobq.mypersonalapp.datos.User;
import com.tecsup.ricardobq.mypersonalapp.datos.UserRepository;
import com.tecsup.ricardobq.mypersonalapp.helper.MyPreferencesActivity;

import java.util.List;

import layout.ConfigFragment;
import layout.PerfilFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView txtName;
    private TextView txtEmail;

    private String name;
    private String username;
    private String phone;

    private static final String TAG = MainActivity.class.getSimpleName();

    // SharedPreferences
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // init SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // get username from SharedPreferences
        username = sharedPreferences.getString("username", null);
        Log.d(TAG, "username: " + username);

        // Extraemos los datos de la base de datos
        List<User> Users = UserRepository.list();

        for (User user : Users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                name = user.getName();
                phone = String.valueOf(user.getPhone());
            }
        }

        //con esto generamos el usuario en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        txtName = (TextView) hView.findViewById(R.id.name);
        txtEmail = (TextView) hView.findViewById(R.id.email);

        txtName.setText(name);
        txtEmail.setText(username);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_perfil) {

            PerfilFragment perfilFragment = new PerfilFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, perfilFragment);

            Bundle args = new Bundle();
            args.putString("name", name);
            args.putString("user", username);
            args.putString("phone", phone);
            perfilFragment.setArguments(args);

            transaction.commit();

        } else if (id == R.id.nav_config) {

            //Intent intent = new Intent(this, MyPreferencesActivity.class);
            //startActivity(intent);

            ConfigFragment configFragment = new ConfigFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, configFragment);

            Bundle args = new Bundle();
            args.putString("user", username);
            configFragment.setArguments(args);

            transaction.commit();


        } else if (id == R.id.nav_logut) {
            logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {

        // remove from SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean success = editor.putBoolean("islogged", false).commit();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
