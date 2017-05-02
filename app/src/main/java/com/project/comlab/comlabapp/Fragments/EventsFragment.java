package com.project.comlab.comlabapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Activities.AddEventActivity;
import com.project.comlab.comlabapp.Adapters.RecyclerEventsAdapter;
import com.project.comlab.comlabapp.POJO.EventsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    // TextView tv;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    FloatingActionButton fab;

    RecyclerView rv_events;
    RecyclerEventsAdapter adapter;
    List<EventsModel> eventList;


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = database.getReference("events");

        rv_events = (RecyclerView) view.findViewById(R.id.rv_events);
        rv_events.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventList = new ArrayList<>();


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });

        // tv = (TextView) view.findViewById(R.id.preferencia);

        adapter = new RecyclerEventsAdapter(eventList);
        rv_events.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.removeAll(eventList);
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    EventsModel event = snap.getValue(EventsModel.class);
                    eventList.add(event);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
