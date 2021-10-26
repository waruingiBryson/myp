package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Soda extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soda);

        Toolbar toolbar=findViewById(R.id.toolbar);

        button = findViewById(R.id.soda_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //notification code

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Soda.this, "Notifications");
                builder.setContentTitle("Tearnie eatery");
                builder.setContentText("Order received");
                builder.setSmallIcon(R.drawable.ic_stat_name);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Soda.this);
                managerCompat.notify(1, builder.build());


            }
        });


        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.nav_view);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                Intent intent=new Intent(this,Main3Activity.class);
                startActivity(intent);
                break;
            case R.id.nav_cart:
                Intent intent1=new Intent(this,CartActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_notifications:
                Intent intent2=new Intent(this,NotificationsActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_users:
                Intent intent3=new Intent(this,UsersActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_points:
                Intent intent4=new Intent(this,PointsActivity.class);
                startActivity(intent4);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))

        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}







