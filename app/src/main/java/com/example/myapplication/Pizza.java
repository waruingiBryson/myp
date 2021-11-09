package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Pizza extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    Button button,button2;
    public static int points;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    String mypoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);


        Toolbar toolbar=findViewById(R.id.toolbar);
        button = findViewById(R.id.pizza_button);
        button2 = findViewById(R.id.points_button);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences=this.getSharedPreferences("mypoints", Context.MODE_PRIVATE);
        points =sharedPreferences.getInt("points",0);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(points>=500){
                    points -=500;
                    SharedPreferences sharedPreferences=getSharedPreferences("mypoints",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putInt("points",points);
                    editor.apply();
                    userID = mAuth.getCurrentUser().getUid();
                    mypoints = String.valueOf(points);
                    DocumentReference documentReference = fStore.collection("users points").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("mypoints",mypoints);
                    documentReference.set(user);


                    //notification code

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Pizza.this, "my notification");
                    builder.setContentTitle("Tearnie eatery");
                    builder.setContentText("Order received");
                    builder.setSmallIcon(R.drawable.ic_stat_name);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Pizza.this);
                    managerCompat.notify(1, builder.build());

                }
                else {
                    Toast.makeText(Pizza.this, "You Do Not Have Enough Points to make this Order", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points +=100;
                SharedPreferences sharedPreferences=getSharedPreferences("mypoints",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("points",points);
                editor.apply();
                userID = mAuth.getCurrentUser().getUid();
                mypoints = String.valueOf(points);
                DocumentReference documentReference = fStore.collection("users points").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("mypoints",mypoints);
                documentReference.set(user);
                //notification code

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Pizza.this, "my notification");
                builder.setContentTitle("Tearnie eatery");
                builder.setContentText("Order received");
                builder.setSmallIcon(R.drawable.ic_stat_name);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Pizza.this);
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
            case R.id.nav_profile:
                Intent intent5 = new Intent(this, My_profile.class);
                startActivity(intent5);
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




