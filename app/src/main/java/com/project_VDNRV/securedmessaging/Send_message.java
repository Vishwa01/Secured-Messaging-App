package com.project_VDNRV.securedmessaging;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Send_message extends AppCompatActivity {

    Message m = new Message();

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,User_List.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);




        final Button send,prev,next;
        final EditText level,security_quetion1,security_answer1,security_quetion2,security_answer2,security_quetion3,security_answer3,message,user;
        level = findViewById(R.id.level);
        level.setText("1");
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        send = findViewById(R.id.send_button);
        send.setVisibility(View.INVISIBLE);
        security_quetion1 = findViewById(R.id.question1);
        security_answer1 = findViewById(R.id.answer1);
        message = findViewById(R.id.message_plane);
        user = findViewById(R.id.reciever_code_name);

        security_answer2 = findViewById(R.id.answer2);
        security_quetion2 =findViewById(R.id.question2);

        security_answer3 = findViewById(R.id.answer3);
        security_quetion3 =findViewById(R.id.question3);



      level.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View v, boolean hasFocus) {
              if(!hasFocus)
              {
                  long num = Long.parseLong(level.getText().toString());
                  if(num!=3&&num!=2&&num!=1)
                  {
                      send.setVisibility(View.INVISIBLE);
                      if(num>3) {
                          level.setError("level can max 3");
                      }
                     else if(num==0)
                          level.setError("level can be min 1");
                      else
                          level.setError("Enter valid level 1,2 or 3");
                  }
                  else
                      send.setVisibility(View.VISIBLE);
              }


          }
      });


        prev.setOnClickListener(new View.OnClickListener() {

            EditText temp1 = findViewById(R.id.level);
            TextView temp  = findViewById(R.id.editText2);
            @Override
            public void onClick(View v) {
                long t = Long.parseLong(temp.getText().toString());
                long level =  Long.parseLong((temp1.getText().toString()));
                if(t==level)
                    send.setVisibility(View.VISIBLE);
                if(t > 1)
                {

                    if(t==2) {
                        security_answer2.setVisibility(View.INVISIBLE);
                        security_quetion2.setVisibility(View.INVISIBLE);
                        security_answer1.setVisibility(View.VISIBLE);
                        security_quetion1.setVisibility(View.VISIBLE);
                        temp.setText("1");
                        send.setVisibility(View.INVISIBLE);
                    }
                    if(t==3)
                    {

                        security_answer3.setVisibility(View.INVISIBLE);
                        security_quetion3.setVisibility(View.INVISIBLE);
                        security_answer2.setVisibility(View.VISIBLE);
                        security_quetion2.setVisibility(View.VISIBLE);
                        temp.setText("2");
                        send.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            TextView temp  = findViewById(R.id.editText2);
            EditText temp1 = findViewById(R.id.level);
            @Override
            public void onClick(View v) {
                long level =  Long.parseLong(temp1.getText().toString());
                long t = Long.parseLong(temp.getText().toString());

                if(t == level)
                    send.setVisibility(View.VISIBLE);
                else if(t  < level)
                {

                    if(t==1) {
                        if(security_quetion1.getText().toString().isEmpty())
                        {
                            security_quetion1.requestFocus();
                            security_quetion1.setError("It cannot be empty");
                        }
                        if(security_answer1.getText().toString().isEmpty())
                        {
                            security_answer1.requestFocus();
                            security_answer1.setError("It cannot be empty");
                        }
                        temp.setText("2");
                        security_quetion1.setVisibility(View.INVISIBLE);
                        security_answer1.setVisibility(View.INVISIBLE);
                        security_quetion2.setVisibility(View.VISIBLE);
                        security_answer2.setVisibility(View.VISIBLE);
                    }
                    if(t==2)
                    {
                        if(security_quetion2.getText().toString().isEmpty())
                        {
                            security_quetion2.requestFocus();
                            security_quetion2.setError("It cannot be empty");
                        }
                        if(security_answer2.getText().toString().isEmpty())
                        {
                            security_answer2.requestFocus();
                            security_answer2.setError("It cannot be empty");
                        }
                        temp.setText("3");
                        security_quetion2.setVisibility(View.INVISIBLE);
                        security_answer2.setVisibility(View.INVISIBLE);
                        security_quetion3.setVisibility(View.VISIBLE);
                        security_answer3.setVisibility(View.VISIBLE);
                    }
                }
            }
        });





        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = database.getReference();
                final DatabaseReference codename = databaseReference.child("Users");
                codename.addListenerForSingleValueEvent(new ValueEventListener() {

                    String que1 = security_quetion1.getText().toString();
                    String ans1 = security_answer1.getText().toString();
                    String que2 = security_quetion2.getText().toString();
                    String ans2 = security_answer2.getText().toString();
                    String que3 = security_quetion3.getText().toString();
                    String ans3 = security_answer3.getText().toString();
                    String reciever = user.getText().toString().trim();
                    String mess = message.getText().toString();
                    String current = CurrentUser.getInstance().getCodeName();
                    long l = Long.parseLong(level.getText().toString());

                    String  key;
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(l!=1&&l!=2&&l!=3)
                        {
                            if(l>3) {
                                level.setError("level can max 3");
                            }
                            else if(l==0)
                                level.setError("level can be min 1");
                            else
                                level.setError("Enter valid level 1,2 or 3");
                        }
                        if(reciever.isEmpty() || !dataSnapshot.hasChild(reciever))
                        {
                            user.setError("Enter a valid user-id");
                            user.requestFocus();
                        }
                        else
                            if(mess.isEmpty())
                            {
                                message.setError("Enter a message");
                                message.requestFocus();
                            }
                            else if(mess.length()>60)
                            {
                                message.setError("Message can have max length 60 ");
                                message.requestFocus();
                            }
                            else
                            {
                                 key = databaseReference.child("Users").push().getKey();
                                long sec_level = Long.parseLong(level.getText().toString());
                                Encryption encr = new Encryption();
                                String coded = encr.encrypt(mess,"Burrraah");
                                m=new Message(mess,que1,ans1,que2,ans2,que3,ans3,coded,key,sec_level);
                                databaseReference.child("Users").child(reciever).child("Recieved").child(current).child(key).setValue(m);
                                databaseReference.child("Users").child(current).child("Sent").child(reciever).child(key).setValue(m);
                                Toast.makeText(Send_message.this,"Message Sent",Toast.LENGTH_SHORT).show();
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
