package com.qsa.metallurgy_material_engineering_premium.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.qsa.metallurgy_material_engineering_premium.R;

public class BookView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridView gridView;
    String[] num = {"one", "two", "two", "two", "two", "two", "two", "two", "two", "two", "two", "two", "two"};
    int[] photo = {R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender};
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookview);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.bottom_navigation_bookView);
        toolbar = findViewById(R.id.toolbar);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(getApplicationContext(), "Home", 0).show();
                break;
            case R.id.bookmark:
//                Intent browserIntent1 = new Intent(getApplicationContext(), AddDrama.class);
//                startActivity(browserIntent1);
                Toast.makeText(getApplicationContext(), "Bookmarked", 0).show();

                break;
            case R.id.download:
//                Intent browserIntent2 = new Intent(getApplicationContext(), AddMovie.class);
//                startActivity(browserIntent2);
                Toast.makeText(getApplicationContext(), "Downloaded", 0).show();

                break;

            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Logged Out", 0).show();
                finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }

    }
}