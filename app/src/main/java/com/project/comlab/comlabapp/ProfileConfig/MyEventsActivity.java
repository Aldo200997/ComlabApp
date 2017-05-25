package com.project.comlab.comlabapp.ProfileConfig;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Adapters.RecyclerEventsAdapter;
import com.project.comlab.comlabapp.Adapters.RecyclerEventsDeleteAdapter;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyEventsActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerEventsDeleteAdapter adapter;
    List<EventsModel> myEventsList;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("events");
        mAuth = FirebaseAuth.getInstance();

        rv = (RecyclerView) findViewById(R.id.rv_myEvents);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        myEventsList = new ArrayList<>();

        adapter = new RecyclerEventsDeleteAdapter(this, getApplicationContext(), myEventsList);
        rv.setAdapter(adapter);

        FirebaseUser user = mAuth.getCurrentUser();

        reference.orderByChild("emailOwner").equalTo(user.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventsModel event = dataSnapshot.getValue(EventsModel.class);
                event.setKey(dataSnapshot.getKey());
                myEventsList.add(event);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                EventsModel event = dataSnapshot.getValue(EventsModel.class);
                for (EventsModel em: myEventsList) {
                    if(em.getKey().equals(key)){
                        em.setValues(event);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (EventsModel em: myEventsList) {
                    if(em.getKey().equals(key)){
                        myEventsList.remove(em);
                        adapter.notifyDataSetChanged();
                    }
                }
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
