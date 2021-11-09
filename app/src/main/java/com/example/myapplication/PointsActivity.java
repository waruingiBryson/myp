package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class PointsActivity extends AppCompatActivity {
    TextView t1;
    int points=0;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;
    String mypoints;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        t1 = findViewById(R.id.textView13);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences=this.getSharedPreferences("mypoints",Context.MODE_PRIVATE);
        points =sharedPreferences.getInt("points",0);

        userID = mAuth.getCurrentUser().getUid();
        mypoints=String.valueOf(points);
        DocumentReference documentReference = fStore.collection("users points").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                t1.setText(value.getString("mypoints"));

            }
        });


         }
}
