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
import com.project.comlab.comlabapp.Adapters.RecyclerNewsAdapter;
import com.project.comlab.comlabapp.Adapters.RecyclerNewsDeleteAdapter;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.project.comlab.comlabapp.R.drawable.news;

public class MyNewsActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerNewsDeleteAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    List<NewsModel> myNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("news");
        mAuth = FirebaseAuth.getInstance();

        rv = (RecyclerView) findViewById(R.id.rv_myNews);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        myNewsList = new ArrayList<>();

        adapter = new RecyclerNewsDeleteAdapter(this, getApplicationContext(), myNewsList);
        rv.setAdapter(adapter);

        FirebaseUser user = mAuth.getCurrentUser();

        reference.orderByChild("emailOwner").equalTo(user.getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NewsModel news = dataSnapshot.getValue(NewsModel.class);
                news.setKey(dataSnapshot.getKey());
                myNewsList.add(news);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                NewsModel newsUpdate = dataSnapshot.getValue(NewsModel.class);
                for (NewsModel nm: myNewsList) {
                    if(nm.getKey().equals(key)){
                        nm.setValues(newsUpdate);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                for (NewsModel nm: myNewsList) {
                    if(nm.getKey().equals(key)){
                        myNewsList.remove(nm);
                        adapter.notifyDataSetChanged();
                        return;
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
