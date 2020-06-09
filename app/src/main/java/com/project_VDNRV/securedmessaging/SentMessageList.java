package com.project_VDNRV.securedmessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SentMessageList extends AppCompatActivity implements SentMessageListAdapter.OnItemClickListener {


    String m,q,key;
    final List<Message> userlist = new ArrayList<Message>();
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,SentUserList.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_message_list);
        final String current = CurrentUser.getInstance().getCodeName();
        final String person = CurrentUser.getInstance().getData();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child("Users").child(current).child("Sent").child(person);
        RecyclerView recyclerView;
        final RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;


        recyclerView = findViewById(R.id.recycler_view2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new SentMessageListAdapter(userlist,  this);
        recyclerView.setAdapter(mAdapter);





        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                key = dataSnapshot.getKey();
                Message mess =  dataSnapshot.getValue(Message.class);
                userlist.add(0,mess);
                m=mess.MESSAGE;
                q=mess.QUESTION1;
                mAdapter.notifyItemInserted(userlist.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Message mess = dataSnapshot.getValue(Message.class);
                String k = mess.KEY;
                ListIterator<Message> i = userlist.listIterator();
                while (i.hasNext())
                {
                    if(i.next().KEY.equals(k))
                    {
                        userlist.remove(i);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent( this,ViewMessage.class);
        Message temp = userlist.get(position);
        CurrentUser.getInstance().setMes(temp.MESSAGE);
        CurrentUser.getInstance().setSec_que1(temp.QUESTION1);
        CurrentUser.getInstance().setSec_que2(temp.QUESTION2);
        CurrentUser.getInstance().setSec_que3(temp.QUESTION3);
        CurrentUser.getInstance().setKey(temp.KEY);
        startActivity(intent);
    }

    }

