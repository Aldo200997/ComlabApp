package com.project.comlab.comlabapp.ProfileConfig;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.comlab.comlabapp.Adapters.RecyclerNewsAdapter;
import com.project.comlab.comlabapp.POJO.NewsModel;
import com.project.comlab.comlabapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyNewsActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerNewsAdapter adapter;
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

        adapter = new RecyclerNewsAdapter(this, getApplicationContext(), myNewsList);
        rv.setAdapter(adapter);

        FirebaseUser user = mAuth.getCurrentUser();

        reference.orderByChild("owner").equalTo(user.getDisplayName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myNewsList.removeAll(myNewsList);
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    NewsModel news = snap.getValue(NewsModel.class);
                    myNewsList.add(news);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
