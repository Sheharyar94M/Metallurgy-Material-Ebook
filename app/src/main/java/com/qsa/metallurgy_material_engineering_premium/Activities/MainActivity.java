package com.qsa.metallurgy_material_engineering_premium.Activities;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.qsa.metallurgy_material_engineering_premium.R;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    String[] num = {"one", "two", "three", "four"};
    int[] photo = {R.drawable.calender, R.drawable.calender, R.drawable.calender, R.drawable.calender};
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}