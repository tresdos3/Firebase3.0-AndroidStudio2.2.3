package com.example.tresdos.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ListView mList;
    private ArrayList<String> mUsers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://studiofire-73405.firebaseio.com/Usuarios");
        mList = (ListView)findViewById(R.id.Lista);
        final ArrayAdapter<String> MyArrarAdapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,mUsers
        );
        mList.setAdapter(MyArrarAdapter);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String Recibido = dataSnapshot.getValue().toString();
                mUsers.add(Recibido);
                MyArrarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
