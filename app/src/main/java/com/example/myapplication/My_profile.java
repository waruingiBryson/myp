package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class My_profile extends AppCompatActivity {
   TextView name,email,number;
   FirebaseAuth mAuth;
   FirebaseFirestore fStore;
   String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        name= findViewById(R.id.profileName);
        email=findViewById(R.id.profileEmail);
        number=findViewById(R.id.profileNumber);

        fStore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();

        userID=mAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                number.setText(value.getString("number"));
                email.setText(value.getString("email"));
                name.setText(value.getString("name"));
            }
        });

    }
}
