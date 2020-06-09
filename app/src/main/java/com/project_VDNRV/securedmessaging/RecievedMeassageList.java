package com.project_VDNRV.securedmessaging;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class RecievedMeassageList extends AppCompatActivity implements RecievedMessageListAdapter.OnItemClickListener {

    String m,q,key;
    final List<Message> userlist = new ArrayList<Message>();
    public void delete(DatabaseReference del)
    {
        del.removeValue();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,User_List.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_meassage_list);
        final String current = CurrentUser.getInstance().getCodeName();
        final String person = CurrentUser.getInstance().getData();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child("Users").child(current).child("Recieved").child(person);
        RecyclerView recyclerView;
         final RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;


        recyclerView = findViewById(R.id.recycler_view1);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecievedMessageListAdapter(userlist,this);
        recyclerView.setAdapter(mAdapter);





        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 key = dataSnapshot.getKey();
                Message mess = (Message) dataSnapshot.getValue(Message.class);
                if(mess.STATUS == true && mess.END<=System.currentTimeMillis())
                    delete(databaseReference.child(key));
                            else {
                    userlist.add(0, mess);
                    m = mess.MESSAGE;
                    q = mess.QUESTION1;
                }
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

        Intent intent = new Intent( this,Decryption.class);
        Message temp = userlist.get(position);
        CurrentUser.getInstance().setMes(temp.ENCRYPTED);
        CurrentUser.getInstance().setSec_que1(temp.QUESTION1);
        CurrentUser.getInstance().setSec_que2(temp.QUESTION2);
        CurrentUser.getInstance().setSec_que3(temp.QUESTION3);
        CurrentUser.getInstance().setKey(temp.KEY);
        startActivity(intent);
    }
}
