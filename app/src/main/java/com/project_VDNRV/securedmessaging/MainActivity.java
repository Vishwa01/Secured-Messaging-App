package com.project_VDNRV.securedmessaging;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    DatabaseReference codename = databaseReference.child("Users");

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText mail,pswd,username;
        Button log_button,reg;
        final Intent intent = new Intent(this, User_List.class);
        final Intent intent1 = new Intent(this, Register.class);
        mail = findViewById(R.id.email);
        pswd= findViewById(R.id.password);
        reg = findViewById(R.id.register);
        log_button = findViewById(R.id.login);
        username= findViewById(R.id.user_name);
       log_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final String mail_id,pass,codeName;
               mail_id= mail.getText().toString().trim();
               pass = pswd.getText().toString().trim();
               codeName = username.getText().toString().toLowerCase().trim();
               if(mail_id.isEmpty()) {
                   mail.setError("e-mail cannot bo empty");
                   mail.requestFocus();
               }
               else
               if(pass.isEmpty())
               {
                   pswd.setError("Password cannot be empty");
                   pswd.requestFocus();
               }
               else {
                   codename.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                           if(!dataSnapshot.hasChild(codeName)) {
                               username.setError("User does not Exist");
                               username.requestFocus();
                           }
                           else {
                               FirebaseAuth mAuth;
                               mAuth = FirebaseAuth.getInstance();
                               mAuth.signInWithEmailAndPassword(mail_id, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful() && dataSnapshot.child(codeName).child("email").getValue().toString().equals(mail_id) ) {

                                           CurrentUser.getInstance().setCodeName(codeName);
                                           Toast.makeText(MainActivity.this, "Authentication Succeed.", Toast.LENGTH_SHORT).show();
                                           startActivity(intent);
                                       } else {
                                           Toast.makeText(MainActivity.this, "Authentication failed. check Login Credentials", Toast.LENGTH_LONG).show();
                                       }

                                   }
                               });
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });


               }
           }
       });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

    }
}
