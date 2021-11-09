package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText repassword, name, number, password,email;
    Button sign_up, sign_in;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repassword = findViewById(R.id.repassword);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user1=name.getText().toString().trim();
                String pas=password.getText().toString().trim();
                String rep=repassword.getText().toString().trim();
                String num=number.getText().toString().trim();
                String eml=email.getText().toString().trim();

                if (TextUtils.isEmpty(user1)){
                    name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(eml)){
                    email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pas)){
                    password.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(rep)){
                    repassword.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(num)){
                    number.setError("Phone Number is required");
                    return;
                }


                //Register the user.
                mAuth.createUserWithEmailAndPassword(eml,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(LoginActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                           userID = mAuth.getCurrentUser().getUid();
                           DocumentReference documentReference = fStore.collection("users").document(userID);
                           Map<String,Object> user = new HashMap<>();
                           user.put("name",user1);
                           user.put("number",num);
                           user.put("email",eml);
                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               private static final String TAG = "TAG";

                               @Override
                               public void onSuccess(Void aVoid) {
                                   Log.d(TAG, "onSuccess: User Profile is created for  " + userID);
                               }
                           });

                           Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                           startActivity(intent);
                       }else
                           Toast.makeText(LoginActivity.this, "Error ! occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });


    }
}
