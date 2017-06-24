package com.project.comlab.comlabapp.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Activities.AddNewActivity;
import com.project.comlab.comlabapp.Activities.ContainerActivity;
import com.project.comlab.comlabapp.Adapters.RecyclerNewsAdapter;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {




    public NewsFragment() {
        // Required empty public constructor
    }

    RecyclerView rv_news;
    RecyclerNewsAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    List<NewsModel> newsList;

    FloatingActionButton fab;
    SearchView search;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        showToolbar(view, "Aportes", false);

        search = (SearchView) view.findViewById(R.id.search_news);


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivity(intent);
            }
        });

        // RecyclerView
        rv_news = (RecyclerView) view.findViewById(R.id.rv_news);
        rv_news.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        newsList = new ArrayList<>();

        // Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");

        // Adapter RV
        adapter = new RecyclerNewsAdapter(getActivity(), getContext(), newsList);
        rv_news.setAdapter(adapter);

        // SearchView
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                NewsModel news = dataSnapshot.getValue(NewsModel.class);
                news.setKey(key);
                newsList.add(news);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                NewsModel news = dataSnapshot.getValue(NewsModel.class);
                for (NewsModel nm: newsList) {
                    if(nm.getKey().equals(key)){
                        nm.setValues(news);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (NewsModel nm: newsList) {
                    if(nm.getKey().equals(key)){
                        newsList.remove(nm);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
    
    private void showToolbar(View v, String title, boolean backButton){
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(backButton);

    }
    
}
