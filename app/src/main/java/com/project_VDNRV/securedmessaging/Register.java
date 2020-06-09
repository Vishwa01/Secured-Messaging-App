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

public class Register extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        Button reg = findViewById(R.id.register_Button);
        final EditText mail,name,pswd,code_name;

        name = findViewById(R.id.full_name);
        mail= findViewById(R.id.register_email);
        pswd = findViewById(R.id.create_password);
        code_name = findViewById(R.id.code_name);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mail_id,pass,fullName,codeName;

                mail_id= mail.getText().toString().trim();
                pass = pswd.getText().toString();
                fullName = name.getText().toString();
                codeName = code_name.getText().toString().trim();
                DatabaseReference codename = databaseReference.child("Users");
                codename.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(codeName))
                        {
                            code_name.setError("Try another one");
                            code_name.requestFocus();
                        }
                        else
                        if(mail_id.isEmpty())
                        {
                            mail.setError("e-mail cannot to empty");
                            mail.requestFocus();
                        }
                        else
                        if(pass.isEmpty())
                        {
                            pswd.setError("Password cannot be empty");
                            pswd.requestFocus();
                        }
                        else {

                            FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            final DatabaseReference reference;
                            reference = FirebaseDatabase.getInstance().getReference();


                            mAuth.createUserWithEmailAndPassword(mail_id, pass)
                                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            User user;
                                            user = new User(fullName,codeName,mail_id,pass);
                                            if (task.isSuccessful()) {

                                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                reference.child("Users").child(codeName).setValue(user);
                                            }
                                            else {
                                                Toast.makeText(Register.this, "Registration Failed,", Toast.LENGTH_SHORT).show();
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
        });




    }
}
