package com.project_VDNRV.securedmessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Decryption extends AppCompatActivity implements Runnable{

    private String current = CurrentUser.getInstance().getCodeName();
    private String person = CurrentUser.getInstance().getData();
    private String q1 = CurrentUser.getInstance().getSec_que1();
    private String q2 = CurrentUser.getInstance().getSec_que2();
    private String q3 = CurrentUser.getInstance().getSec_que3();
    private String m = CurrentUser.getInstance().getMes();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String key = CurrentUser.getInstance().getKey();
    final DatabaseReference databaseReference = database.getReference().child("Users").child(current).child("Recieved").child(person);
    public TextView question1,question2,question3,message,temp;
    public EditText answer1,answer2,answer3;
    public Button submit;
    DatabaseReference del = null;

    Thread t = new Thread(this);
    boolean flag = false;

    public void delete(DatabaseReference ref)
    {
        if(ref!=null)
        ref.removeValue();
    }
    @Override
    public void onBackPressed()
    {
        Intent intent =new Intent(this,User_List.class);
        if(t.isAlive())
        {
            flag = true;
        }
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);




        question1 = findViewById(R.id.decryption_question1);
        question1.setText(q1);
        question2 = findViewById(R.id.decryption_question2);
        question2.setText(q2);
        question3 = findViewById(R.id.decryption_question3);
        question3.setText(q3);
        message=findViewById(R.id.decryption_message);
        message.setText(m);
        flag= false;
        answer1 = findViewById(R.id.decryption_answer1);
        answer2 = findViewById(R.id.decryption_answer2);
        answer3 = findViewById(R.id.decryption_answer3);
        temp = findViewById(R.id.textView7);
        submit = findViewById(R.id.button);
        submit.setVisibility(View.VISIBLE);
        submit.setText("Next");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addChildEventListener(new ChildEventListener() {
                    String  a1= answer1.getText().toString();
                    String correct_answer1="";
                    String correct_answer2="";
                    String correct_answer3="";
                    Button next = findViewById(R.id.next);
                    long time = System.currentTimeMillis()+120000;

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if(key.equals(dataSnapshot.getKey()))
                        {
                            next.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.INVISIBLE);
                            final Message m = dataSnapshot.getValue(Message.class);
                             correct_answer1 = m.ANSWER1;
                             correct_answer2 = m.ANSWER2;
                             correct_answer3 = m.ANSWER3;
                             final long level = m.LEVEL;
                            if(a1.equalsIgnoreCase(correct_answer1)) {
                                submit.setVisibility(View.INVISIBLE);
                                answer1.setVisibility(View.INVISIBLE);
                                question1.setVisibility(View.INVISIBLE);
                                if(level>1) {
                                    question2.setVisibility(View.VISIBLE);
                                    question2.setText(m.QUESTION2);
                                    answer2.setVisibility(View.VISIBLE);
                                    next.setVisibility(View.VISIBLE);
                                    next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String ans = answer2.getText().toString();
                                            if(ans.equalsIgnoreCase(correct_answer2))
                                            {
                                                question2.setVisibility(View.INVISIBLE);
                                                answer2.setVisibility(View.INVISIBLE);
                                                if(level>2)
                                                {
                                                    question3.setVisibility(View.VISIBLE);
                                                    question3.setText(q3);
                                                    answer3.setVisibility(View.VISIBLE);
                                                    next.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            String ans= answer3.getText().toString();
                                                            if(ans.equalsIgnoreCase(correct_answer3))
                                                            {
                                                                temp.setVisibility(View.GONE);
                                                                databaseReference.child(key).child("STATUS").setValue(true);
                                                                databaseReference.child(key).child("END").setValue(time);
                                                                del = databaseReference.child(key);
                                                                message.setText(m.MESSAGE);
                                                                next.setVisibility(View.INVISIBLE);
                                                                answer3.setVisibility(View.INVISIBLE);
                                                                question3.setVisibility(View.INVISIBLE);
                                                                t.start();
                                                            }
                                                            else
                                                            {
                                                                question3.requestFocus();
                                                                question3.setError("Wrong Answer");
                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    temp.setVisibility(View.GONE);
                                                    databaseReference.child(key).child("STATUS").setValue(true);
                                                    databaseReference.child(key).child("END").setValue(time);
                                                    message.setText(m.MESSAGE);
                                                    next.setVisibility(View.INVISIBLE);
                                                    submit.setVisibility(View.INVISIBLE);
                                                    answer2.setVisibility(View.INVISIBLE);
                                                    question2.setVisibility(View.INVISIBLE);
                                                    del = databaseReference.child(key);
                                                    t.start();
                                                }
                                            }
                                            else
                                            {
                                                answer2.requestFocus();
                                                answer2.setError("Wrong Answer");
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    temp.setVisibility(View.GONE);
                                    next.setVisibility(View.INVISIBLE);
                                    submit.setVisibility(View.INVISIBLE);
                                    databaseReference.child(key).child("STATUS").setValue(true);
                                    databaseReference.child(key).child("END").setValue(time);
                                    message.setText(m.MESSAGE);
                                    del = databaseReference.child(key);
                                    t.start();
                                }

                            }
                        }
                        else
                        {
                            answer1.requestFocus();
                            answer1.setError("Incorrect answer");
                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


    @Override
    public void run() {
        final Intent intent = new Intent(this, User_List.class);
        try {
            t.sleep(10000);
        }
        catch (Exception e){
            Toast.makeText(Decryption.this, "Authentication failed. check Login Credential", Toast.LENGTH_LONG).show();

        }
        if(flag==false) {
            delete(del);
            startActivity(intent);

        }
    }




}
